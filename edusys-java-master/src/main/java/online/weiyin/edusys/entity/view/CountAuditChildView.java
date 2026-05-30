package online.weiyin.edusys.entity.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname CountAuditChildView
 * @description 考勤指标明细VO
 */
@Schema(name = "考勤指标明细VO", description = "服装指标数据使用的实体类")
@Data
@AllArgsConstructor
public class CountAuditChildView {
    @Schema(description = "学生总数")
    private Integer total;
    @Schema(description = "签到总数")
    private Integer countTotal;
    @Schema(description = "请假总数")
    private Integer leaveTotal;
    @Schema(description = "缺勤总数")
    private Integer notCountTotal;
}
