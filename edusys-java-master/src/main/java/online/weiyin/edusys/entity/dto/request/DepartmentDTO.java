package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName DepartmentDTO
 * @Description TODO
 * @Version 1.0.0
 * @Time 2023/11/07 下午 08:45
 * @Author 卢子昂
 */
@Schema(name = "部门DTO", description = "部门相关信息dto")
@Data
@AllArgsConstructor
public class DepartmentDTO {
    @Schema(description = "部门编码")
    private String deptCode;
    @Schema(description = "部门名称")
    private String deptName;
    @Schema(description = "上级部门")
    private String superior;
}
