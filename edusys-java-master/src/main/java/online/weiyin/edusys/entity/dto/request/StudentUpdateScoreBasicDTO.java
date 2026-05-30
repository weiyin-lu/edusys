package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentUpdateScoreBasicDTO
 * @description 成绩填写dto
 */
@Schema(name = "成绩填写dto", description = "记录学生指定科目成绩信息")
@Data
public class StudentUpdateScoreBasicDTO {
    @Schema(description = "数据源（学年）")
    private String year;
    @Schema(description = "学号")
    private String id;
    @Schema(description = "考核项目")
    private String rawValue;
    @Schema(description = "考核成绩")
    private Double score;
}
