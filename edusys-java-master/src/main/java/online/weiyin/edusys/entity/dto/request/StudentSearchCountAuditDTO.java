package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSearchCountAuditDTO
 * @description 学生考勤指标DTO
 */
@Schema(name = "学生考勤指标DTO", description = "查询考勤指标时使用")
@Data
public class StudentSearchCountAuditDTO {
    @Schema(description = "查询日期")
    private LocalDate date;
    @Schema(description = "入学年份，同时为数据源生成表名")
    private String year;
    @Schema(description = "上午/下午状态(0=上午 1=下午)")
    private String amOrPm;
    @Schema(description = "是否过滤考勤正常学生（仅在导出文件时使用）")
    private Boolean isNormal;
}
