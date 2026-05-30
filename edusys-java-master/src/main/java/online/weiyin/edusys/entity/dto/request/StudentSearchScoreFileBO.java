package online.weiyin.edusys.entity.dto.request;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
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
public class StudentSearchScoreFileBO {
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
    @Schema(description = "队列动作")
    @ExcelProperty("队列动作")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double groupScore;
    @Schema(description = "轻武器射击")
    @ExcelProperty("轻武器射击")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double weaponScore;
    @Schema(description = "战术动作")
    @ExcelProperty("战术动作")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double tacticalScore;
    @Schema(description = "格斗基础")
    @ExcelProperty("格斗基础")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double fightScore;
    @Schema(description = "战场救护")
    @ExcelProperty("战场救护")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double rescueScore;
    @Schema(description = "核生化防护")
    @ExcelProperty("核生化防护")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double nuclearScore;
    @Schema(description = "行军拉练")
    @ExcelProperty("行军拉练")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double runScore;
    @Schema(description = "考勤扣除分")
    @ExcelProperty("考勤扣除")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double countScore;
    @Schema(description = "最终成绩")
    @ExcelProperty("最终成绩")
    @NumberFormat("#.##")
    @ColumnWidth(15)
    private Double finalScore;
    @Schema(description = "最终评级")
    @ExcelProperty("最终评级")
    @ColumnWidth(15)
    private String finalLevel;
    @Schema(description = "索引ID")
    @ExcelIgnore
    private Long indexId;
}
