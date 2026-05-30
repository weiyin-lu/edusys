package online.weiyin.edusys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class EduSysJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduSysJavaApplication.class, args);
        System.out.println("服务器启动成功");
        System.out.println("开发文档(knife4j): /doc.html");
        System.out.println("开发文档(swagger): /swagger-ui.html");
        System.out.println("开发文档(api-doc): /v3/api-docs");
    }

}
