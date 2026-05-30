package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname 数据源信息登记对象
 * @description 数据源信息登记对象
 */
@Schema(name = "数据源信息登记对象", description = "学生登记时使用")
@Data
@AllArgsConstructor
public class StudentCommitDTO {
    @Schema(description = "入学年份（同时为数据源生成表名）")
    private String year;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "学号")
    private String id;
    @Schema(description = "身高")
    private String height;
    @Schema(description = "体重")
    private String weight;
    @Schema(description = "鞋码")
    private String shoeSize;
    @Schema(description = "手机号")
    private String phone;
}
