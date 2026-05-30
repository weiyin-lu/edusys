package online.weiyin.edusys.controller.serviceManage;

import cn.hutool.json.JSONUtil;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentSearchCountBasicDTO;
import online.weiyin.edusys.entity.dto.request.StudentUpdateCountBasicDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceCountControllerTest
 * @description TODO
 */
@SpringBootTest
class SourceCountControllerTest {

    @Autowired
    SourceCountController sourceCountController;

    @Test
    void getCountListDetail() {

        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2024,4,26));
        dates.add(LocalDate.now());

        StudentSearchCountBasicDTO dto = new StudentSearchCountBasicDTO();
        dto.setPageNumber(1);
        dto.setPageSize(10);
        dto.setYear("test");
        dto.setDeptId("L1P1");
        dto.setId("2307130101");
        dto.setSearchDate(dates);
        Result studentListDetail = sourceCountController.getCountListDetail(dto);
        System.out.println(studentListDetail);

    }

    @Test
    void setCount() {
        StudentUpdateCountBasicDTO dto = new StudentUpdateCountBasicDTO();
        dto.setYear("test");
        dto.setStatus(true);
        dto.setIndexId(Arrays.asList(1L,2L,3L));
        Result result = sourceCountController.setCount(dto);
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    void getStudentListByDept() {
    }

    @Test
    void testGetStudentListDetail() {
    }

    @Test
    void updateStudentCount() {
    }

    @Test
    void updateStudentHonor() {
    }
}