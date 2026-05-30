package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentAddDTO
 * @description 学生信息添加DTO
 */
@Schema(name = "学生信息添加DTO", description = "添加学生数据时使用")
@Data
public class StudentAddDTO {
    @Schema(description = "数据源学年")
    private String year;
    @Schema(description = "学院")
    private String academy;
    @Schema(description = "班级")
    private String clazz;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "身份证号")
    private String cardId;
    @Schema(description = "性别")
    private String sexual;
    @Schema(description = "学号")
    private String id;
    @Schema(description = "备注")
    private String description;
}
