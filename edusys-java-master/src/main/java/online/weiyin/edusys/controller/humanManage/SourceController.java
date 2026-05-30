package online.weiyin.edusys.controller.humanManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentAddDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchBasicDTO;
import online.weiyin.edusys.entity.dto.response.StudentListBasicVO;
import online.weiyin.edusys.entity.table.SourceOperateRecord;
import online.weiyin.edusys.entity.table.StudentSource;
import online.weiyin.edusys.service.SourceOperateRecordService;
import online.weiyin.edusys.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.column;
import static com.mybatisflex.core.query.QueryMethods.distinct;
import static online.weiyin.edusys.entity.table.table.DepartmentTableDef.DEPARTMENT;
import static online.weiyin.edusys.entity.table.table.SourceOperateRecordTableDef.SOURCE_OPERATE_RECORD;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname 人员管理：学生管理（通用）
 * @description studentsource_xxx系列的数据源表的相关功能
 */
@Tag(name = "人员管理：学生管理（通用）")
@SaCheckLogin
@RestController
@RequestMapping("/api/manages/sources")
public class SourceController {

    @Autowired
    private SourceService sourceService;
    @Autowired
    private SourceOperateRecordService sourceOperateRecordService;
    @Value("${spring.servlet.multipart.location}")
    private String uploadUrl;

    @Operation(summary = "录入数据源", description = "[source.basic.upload]从文件读取学生数据源，产生信息表和分数表")
    @SaCheckPermission("source.basic.upload")
    @PutMapping("/sourceLoad/{year}")
    public Result sourceLoad(
            @Parameter(description = "文件")
            @RequestBody MultipartFile file,
            @Parameter(description = "数据源学年")
            @PathVariable String year) {
//        解析文件名，索引1是扩展名
        String[] split = file.getOriginalFilename().split("\\.");
//        文件接收
        File excelFile = new File(uploadUrl + "/excel/" + year + "." + split[1]);
        try {
            file.transferTo(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failed("上传失败", Code.UPLOAD_ERROR);
        }
//        创建操作记录
        SourceOperateRecord record = new SourceOperateRecord(0, year,
                StpUtil.getLoginId().toString(), null, year);
//        录入数据源
        sourceService.operateSource(record, excelFile);
        return Result.success("解析成功");
    }

    @Operation(summary = "查询所有学生列表", description = "[source.basic.show]分页查询特定数据源下全部数据，一页十条")
    @ApiResponse(responseCode = "data", description = "分页信息和学生列表数据")
    @SaCheckPermission("source.basic.show")
    @GetMapping("/searchFullListByPage/{page}/{year}")
    public Result searchFullListByPage(
            @Parameter(description = "页码")
            @PathVariable Integer page,
            @Parameter(description = "数据源学年")
            @PathVariable String year) {
        Page<StudentListBasicVO> pageInstance = new Page<>(page, 10);
        QueryWrapper query = QueryWrapper.create().from(year).as("student")
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")));;
        Page<StudentListBasicVO> sourcePage = sourceService.pageAs(pageInstance, query, StudentListBasicVO.class);
        return Result.success("查询成功", sourcePage);
    }

    // 使用了专用dto返回分页查询结果，可调整每页条目数量
    @Operation(summary = "根据若干条件查询数据源信息", description = "[source.basic.show]数据源查询结果")
    @ApiResponse(responseCode = "data")
    @SaCheckPermission("source.basic.show")
    @PostMapping("/searchByCondition")
    public Result searchByCondition(@RequestBody StudentSearchBasicDTO dto) {
        Page<StudentListBasicVO> page = new Page<>(dto.getPageNumber(), dto.getPageSize());
        // 由于数据源表无法被确定，字段名必须用column()自定义
        // 否则框架会根据实体类代码拼接成”datasource.字段名“
        QueryWrapper query = QueryWrapper.create()
                .from(dto.getYear()).as("student")
                .where(column("academy").like(dto.getAcademy(), StringUtil::isNotBlank))
                .where(column("clazz").like(dto.getClazz(), StringUtil::isNotBlank))
                .where(column("name").like(dto.getName(), StringUtil::isNotBlank))
                .where(column("sexual").like(dto.getSexual(), StringUtil::isNotBlank))
                .where(column("id").eq(dto.getId(), StringUtil::isNotBlank))
                .where(column("phone").eq(dto.getPhone(), StringUtil::isNotBlank))
                .where(column("check_status").eq(dto.getCheckStatus()))
                .where(column("receive_status").eq(dto.getReceiveStatus()))
                .where(column("dept_id").like(dto.getDeptId(), StringUtil::isNotBlank))
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(column("student.dept_id")));
        Page<StudentListBasicVO> sourcePage = sourceService.pageAs(page, query, StudentListBasicVO.class);

        return Result.success("查询成功", sourcePage);
    }

    @Operation(summary = "查询所有学院", description = "[source.basic.show]数据源查询结果")
    @ApiResponse(responseCode = "data")
    @SaCheckPermission("source.basic.show")
    @GetMapping("/getAcademyList/{year}")
    public Result getAcademyList(
            @Parameter(description = "数据源学年")
            @PathVariable String year) {
        QueryWrapper query = QueryWrapper.create()
                .select(distinct(column("academy")))
                .from(year);
        List<String> list = sourceService.listAs(query, String.class);
        return Result.success("学院查询成功", list);
    }

    @Operation(summary = "查询某一学院下的所有班级", description = "[source.basic.show]数据源查询结果")
    @ApiResponse(responseCode = "data")
    @SaCheckPermission("source.basic.show")
    @GetMapping("/searchClassByAcademy/{year}/{academy}")
    public Result searchClassByAcademy(@Parameter(description = "数据源学年")
                                       @PathVariable String year,
                                       @Parameter(description = "学院名")
                                       @PathVariable String academy) {
        QueryWrapper query = QueryWrapper.create()
                .select(distinct(column("clazz")))
                .from(year)
                .where(column("academy").eq(academy));
        List<String> list = sourceService.listAs(query, String.class);
        return Result.success("班级查询成功", list);
    }

    @Operation(summary = "获取数据源操作记录", description = "[source.basic.show]数据源操作记录查询结果（去重）")
    @ApiResponse(responseCode = "data")
    @SaCheckPermission("source.basic.show")
    @GetMapping("/getOperateRecord")
    public Result getOperateRecord() {
        QueryWrapper wrapper = QueryWrapper.create().select(distinct(SOURCE_OPERATE_RECORD.YEAR));
        List<String> list = sourceOperateRecordService.listAs(wrapper, String.class);
        return Result.success("查询成功", list);
    }

    @Operation(summary = "删除一个学生", description = "[source.basic.change]根据学号删除一个学生")
    @ApiResponse(responseCode = "data")
    @SaCheckPermission("source.basic.change")
    @DeleteMapping("/removeById/{id}/{year}")
    public Result removeById(@PathVariable String id, @PathVariable String year) {
        sourceService.deleteById(year, id);
        return Result.success("删除成功");
    }


    @Operation(summary = "添加一个学生", description = "[source.basic.change]向指定数据源添加一条学生信息")
    @ApiResponse(responseCode = "data")
    @SaCheckPermission("source.basic.change")
    @PutMapping("/addForYear")
    public Result addForYear(@RequestBody StudentAddDTO dto) {
        sourceService.addForYear(dto);
        return Result.success("添加成功");
    }
}
