package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname UserInfoRegisterDTO
 * @description 工作人员账号注册DTO
 */
@Schema(name = "工作人员账号注册DTO", description = "注册单独账号使用")
@Data
@AllArgsConstructor
public class UserInfoRegisterDTO {
    @Schema(description = "账号（用户id）")
    private String userId;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "性别")
    private String sexual;
    @Schema(description = "岗位及其他")
    private String post;
}
