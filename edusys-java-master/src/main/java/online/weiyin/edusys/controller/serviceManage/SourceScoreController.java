package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.*;
import online.weiyin.edusys.entity.dto.response.DictionaryTypeVO;
import online.weiyin.edusys.entity.dto.response.StudentListScoreVO;
import online.weiyin.edusys.entity.table.Dictionary;
import online.weiyin.edusys.service.DictionaryService;
import online.weiyin.edusys.service.ScoreService;
import online.weiyin.edusys.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.column;
import static online.weiyin.edusys.entity.table.table.DepartmentTableDef.DEPARTMENT;
import static online.weiyin.edusys.entity.table.table.DictionaryTableDef.DICTIONARY;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceScoreController
 * @description 业务管理：学生管理（成绩业务相关）
 */
@Tag(name = "业务管理：学生管理（成绩业务相关）")
@SaCheckLogin
@RestController
@RequestMapping("/api/sources/scores")
public class SourceScoreController {

    @Autowired
    ScoreService scoreService;
    @Autowired
    SourceService sourceService;
    @Autowired
    DictionaryService dictionaryService;

    @Operation(summary = "获取所有考核项目列表", description = "[source.score.show]获取所有考核项目信息")
    @ApiResponse(responseCode = "data", description = "所有考勤项目列表信息")
    @SaCheckPermission("source.score.show")
    @GetMapping("/getScoreTotalAll")
    public Result getScoreTotalAll() {
        List<Dictionary> list = dictionaryService.queryChain()
                .where(DICTIONARY.DIC_ID.eq("score"))
                .list();
        return Result.success("查询成功", list);
    }

    @Operation(summary = "获取有效考核项目列表", description = "[source.score.show|source.score.client]获取总分不为0的项目的清单")
    @ApiResponse(responseCode = "data", description = "有效的考勤项目列表")
    @SaCheckPermission(value = {"source.score.show","source.score.client"},mode = SaMode.OR)
    @GetMapping("/getUsefulScore")
    public Result getUsefulScore() {
        List<DictionaryTypeVO> list = dictionaryService.queryChain()
                .where(DICTIONARY.DIC_ID.eq("score"))
                .and(DICTIONARY.PARSE_VALUE.gt(0))
                .listAs(DictionaryTypeVO.class);
        return Result.success("查询成功", list);
    }

    @Operation(summary = "修改指定考核项目的总分", description = "[source.score.change]根据项目id修改项目的总分，如果为0视为该项不进行考核")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("source.score.change")
    @PostMapping("/updateScoreTotal")
    public Result updateScoreTotal(@RequestBody DictionaryDTO dto) {
        Double scoreTotal = dictionaryService.queryChain()
                .select("sum(parse_value + 0)")
                .where(DICTIONARY.DIC_ID.eq("score"))
                .oneAs(Double.class);
        if (scoreTotal > 100.0) {
            return Result.failed("所有项目总分不可超过100", Code.INPUT_ERROR);
        }
        UpdateChain.of(Dictionary.class)
                .set(DICTIONARY.PARSE_VALUE, dto.getParseValue())
                .where(DICTIONARY.DIC_ID.eq(dto.getId()))
                .and(DICTIONARY.RAW_VALUE.eq(dto.getRawValue()))
                .update();
        return Result.success("修改成功");
    }

    @Operation(summary = "获得指定连队的学生成绩信息", description = "[source.score.show]根据组织id获得学生信息")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("source.score.show")
    @PostMapping("/getScoreListByDept")
    public Result getScoreListByDept(@RequestBody StudentSearchServiceBasicDTO dto) {
//        请求内容非空检查
        if (dto.getYear() == null) {
            return Result.failed("数据源无效", Code.NOT_FOUND);
        } else if (dto.getDeptId() == null) {
            return Result.failed("无可用组织", Code.NOT_FOUND);
        }
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create()
                .from(dto.getYear()).as("student")
                .leftJoin("score_" + dto.getYear()).as("score").on(column("score.id").eq(column("student.id")))
                .where(column("dept_id").like(dto.getDeptId()));
//        查询学生信息
        List<StudentListScoreVO> list = sourceService.listAs(wrapper, StudentListScoreVO.class);
        return Result.success("连队信息获取成功", list);
    }

    @Operation(summary = "查询特定条件学生成绩信息",
            description = "[source.score.show]查询特定条件下的学生成绩信息详情")
    @ApiResponse(responseCode = "data", description = "学生成绩信息列表")
    @SaCheckPermission("source.score.show")
    @PostMapping("/getScoreListDetail")
    public Result getScoreListDetail(@RequestBody StudentSearchBasicDTO dto) {
        // 构造查询
        Page<StudentListScoreVO> page = new Page<>(dto.getPageNumber(), dto.getPageSize());
        QueryWrapper query = QueryWrapper.create()
                .from(dto.getYear()).as("student")
                .where(column("student.academy").like(dto.getAcademy(), StringUtil::isNotBlank))
                .where(column("student.clazz").like(dto.getClazz(), StringUtil::isNotBlank))
                .where(column("student.name").like(dto.getName(), StringUtil::isNotBlank))
                .where(column("student.sexual").like(dto.getSexual(), StringUtil::isNotBlank))
                .where(column("student.id").eq(dto.getId(), StringUtil::isNotBlank))
                .where(column("student.dept_id").like(dto.getDeptId(), StringUtil::isNotBlank))
                .leftJoin("score_" + dto.getYear()).as("score").on(column("score.id").eq(column("student.id")))
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")));
        Page<StudentListScoreVO> sourcePage = sourceService.pageAs(page, query, StudentListScoreVO.class);
        return Result.success("查询成功", sourcePage);
    }

    @Operation(summary = "登记成绩",
            description = "[source.score.update|source.score.client]登记指定学生指定科目的成绩")
    @ApiResponse(responseCode = "data", description = "修改成功")
    @SaCheckPermission(value = {"source.score.update","source.score.client"},mode = SaMode.OR)
    @PostMapping("/updateScoreByIdAndRaw")
    public Result updateScoreByIdAndRaw(@RequestBody StudentUpdateScoreBasicDTO dto) {
        // 检查输入的有效性，不能超出设置的最大值
        Double scoreTotal = dictionaryService.queryChain()
                .select(DICTIONARY.PARSE_VALUE)
                .from(DICTIONARY)
                .where(DICTIONARY.RAW_VALUE.eq(dto.getRawValue()))
                .oneAs(Double.class);
        if (dto.getScore() > scoreTotal || dto.getScore() < 0) {
            return Result.failed("超出有效范围", Code.INPUT_ERROR);
        }
        scoreService.updateScoreByIDAndRaw(dto);
        return Result.success("修改成功");
    }

    @Operation(summary = "导出成绩详情",
            description = "[source.score]导出成绩详情文件")
    @ApiResponse(responseCode = "data", description = "导出成功")
    @SaCheckPermission("source.score")
    @PostMapping("/getScoreFile")
    public Result getScoreFile(@RequestBody StudentSearchScoreFileDTO dto) {
        List<StudentSearchScoreFileBO> studentList = sourceService.queryChain()
                .from(dto.getYear()).as("student")
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")))
                .leftJoin("score_" + dto.getYear()).as("score").on(column("score.id").eq(column("student.id")))
                .listAs(StudentSearchScoreFileBO.class);

        String filePath = scoreService.writeScoreFile(dto, studentList);
        return Result.success("导出成功", filePath);
    }

}
