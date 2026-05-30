package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname UserInfoDTO
 * @description 工作人员信息查询DTO
 */
@Schema(name = "工作人员信息查询DTO", description = "包含分页参数")
@Data
@AllArgsConstructor
public class UserInfoDTO {
    @Schema(description = "页码")
    private Integer pageNumber;
    @Schema(description = "每页查询数量")
    private Integer pageSize;
    @Schema(description = "账号（用户id）")
    private String userId;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "部门")
    private String department;
    @Schema(description = "岗位及其他")
    private String post;
    @Schema(description = "性别")
    private String sexual;
}
