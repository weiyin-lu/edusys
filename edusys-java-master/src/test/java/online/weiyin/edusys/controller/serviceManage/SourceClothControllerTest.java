package online.weiyin.edusys.controller.serviceManage;

import online.weiyin.edusys.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceClothControllerTest
 * @description TODO
 */
@SpringBootTest
class SourceClothControllerTest {

    @Autowired
    SourceClothController sourceClothController;

    @Test
    void checkStudent() {
    }

    @Test
    void updateSourceInfoByIdAndName() {
    }

    @Test
    void updateCheckStatusById() {
    }

    @Test
    void searchSourceInfoBeforeParse() {
        Result test = sourceClothController.searchSourceInfoBeforeParse("2304070101", "test");
        System.out.println(test);
    }

    @Test
    void updateReceiveStatusById() {
    }
}