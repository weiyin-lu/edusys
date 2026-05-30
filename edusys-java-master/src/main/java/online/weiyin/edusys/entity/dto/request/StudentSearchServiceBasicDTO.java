package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname CountDTO
 * @description TODO
 */
@Schema(name = "连队学生清单DTO", description = "获取连队学生相关清单时使用")
@Data
public class StudentSearchServiceBasicDTO {

    @Schema(description = "数据源学年")
    private String year;

    @Schema(description = "学生所在连队")
    private String deptId;
}
