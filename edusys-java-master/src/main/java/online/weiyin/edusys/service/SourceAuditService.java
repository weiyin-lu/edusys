package online.weiyin.edusys.service;

import online.weiyin.edusys.entity.dto.request.StudentSearchCountFileBO;
import online.weiyin.edusys.entity.view.ClothAuditView;
import online.weiyin.edusys.entity.view.DistributeDeptView;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceAuditService
 * @description 服装领取指标service
 */
public interface SourceAuditService {
    List<ClothAuditView> getSourceAuditAtAcademy(String year);

    List<DistributeDeptView> getDistributeAtDept(String year);

    String writeCountAuditFile(List<StudentSearchCountFileBO> list, String fileName, Boolean isNormal);
}
