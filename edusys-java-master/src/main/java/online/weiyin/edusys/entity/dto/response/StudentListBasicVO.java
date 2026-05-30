package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentListBasicVO
 * @description 基本学生信息查询VO
 */
@Schema(name = "基本学生信息查询VO", description = "查询通用学生信息时使用，包含部门含义转换")
@Data
public class StudentListBasicVO {
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
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "备注")
    private String description;
    @Schema(description = "服装登记状态")
    private Integer checkStatus;
    @Schema(description = "服装领取状态")
    private Integer receiveStatus;
    @Schema(description = "身高")
    private String height;
    @Schema(description = "体重")
    private String weight;
    @Schema(description = "鞋码")
    private String shoeSize;
    @Schema(description = "索引ID")
    private Integer indexId;
    @Schema(description = "连队编号")
    private String deptId;
    @Schema(description = "连队名")
    private String deptName;

}
