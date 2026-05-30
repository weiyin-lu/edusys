package online.weiyin.edusys.entity.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname CountAuditView
 * @description 考勤指标VO
 */
@Schema(name = "考勤指标VO", description = "服装指标数据使用的实体类")
@Data
@AllArgsConstructor
public class CountAuditView {
    @Schema(description = "学生总数")
    private Integer total;
    @Schema(description = "签到总数")
    private Integer countTotal;
    @Schema(description = "请假总数")
    private Integer leaveTotal;
    @Schema(description = "缺勤总数")
    private Integer notCountTotal;
    @Schema(description = "指标明细 班级视图")
    private Map<String, CountAuditChildView> classView;
    @Schema(description = "指标明细 班级视图")
    private Map<String, CountAuditChildView> deptView;
    @Schema(description = "指标明细 学院视图")
    private Map<String, CountAuditChildView> academyView;
}
