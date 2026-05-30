package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname UserInfoLofinVO
 * @description 用户登录后信息DTO
 */
@Schema(name = "用户登录后信息DTO", description = "在用户登陆后可以获取到的全部信息")
@Data
public class UserInfoLoginVO {
    @Schema(description = "账号")
    private String userId;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "组织机构")
    private String department;
    @Schema(description = "组织含义")
    private String deptName;
    @Schema(description = "岗位及其他")
    private String post;
    @Schema(description = "性别")
    private String sexual;
    @Schema(description = "头像地址")
    private String image;
}
