package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.StudentCheckDTO;
import online.weiyin.edusys.entity.dto.request.StudentCommitDTO;
import online.weiyin.edusys.entity.dto.response.DictionaryParseVO;
import online.weiyin.edusys.entity.table.StudentSource;
import online.weiyin.edusys.service.DictionaryService;
import online.weiyin.edusys.service.SourceService;
import online.weiyin.edusys.util.ClothUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.mybatisflex.core.query.QueryMethods.column;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceClothController
 * @description 业务管理：学生管理（服装业务相关）
 */
@Tag(name = "业务管理：学生管理（服装业务相关）")
@RestController
@SaCheckLogin
@RequestMapping("/api/services/sources/cloths")
public class SourceClothController {

    @Autowired
    SourceService sourceService;

    @Autowired
    DictionaryService dictionaryService;

    @Operation(summary = "校验学生身份", description = "[null]通过身份证号和姓名校验学生身份")
    @ApiResponse(responseCode = "data", description = "校验结果")
    @SaIgnore
    @PostMapping("/checkStudent")
    public Result checkStudent(@RequestBody StudentCheckDTO dto) {
        QueryWrapper query = QueryWrapper.create()
                .from(dto.getYear())
                .where(column("name").eq(dto.getName()))
                .and(column("card_id").eq(dto.getCardId()));
        StudentSource one = sourceService.getOne(query);
        if (one != null) {
            return Result.success("成功", one);
        } else {
            return Result.failed(Code.NOT_FOUND);
        }
    }

    @Operation(summary = "登记学生的基本信息", description = "[null]学生登记服装尺码信息和联系方式")
    @ApiResponse(responseCode = "data", description = "登记完成")
    @SaIgnore
    @PutMapping("/updateSourceInfoByIdAndName")
    public Result updateSourceInfoByIdAndName(@RequestBody StudentCommitDTO info) {
        // 写入信息
        sourceService.updateSourceInfoByIdAndName(info);
        return Result.success("登记完成");
    }

    @Operation(summary = "修改某一学生的登记状态", description = "[null]更新登记状态，0=未领取、1=已领取")
    @ApiResponse(responseCode = "data", description = "修改完成")
    @SaIgnore
    @GetMapping("/updateCheckStatus/{year}/{id}/{status}")
    public Result updateCheckStatusById(
            @Parameter(description = "数据源学年")
            @PathVariable String year,
            @Parameter(description = "学号")
            @PathVariable String id,
            @Parameter(description = "修改状态1/0")
            @PathVariable String status) {
        sourceService.updateCheckStatusById(year, id, status);
        return Result.success("修改完成");
    }

    @Operation(summary = "获取一个学生转换后的服装信息", description = "[null]获取服装转换信息，转换后增加三个新字段")
    @ApiResponse(responseCode = "data", description = "转换结果")
    @SaIgnore
    @GetMapping("/searchSourceInfoBeforeParse/{id}/{year}")
    public Result searchSourceInfoBeforeParse(
            @Parameter(description = "学号")
            @PathVariable String id,
            @Parameter(description = "数据源学年")
            @PathVariable String year) {
        // 获取此学生的所有基本信息
        QueryWrapper wrapper = QueryWrapper.create().from(year).where(column("id").eq(id));
        StudentSource source = sourceService.getOne(wrapper);
        // 执行转换算法
        DictionaryParseVO dictionaryParseVO = new ClothUtil(dictionaryService).parse(source);
        // 返回结果
        return Result.success("转换完成", dictionaryParseVO);
    }

    @Operation(summary = "更新某一学生的领取状态", description = "[source.cloths]更新领取状态，0=未领取、1=已领取")
    @ApiResponse(responseCode = "data", description = "更新完成")
    @SaCheckLogin
    @SaCheckPermission("source.cloths.status")
    @GetMapping("/updateReceiveStatus/{year}/{id}/{status}")
    public Result updateReceiveStatusById(@Parameter(description = "数据源学年")
                                          @PathVariable String year,
                                          @Parameter(description = "学号")
                                          @PathVariable String id,
                                          @Parameter(description = "修改状态1/0")
                                          @PathVariable String status) {
        sourceService.updateReceiveStatusById(year, id, status);
        return Result.success("成功");
    }
}
