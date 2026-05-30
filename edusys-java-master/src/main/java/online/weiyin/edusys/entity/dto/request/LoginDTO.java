package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Authorize
 * @Description 登录/注册请求dto
 * @Version 1.0.0
 * @Time 2023/11/06 下午 01:59
 * @Author 卢子昂
 */

@Schema(name = "登录请求dto", description = "登录时使用")
@Data
@AllArgsConstructor
public class LoginDTO {

    @Schema(description = "账号")
    private String username;
    @Schema(description = "密码")
    private String password;
}
