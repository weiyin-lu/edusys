package online.weiyin.edusys.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.PermissionDTO;
import online.weiyin.edusys.entity.dto.request.RelevanceRPDTO;
import online.weiyin.edusys.entity.dto.request.RelevanceBatchDTO;
import online.weiyin.edusys.entity.table.RolePermissionRelevance;
import online.weiyin.edusys.service.PermissionService;
import online.weiyin.edusys.service.RolePermissionRelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.distinct;
import static online.weiyin.edusys.entity.table.table.PermissionTableDef.PERMISSION;
import static online.weiyin.edusys.entity.table.table.RolePermissionRelevanceTableDef.ROLE_PERMISSION_RELEVANCE;
import static online.weiyin.edusys.entity.table.table.RoleTableDef.ROLE;


/**
 * @ClassName PermissionController
 * @Description 系统管理：角色/权限配置
 * @Version 1.0.0
 * @Time 2023/11/06 下午 10:54
 * @Author 卢子昂
 */
@Tag(name = "系统管理：角色/权限配置")
@RestController
@SaCheckLogin
@RequestMapping("/api/admins/relevances/permission")
public class RolePermissionRelevancController {
    @Autowired
    RolePermissionRelevanceService rolePermissionRelevanceService;
    @Autowired
    PermissionService permissionService;

    @Operation(summary = "查看所有权限列表", description = "[admin.relevance.permission.show]查看所有有效的权限信息")
    @ApiResponse(responseCode = "data", description = "权限列表")
    @SaCheckPermission("admin.relevance.permission.show")
    @GetMapping("/getPermissionList")
    public Result getPermissionListAll() {
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create()
                .select(distinct(PERMISSION.PERMISSION_ID, PERMISSION.PERMISSION_NAME));
//        执行
        List<PermissionDTO> list = permissionService.listAs(wrapper, PermissionDTO.class);
        return Result.success("查询成功", list);
    }

    @Operation(summary = "查看角色拥有的权限列表", description = "[admin.relevance.permission.show]根据角色id查询角色当前拥有的权限")
    @ApiResponse(responseCode = "data", description = "角色列表")
    @SaCheckPermission("admin.relevance.permission.show")
    @GetMapping("/getPermissionList/{roleId}")
    public Result getPermissionListByRoleId(
            @Parameter(description = "角色id")
            @PathVariable String roleId) {
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create()
                .select(distinct(PERMISSION.PERMISSION_ID, PERMISSION.PERMISSION_NAME))
                .from(PERMISSION).as("p")
                .join(ROLE_PERMISSION_RELEVANCE).as("rp")
                .on(ROLE_PERMISSION_RELEVANCE.PERMISSION_ID.eq(PERMISSION.PERMISSION_ID))
                .join(ROLE).as("r")
                .on(ROLE.ROLE_ID.eq(ROLE_PERMISSION_RELEVANCE.ROLE_ID))
                .where(ROLE.ROLE_ID.eq(roleId));
//        执行
        List<PermissionDTO> list = permissionService.listAs(wrapper, PermissionDTO.class);
        return Result.success("查询成功", list);
    }

    @Operation(summary = "为角色添加权限（批量）", description = "[admin.relevance.permission.add]根据角色id和权限id为角色批量添加权限")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("admin.relevance.permission.add")
    @Transactional
    @PostMapping("/addPermission")
    public Result addPermissionForRole(@RequestBody RelevanceBatchDTO dto) {
//        根据dto构造批量插入所用对象
        List<RolePermissionRelevance> batch = new ArrayList<>();
        for (String permissionId : dto.getList()) {
            batch.add(new RolePermissionRelevance(null, dto.getRoleId(), permissionId));
        }
//        执行
        rolePermissionRelevanceService.saveBatch(batch);
        return Result.success("添加成功");
    }

    @Operation(summary = "为角色移除权限", description = "[admin.relevance.permission.remove]根据角色id和权限id为其移除权限")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("admin.relevance.permission.remove")
    @PutMapping("/removePermission")
    public Result removePermissionForRole(@RequestBody RelevanceRPDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where(ROLE_PERMISSION_RELEVANCE.ROLE_ID.eq(dto.getRoleId()))
                .and(ROLE_PERMISSION_RELEVANCE.PERMISSION_ID.eq(dto.getPermissionId()));
        boolean remove = rolePermissionRelevanceService.remove(wrapper);
        if (remove) {
            return Result.success("移除成功");
        } else {
            return Result.failed(Code.REMOVE_ERROR);
        }
    }

}
