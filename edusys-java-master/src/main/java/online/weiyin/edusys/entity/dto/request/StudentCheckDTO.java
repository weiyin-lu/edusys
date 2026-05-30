package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname CheckStudentDTO
 * @description 学生信息校验对象
 */
@Schema(name = "学生信息校验对象", description = "校验学生信息时使用")
@Data
@AllArgsConstructor
public class StudentCheckDTO {
    @Schema(description = "数据源学年")
    private String year;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "身份证号")
    private String cardId;
}
