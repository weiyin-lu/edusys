package online.weiyin.edusys.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountFileBO;
import online.weiyin.edusys.entity.view.ClothAuditView;
import online.weiyin.edusys.entity.view.DistributeDeptView;
import online.weiyin.edusys.mapper.SourceAuditMapper;
import online.weiyin.edusys.service.SourceAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceAuditServiceImpl
 * @description 服装领取指标serviceImpl
 */
@Service
public class SourceAuditServiceImpl implements SourceAuditService {

    @Autowired
    private SourceAuditMapper sourceAuditMapper;

    @Value("${spring.servlet.multipart.location}")
    private String uploadUrl;

    @Override
    public List<ClothAuditView> getSourceAuditAtAcademy(String year) {
        String tableName = "studentsource_" + year;
        return sourceAuditMapper.selectClothAuditAtAcademy(tableName);
    }

    @Override
    public List<DistributeDeptView> getDistributeAtDept(String year) {
        String tableName = "studentsource_" + year;
        return sourceAuditMapper.selectDistributeAtDept(tableName);
    }

    @Override
    public String writeCountAuditFile(List<StudentSearchCountFileBO> list, String fileName,Boolean isNormal) {
        // 文件完整路径
        String filePath = uploadUrl + "/fileOut/" + fileName;
        // 按学院分类的学生缓存集合
        HashMap<String, List<StudentSearchCountFileBO>> academyMap = new HashMap<>();
        // 解析传入的列表对其按学院分类
        for (StudentSearchCountFileBO fileBO : list) {
            // 检查键名，如果列表中不存在该学院，说明sheet页从未创建
            if (!academyMap.containsKey(fileBO.getAcademy())) {
                academyMap.put(fileBO.getAcademy(), new ArrayList<>());
            }
            // 将数据存储到对应学院的分类缓存集合中，根据参数判断是否过滤正常出勤的学生
            if(isNormal) {
                // 过滤出勤学生
                if("出勤".equals(fileBO.getStatus())) {
                    continue;
                } else {
                    academyMap.get(fileBO.getAcademy()).add(fileBO);
                }
            } else {
                // 不过滤，所有都记录
                academyMap.get(fileBO.getAcademy()).add(fileBO);
            }
        }
        try (ExcelWriter excelWriter = EasyExcel.write(filePath, StudentSearchCountFileBO.class).build()) {
            // sheet页位置
            int sheetNo = 0;
            for (String academy : academyMap.keySet()) {
                // 创建sheet页
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, academy).build();
                // 向当前sheet页导入数据
                excelWriter.write(academyMap.get(academy), writeSheet);
                // sheet页位置自增
                sheetNo++;
            }
        }
        return "/sources/fileOut/" + fileName;
    }
}
