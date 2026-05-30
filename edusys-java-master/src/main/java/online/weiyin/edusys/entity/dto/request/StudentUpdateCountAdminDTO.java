package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentUpdateCountAdminDTO
 * @description 考勤信息复杂批量修改dto
 */
@Schema(name = "考勤信息复杂批量修改dto", description = "批量变更指定日期指定时间段的考勤信息")
@Data
public class StudentUpdateCountAdminDTO {
    @Schema(description = "数据源（学年）")
    private String year;
    @Schema(description = "选定时间")
    private LocalDate date;
    @Schema(description = "上午/下午状态(0=上午 1=下午)")
    private String amOrPm;
    @Schema(description = "变更的考勤状态(0=缺勤 1=出勤 2=请假)")
    private String newCountStatus;
    @Schema(description = "数据源键列表")
    private List<Long> indexId;
}
