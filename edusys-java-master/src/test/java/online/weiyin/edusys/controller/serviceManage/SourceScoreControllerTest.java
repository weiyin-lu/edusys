package online.weiyin.edusys.controller.serviceManage;

import cn.hutool.json.JSONUtil;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentSearchScoreFileDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceScoreControllerTest
 * @description TODO
 */
@SpringBootTest
class SourceScoreControllerTest {

    @Autowired
    SourceScoreController sourceScoreController;

    @Test
    void getScoreTotalAll() {
    }

    @Test
    void getUsefulScore() {
    }

    @Test
    void updateScoreTotal() {
    }

    @Test
    void getScoreListByDept() {
    }

    @Test
    void getScoreListDetail() {
    }

    @Test
    void updateScoreByIdAndRaw() {
    }

    @Test
    void getScoreFile() {
        StudentSearchScoreFileDTO dto = new StudentSearchScoreFileDTO();
        dto.setYear("test");
        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2024,5,8));
        dates.add(LocalDate.of(2024,5,9));
        dto.setSearchDate(dates);
        Result file = sourceScoreController.getScoreFile(dto);
        System.out.println(JSONUtil.toJsonPrettyStr(file));
    }
}