package online.weiyin.edusys.entity.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname ClothAuditVO
 * @description 服装指标VO
 */
@Schema(name = "服装指标VO", description = "服装指标数据使用的实体类")
@Data
@AllArgsConstructor
public class ClothAuditView {
    @Schema(description = "学院")
    private String academy;
    @Schema(description = "统计人数")
    private Integer total;
    @Schema(description = "未登记人数")
    private Integer notCheck;
    @Schema(description = "已登记人数")
    private Integer checked;
    @Schema(description = "未领取人数")
    private Integer notReceive;
    @Schema(description = "已领取人数")
    private Integer received;
}
