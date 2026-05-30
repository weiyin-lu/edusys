package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSearchCountAuditDTO
 * @description 学生考勤指标BO
 */
@Schema(name = "学生考勤指标BO", description = "查询考勤指标时的中间对象")
@Data
public class StudentSearchCountAuditBO {
    @Schema(description = "学生所在学院")
    private String academy;
    @Schema(description = "学生所在连队")
    private String deptName;
    @Schema(description = "学生所在班级")
    private String clazz;
    @Schema(description = "索引ID")
    private Long indexId;
}
