package online.weiyin.edusys.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.dto.request.DistributeStudentDTO;
import online.weiyin.edusys.entity.dto.request.StudentAddDTO;
import online.weiyin.edusys.entity.dto.request.StudentCommitDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchBasicDTO;
import online.weiyin.edusys.entity.table.SourceOperateRecord;
import online.weiyin.edusys.entity.table.StudentSource;
import online.weiyin.edusys.listener.SourceListener;
import online.weiyin.edusys.mapper.ScoreMapper;
import online.weiyin.edusys.mapper.SourceMapper;
import online.weiyin.edusys.mapper.SourceOperateMapper;
import online.weiyin.edusys.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceServiceImpl
 * @description SourceMapper的业务层实现
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceMapper, StudentSource> implements SourceService {

    @Autowired
    private SourceMapper sourceMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private SourceOperateMapper sourceOperateMapper;

    /**
     * 导入数据源
     *
     * @param record 数据源操作记录
     * @param file   数据源文件
     * @return
     */
    @Override
    @Transactional
    public Boolean operateSource(SourceOperateRecord record, File file) {
        // 构造表名
        String tableName1 = "studentsource_" + record.getYear();
        String tableName2 = "studentsource_score_" + record.getYear();
        // 创建学生信息表并导入数据
        sourceMapper.createSource(tableName1); // 创建表，语法会判断表的存在性
        sourceMapper.clear(tableName1); // 先清空同名数据源表的历史数据（如果表之前被上传过的话）
        // 创建学生成绩表并导入数据
        sourceMapper.createScore(tableName2);
        sourceMapper.clear(tableName2);
        // 添加操作记录
        sourceOperateMapper.insert(record);
        // 插入本次读取到的新数据
        EasyExcel.read(file, StudentSource.class, new SourceListener(sourceMapper, scoreMapper, record.getYear()))
                .sheet().doRead(); // 从excel添加数据
        return true;
    }

    /**
     * 根据表名、id、name修改数据源信息
     *
     * @param info
     * @return
     */
    @Override
    public Integer updateSourceInfoByIdAndName(StudentCommitDTO info) {
        info.setYear("studentsource_" + info.getYear());
        return sourceMapper.updateSourceInfoByIdAndName(info);
    }

    /**
     * 根据表名（入学年份）、id修改特定状态（status）
     *
     * @param year   入学年份（表名）
     * @param id     学号
     * @param status 新的状态（1/0）
     * @return
     */
    @Override
    public Integer updateCheckStatusById(String year, String id, String status) {
        return sourceMapper.updateCheckStatusById("studentsource_" + year, id, status);
    }

    /**
     * 根据表名（入学年份）、id修改特定状态（status）
     *
     * @param year   入学年份（表名）
     * @param id     学号
     * @param status 新的状态（1/0）
     * @return
     */
    @Override
    public Integer updateReceiveStatusById(String year, String id, String status) {
        return sourceMapper.updateReceiveStatusById("studentsource_" + year, id, status);
    }

    /**
     * 根据若干查询条件修改dept_id
     *
     * @param dto 更新条件和更新值
     * @return
     */
    @Override
    public Boolean updateDeptIdByCondition(StudentSearchBasicDTO dto) {
        dto.setYear("studentsource_" + dto.getYear());
        Integer r1 = sourceMapper.updateDeptIdByCondition(dto);
        return r1 > 0;
    }

    /**
     * 根据id修改dept_id
     *
     * @param dto
     * @return
     */
    public Boolean updateDeptIdById(DistributeStudentDTO dto) {
        dto.setYear("studentsource_" + dto.getYear());
        Integer r1 = sourceMapper.updateDeptIdById(dto);
        return r1 > 0;
    }

    /**
     * 根据id删除一个学生
     *
     * @param year
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(String year, String id) {
        Integer integer = sourceMapper.deleteAloneById("studentsource_" + year, id);
        return integer > 0;
    }

    /**
     * 添加一个学生
     *
     * @param dto
     * @return
     */
    @Override
    public Boolean addForYear(StudentAddDTO dto) {
        dto.setYear("studentsource_" + dto.getYear());
        Integer integer = sourceMapper.addInfo(dto);
        return integer > 0;
    }
}
