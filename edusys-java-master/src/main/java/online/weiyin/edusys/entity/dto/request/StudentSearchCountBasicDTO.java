package online.weiyin.edusys.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSearchCountDateDTO
 * @description 学生考勤相关数据查询DTO
 */
@Schema(name = "学生考勤相关数据查询DTO", description = "根据时间段获取考勤信息DTO")
@Data
public class StudentSearchCountBasicDTO {
    @Schema(description = "页码")
    private Integer pageNumber;
    @Schema(description = "每页查询数量")
    private Integer pageSize;
    @Schema(description = "查询日期范围")
    private List<LocalDate> searchDate;
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
    @Schema(description = "连队编号")
    private String deptId;
}
