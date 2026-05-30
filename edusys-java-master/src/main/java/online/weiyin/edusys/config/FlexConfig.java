package online.weiyin.edusys.config;

import com.mybatisflex.core.table.DynamicTableProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @classname mybatis-flex配置类
 * @description mybatis-flex的动态表名配置
 * @version 1.0.0
 * @author 卢子昂
 */
@Configuration
public class FlexConfig {

    @Bean
    public DynamicTableProcessor dynamicTableProcessor() {
        // 如果待操作的表不是sys_xxx自动变更成studentsource_xxx
        // 说明：
        // 此配置主要目的用于处理不特定数据源的查询业务
        // 仅控制studentsource的查询，studentscore的查询全部使用手写语法实现
        // online.weiyin.edusys.controller.infoManage.SourceController中直接使用ORM框架方法的接口受此配置控制
        // online.weiyin.edusys.service.impl.SourceServiceImpl中自定义的业务无法受此配置控制，在代码中进行了额外声明
        DynamicTableProcessor processor = s -> {
            if (s.matches("^(sys_).*")) {
                return s;
            } else {
                return "StudentSource_" + s;
            }
        };
        return processor;
    }
}
