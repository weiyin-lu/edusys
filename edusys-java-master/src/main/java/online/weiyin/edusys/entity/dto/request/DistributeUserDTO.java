package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname DistributeDTO
 * @description 组织分配dto
 */
@Schema(name = "教官组织分配dto",description = "传输教官组织分配信息")
@Data
public class DistributeUserDTO {

    @Schema(description = "用户ID")
    private String userId;
    @Schema(description = "新的连队ID")
    private String newDeptId;
}
