package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentListCountBatchVO
 * @description 学生考勤信息范围查询DTO
 */
@Schema(name = "学生考勤信息范围查询DTO", description = "查询学生考勤数据时使用，获得多天数据")
@Data
public class StudentListCountDateVO {
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
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "索引ID")
    private Integer indexId;
    @Schema(description = "连队编号")
    private String deptId;
    @Schema(description = "连队名")
    private String deptName;
    @Schema(description = "每日考勤状态")
    private List<StudentCountListBO> countStatus;
}
