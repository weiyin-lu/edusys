package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname DictionaryDTO
 * @description 字典数据DTO
 */
@Schema(name = "字典数据DTO",description = "传输字典相关信息")
@Data
public class DictionaryDTO {
    @Schema(description = "数据ID")
    private String id;
    @Schema(description = "原始值")
    private String rawValue;
    @Schema(description = "转换值")
    private String parseValue;
}
