package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentDistributeDTO
 * @description 学生组织分配dto
 */
@Schema(name = "学生组织分配dto",description = "传输单独学生的组织分配信息")
@Data
public class DistributeStudentDTO {
    @Schema(description = "数据源学年")
    private String year;
    @Schema(description = "学号")
    private String id;
    @Schema(description = "新的组织ID")
    private String newDeptId;

}
