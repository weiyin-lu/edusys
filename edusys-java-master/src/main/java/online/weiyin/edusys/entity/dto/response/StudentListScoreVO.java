package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSearchCountDateDTO
 * @description 学生成绩相关数据查询结果VO
 */
@Schema(name = "学生成绩相关数据查询结果VO", description = "学生成绩数据查询结果")
@Data
public class StudentListScoreVO {
    @Schema(description = "页码")
    private Integer pageNumber;
    @Schema(description = "每页查询数量")
    private Integer pageSize;
    @Schema(description = "入学年份，同时为数据源生成表名")
    private String year;
    @Schema(description = "学院")
    private String academy;
    @Schema(description = "班级")
    private String clazz;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "性别")
    private String sexual;
    @Schema(description = "学号")
    private String id;
    @Schema(description = "连队名")
    private String deptName;
    @Schema(description = "队列动作")
    private Double groupScore;
    @Schema(description = "轻武器射击")
    private Double weaponScore;
    @Schema(description = "战术动作")
    private Double tacticalScore;
    @Schema(description = "格斗基础")
    private Double fightScore;
    @Schema(description = "战场救护")
    private Double rescueScore;
    @Schema(description = "核生化防护")
    private Double nuclearScore;
    @Schema(description = "行军拉练")
    private Double runScore;
    @Schema(description = "出勤考核")
    private Double countScore;
}
