package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import online.weiyin.edusys.entity.dto.request.RoleDTO;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname UserInfoListBasicDTO
 * @description 基本教官信息查询DTO
 */
@Schema(name = "基本教官信息查询DTO", description = "查询通用教官信息时使用，包含部门含义转换")
@Data
public class UserInfoListBasicDTO {
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
    @Schema(description = "可用角色列表")
    private List<RoleDTO> roleList;
    @Schema(description = "岗位及其他")
    private String post;
    @Schema(description = "性别")
    private String sexual;
    @Schema(description = "头像地址")
    private String image;
}
