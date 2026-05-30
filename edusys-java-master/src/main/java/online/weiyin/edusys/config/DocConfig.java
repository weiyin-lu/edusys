package online.weiyin.edusys.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @classname 接口文档配置类
 * @description knife4j的文档介绍基本配置
 * @version 1.0.0
 * @author 卢子昂
 */
@Configuration
public class DocConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("高校学生军训信息化管理系统")
                        .description("knife4j接口文档")
                        .version("v1,0")
                        .contact(new Contact()
                                .name("卢子昂")
                                .email("weiyin2002@outlook.com")));
    }
}
