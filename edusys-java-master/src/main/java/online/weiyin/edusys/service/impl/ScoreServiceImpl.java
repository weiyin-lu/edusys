package online.weiyin.edusys.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountFileBO;
import online.weiyin.edusys.entity.dto.request.StudentSearchScoreFileBO;
import online.weiyin.edusys.entity.dto.request.StudentSearchScoreFileDTO;
import online.weiyin.edusys.entity.dto.request.StudentUpdateScoreBasicDTO;
import online.weiyin.edusys.entity.table.StudentSourceScore;
import online.weiyin.edusys.mapper.ScoreMapper;
import online.weiyin.edusys.mapper.SourceMapper;
import online.weiyin.edusys.service.ScoreService;
import online.weiyin.edusys.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname ScoreServiceImpl
 * @description 学生成绩表 serviceImpl
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, StudentSourceScore>
        implements ScoreService {
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${spring.servlet.multipart.location}")
    private String uploadUrl;

    @Override
    public Boolean updateScoreByIDAndRaw(StudentUpdateScoreBasicDTO dto) {
        dto.setYear("studentsource_score_" + dto.getYear());
        Integer integer = scoreMapper.updateScoreByIDAndRaw(dto);
        return integer > 0;
    }

    @Override
    @Transactional
    public String writeScoreFile(StudentSearchScoreFileDTO dto, List<StudentSearchScoreFileBO> studentList) {
        // 资源准备
        // 文件名
        String fileName = dto.getYear() +
                "年学生军训(" +
                dto.getSearchDate().get(0).format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "-" +
                dto.getSearchDate().get(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                ")" +
                "总成绩单" +
                ".xlsx";
        // 文件路径
        String filePath = uploadUrl + "/fileOut/" + fileName;
        // 检查时间范围
        LocalDate startDate = dto.getSearchDate().get(0);
        LocalDate endDate = dto.getSearchDate().get(1);
        // 循环处理
        for (int i = 0; i < studentList.size(); i++) {
            // 核算每个学生的考勤成绩，填入到成绩表里
            // 当前学生出勤考核总分对象
            Double countScore = 0.0;
            for (LocalDate nowDate = startDate;
                 nowDate.isBefore(endDate) || nowDate.isEqual(endDate);
                 nowDate = nowDate.plusDays(1)) {
                // 获取当前时间构成的键名
                String key = RedisUtil.getKey(dto.getYear(), nowDate);
                // 查询当天的所有考勤记录数据
                Boolean amNormal = redisTemplate.opsForValue().getBit(key,
                        studentList.get(i).getIndexId());
                Boolean pmNormal = redisTemplate.opsForValue().getBit(key,
                        studentList.get(i).getIndexId() + 10000);
                Boolean amLeave = redisTemplate.opsForValue().getBit(key,
                        studentList.get(i).getIndexId() + 20000);
                Boolean pmLeave = redisTemplate.opsForValue().getBit(key,
                        studentList.get(i).getIndexId() + 30000);
                // 对考勤数据统计计分
                // 请假一次扣5分，缺勤一次扣20分，正常出勤不扣分
                if (amLeave) {
                    countScore += 5.0;
                }
                if (pmLeave) {
                    countScore += 5.0;
                }
                // 缺勤一次扣20分
                if (!amNormal) {
                    countScore += 20.0;
                }
                if (!pmNormal) {
                    countScore += 20.0;
                }
            }
            // 考勤核分结束，将分数存储到中间对象里，并同时存入数据库
            studentList.get(i).setCountScore(countScore);
            scoreMapper.updateCountScoreById("studentsource_score_" + dto.getYear(), countScore,
                    studentList.get(i).getId());
            // 计算总分和等级分，将其存储到中间对象里
            // 计算总分
            StudentSearchScoreFileBO tempInstance = studentList.get(i);
            Double total = tempInstance.getGroupScore()
                    + tempInstance.getWeaponScore()
                    + tempInstance.getTacticalScore()
                    + tempInstance.getFightScore()
                    + tempInstance.getRescueScore()
                    + tempInstance.getNuclearScore()
                    + tempInstance.getRunScore()
                    - tempInstance.getCountScore();
            String level = "未定级";
            if (total < 0.0) {
                total = 0.0;
            }

            // 计算等级分
            switch ((int) (total / 10)) {
                case 10:
                case 9:
                    level = "优秀";
                    break;
                case 8:
                    level = "良好";
                    break;
                case 7:
                    level = "一般";
                    break;
                case 6:
                    level = "合格";
                    break;
                default:
                    level = "不合格";
            }
            studentList.get(i).setFinalScore(total);
            studentList.get(i).setFinalLevel(level);
        }

        // 生成文件
        try (ExcelWriter excelWriter = EasyExcel.write(filePath, StudentSearchScoreFileBO.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("成绩详情").build();
            excelWriter.write(studentList, writeSheet);
        }
        return "/sources/fileOut/" + fileName;
    }
}
