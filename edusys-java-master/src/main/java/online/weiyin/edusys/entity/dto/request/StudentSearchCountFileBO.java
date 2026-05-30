package online.weiyin.edusys.entity.dto.request;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSearchCountFileBO
 * @description 学生考勤指标明细BO
 */
@Schema(name = "学生考勤指标明细BO", description = "导出考勤明细文件的中间对象")
@Data
@EqualsAndHashCode
@HeadRowHeight(20)
public class StudentSearchCountFileBO {
    @Schema(description = "学院")
    @ExcelProperty("学院")
    @ColumnWidth(25)
    private String academy;
    @Schema(description = "班级")
    @ExcelProperty("班级")
    @ColumnWidth(10)
    private String clazz;
    @Schema(description = "学号")
    @ExcelProperty("学号")
    @ColumnWidth(10)
    private String id;
    @Schema(description = "姓名")
    @ExcelProperty("姓名")
    @ColumnWidth(10)
    private String name;
    @Schema(description = "性别")
    @ExcelProperty("性别")
    @ColumnWidth(10)
    private String sexual;
    @Schema(description = "所属连队")
    @ExcelProperty("所属连队")
    @ColumnWidth(15)
    private String deptName;
    @Schema(description = "记录时间")
    @ExcelProperty("记录时间")
    @ColumnWidth(15)
    private String date;
    @Schema(description = "时间段")
    @ExcelProperty("时间段")
    @ColumnWidth(10)
    private String amOrPm;
    @Schema(description = "考勤状态")
    @ExcelProperty("考勤状态")
    @ColumnWidth(10)
    private String status;
    @Schema(description = "索引ID")
    @ExcelIgnore
    private Long indexId;
}
