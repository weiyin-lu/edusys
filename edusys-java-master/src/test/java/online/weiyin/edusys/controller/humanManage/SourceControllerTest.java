package online.weiyin.edusys.controller.humanManage;

import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.controller.serviceManage.SourceClothController;
import online.weiyin.edusys.entity.dto.request.StudentSearchBasicDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceControllerTest
 * @description TODO
 */
@SpringBootTest
class SourceControllerTest {

    @Autowired
    SourceController sourceController;

    @Autowired
    SourceClothController sourceClothController;

    @Test
    public void searchByCondition() {
        StudentSearchBasicDTO dto = new StudentSearchBasicDTO(1, 10, "test", null
                , "土木", null, null, null, null, null,
                null, null, null, null, null,
                null, null);
        Result r = sourceController.searchByCondition(dto);
        System.out.println(r);
    }

    @Test
    void searchAcademy() {
        Result test = sourceController.getAcademyList("test");
        System.out.println(test);
    }

    @Test
    void searchClassByAcademy() {
        Result test = sourceController.searchClassByAcademy("test", "土木工程学院");
        System.out.println(test);
    }

    @Test
    void sourceLoad() {
    }

    @Test
    void searchFullListByPage() {
    }

    @Test
    void getAcademyList() {
    }

    @Test
    void getOperateRecord() {
    }

    @Test
    void removeById() {
    }

    @Test
    void addForYear() {
    }
}