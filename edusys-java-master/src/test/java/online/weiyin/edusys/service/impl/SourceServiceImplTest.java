package online.weiyin.edusys.service.impl;

import online.weiyin.edusys.entity.table.SourceOperateRecord;
import online.weiyin.edusys.service.SourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @ClassName TODO
 * @Description TODO
 * @Version 1.0.0
 * @Author 卢子昂
 */
@SpringBootTest
class SourceServiceImplTest {

    @Autowired
    private SourceService sourceService;

    @Test
    public void operateSource() {
        SourceOperateRecord record = new SourceOperateRecord(null, "test",
                "admin", null, "测试数据，可删除");
        File file = new File("src/main/resources/data/test.xlsx");
        sourceService.operateSource(record, file);
    }
}