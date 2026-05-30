package online.weiyin.edusys.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentCountList
 * @description TODO
 */
@Schema(name = "每日考勤列表实体")
@Data
public class StudentCountListBO {
    @Schema(description = "日期")
    private LocalDate date;
    @Schema(description = "上午考勤状态")
    private String amStatus;
    @Schema(description = "下午考勤状态")
    private String pmStatus;
}
