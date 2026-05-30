package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname DictionaryTypeVO
 * @description TODO
 */
@Schema(name = "有效考核项目字典VO", description = "获取有效的考核项目列表时使用")
@Data
@AllArgsConstructor
public class DictionaryTypeVO {
    @Schema(description = "字典集ID")
    private String dicId;
    @Schema(description = "字典集含义")
    private String description;
    @Schema(description = "原始值（考核项目编号）")
    private String rawValue;
    @Schema(description = "转换值（考核项目总分）")
    private String parseValue;
}
