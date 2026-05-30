package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.DistributeStudentDTO;
import online.weiyin.edusys.entity.dto.request.DistributeUserDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchBasicDTO;
import online.weiyin.edusys.entity.table.UserInfo;
import online.weiyin.edusys.entity.view.DistributeDeptView;
import online.weiyin.edusys.service.SourceAuditService;
import online.weiyin.edusys.service.SourceService;
import online.weiyin.edusys.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static online.weiyin.edusys.entity.table.table.UserInfoTableDef.USER_INFO;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceDistributeController
 * @description 人员管理：人员分配管理
 */
@Tag(name = "人员管理：人员分配管理")
@SaCheckLogin
@RestController
@RequestMapping("/api/manages/distribute")
public class DistributeController {

    @Autowired
    private SourceService sourceService;

    @Autowired
    private SourceAuditService sourceAuditService;

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "为学生分配连队（批量）", description = "[distribute.update]根据查询条件为学生分配连队")
    @ApiResponse(responseCode = "data", description = "分配结果")
    @SaCheckPermission("distribute.student")
    @PutMapping("/updateStudentDeptByCondition")
    public Result updateStudentDeptByCondition(@RequestBody StudentSearchBasicDTO dto) {
        sourceService.updateDeptIdByCondition(dto);
        return Result.success("分配完成");
    }

    @Operation(summary = "为学生分配连队（单个）", description = "[distribute.update]根据学号为单个学生分配连队")
    @ApiResponse(responseCode = "data", description = "分配结果")
    @SaCheckPermission("distribute.student")
    @PutMapping("/updateStudentDeptById")
    public Result updateStudentDeptById(@RequestBody DistributeStudentDTO dto) {
        sourceService.updateDeptIdById(dto);
        return Result.success("分配完成");
    }

    @Operation(summary = "为教官分配连队", description = "[distribute.update]根据教官ID为教官分配连队")
    @ApiResponse(responseCode = "data", description = "分配结果")
    @SaCheckPermission("distribute.user")
    @PutMapping("/updateUserDeptByCondition")
    public Result updateUserDeptByCondition(@RequestBody DistributeUserDTO dto) {
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create().where(USER_INFO.USER_ID.eq(dto.getUserId()));
//        构造更新对象
        UserInfo of = UpdateEntity.of(UserInfo.class);
        of.setDepartment(dto.getNewDeptId());
//        执行
        userInfoService.update(of, wrapper);
        return Result.success("分配完成");
    }

    @Operation(summary = "查询当前数据源人员分配情况统计", description = "[distribute.audit]获取当前数据源的连队分配情况")
    @ApiResponse(responseCode = "data", description = "统计数据")
    @SaCheckPermission(value = {"distribute.user","distribute.student"},mode = SaMode.OR)
    @GetMapping("/getDistributeAudit/{year}")
    public Result getDistributeAudit(@PathVariable String year) {
        List<DistributeDeptView> list = sourceAuditService.getDistributeAtDept(year);
        return Result.success("统计完成", list);
    }
}
