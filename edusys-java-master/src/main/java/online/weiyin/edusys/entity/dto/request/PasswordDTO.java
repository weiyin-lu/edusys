package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname PasswordDTO
 * @description 修改密码dto
 */
@Schema(name = "修改密码dto", description = "修改秘密时使用")
@Data
public class PasswordDTO {
    @Schema(description = "账号")
    private String username;
    @Schema(description = "原密码")
    private String oldPwd;
    @Schema(description = "新密码")
    private String newPwd;
}
