package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentUpdateCountBasicDTO
 * @description 考勤批量修改dto
 */
@Schema(name = "考勤批量修改dto", description = "批量记录当日考勤信息")
@Data
public class StudentUpdateCountBasicDTO {
    @Schema(description = "数据源（学年）")
    private String year;
    @Schema(description = "数据源键列表")
    private List<Long> indexId;
    @Schema(description = "考勤状态 true=出勤 false=缺勤")
    private Boolean status;
}
