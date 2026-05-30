package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname 数据源响应对象（分页）
 * @description 数据源响应对象（分页）
 */
@Schema(name = "数据源信息综合查询对象", description = "包含分页参数")
@Data
@AllArgsConstructor
public class StudentSearchBasicDTO {
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
    @Schema(description = "备注")
    private String description;
    @Schema(description = "身高")
    private String height;
    @Schema(description = "体重")
    private String weight;
    @Schema(description = "鞋码")
    private String shoeSize;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "服装登记状态 0=未登记、1=已登记")
    private Integer checkStatus;
    @Schema(description = "服装领取状态 0=未领取 1=已领取")
    private Integer receiveStatus;
    @Schema(description = "连队编号")
    private String deptId;
    @Schema(description = "连队编号（仅在更新时使用）")
    private String newDeptId;

}
