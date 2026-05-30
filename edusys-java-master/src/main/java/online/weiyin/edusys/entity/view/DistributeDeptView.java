package online.weiyin.edusys.entity.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname DistributeDeptView
 * @description TODO
 */
@Schema(name = "连队分配指标VO", description = "连队分配的学生数")
@Data
@AllArgsConstructor
public class DistributeDeptView {

    @Schema(description = "组织名")
    private String deptName;
    @Schema(description = "统计人数")
    private String studentTotal;
    @Schema(description = "教官信息")
    private String userList;
    @Schema(description = "教官数量")
    private String userTotal;
}
