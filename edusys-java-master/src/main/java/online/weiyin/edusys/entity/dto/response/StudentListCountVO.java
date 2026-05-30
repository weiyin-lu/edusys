package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname CountSearchDTO
 * @description 学生考勤信息单独查询DTO
 */
@Schema(name = "学生考勤信息单独查询DTO", description = "查询学生考勤数据时使用（仅获得当天考勤数据）")
@Data
public class StudentListCountVO {
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

    @Schema(description = "本日上午考勤状态")
    private String AmStatus;
    @Schema(description = "本日下午考勤状态")
    private String PmStatus;

}
