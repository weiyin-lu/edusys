package online.weiyin.edusys.controller.serviceManage;

import cn.hutool.json.JSONUtil;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountAuditDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname AuditCountControllerTest
 * @description TODO
 */
@SpringBootTest
class AuditCountControllerTest {

    @Autowired
    AuditCountController auditCountController;

    @Test
    void getCountAuditAtDept() {
        StudentSearchCountAuditDTO dto = new StudentSearchCountAuditDTO();
        dto.setDate(LocalDate.now());
        dto.setYear("test");
        dto.setAmOrPm("1");
        Result countAuditAtDept = auditCountController.getCountAuditAtDept(dto);
        System.out.println(JSONUtil.toJsonPrettyStr(countAuditAtDept));
    }

    @Test
    void getCountAuditFile() {
        StudentSearchCountAuditDTO dto = new StudentSearchCountAuditDTO();
        dto.setDate(LocalDate.of(2024,5,3));
        dto.setYear("test");
        dto.setAmOrPm("1");
//        dto.setAmOrPm("0");
        dto.setIsNormal(true);
        Result countAuditFile = auditCountController.getCountAuditFile(dto);
        System.out.println(JSONUtil.toJsonPrettyStr(countAuditFile));
    }
}