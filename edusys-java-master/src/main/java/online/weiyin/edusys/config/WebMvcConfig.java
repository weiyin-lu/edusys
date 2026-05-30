package online.weiyin.edusys.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfig
 * @Description webmvc拦截器配置
 * @Version 1.0.0
 * @Time 2023/11/05 下午 08:03
 * @Author 卢子昂
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.servlet.multipart.location}")
    String uploadUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        Sa-Token异常拦截（注解）
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        重定向动态文件资源
        registry.addResourceHandler("/sources/**")
                .addResourceLocations("file:" + uploadUrl + "/headerImage") //头像
                .addResourceLocations("file:" + uploadUrl + "/template") //文件上传模板
                .addResourceLocations("file:" + uploadUrl + "/document") //指导文件
                .addResourceLocations("file:" + uploadUrl + "/fileOut"); //导出考勤指标文件
    }
}
