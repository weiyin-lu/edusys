package online.weiyin.edusys.entity.dto.request;

import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSearchScoreFileDTO
 * @description 学生成绩明细DTO
 */
@Schema(name = "学生成绩明细DTO", description = "导出学生明细信息的请求对象")
@Data
@EqualsAndHashCode
public class StudentSearchScoreFileDTO {
    @Schema(description = "入学年份，同时为数据源生成表名")
    private String year;
    @Schema(description = "考勤核算范围")
    private List<LocalDate> searchDate;
}
