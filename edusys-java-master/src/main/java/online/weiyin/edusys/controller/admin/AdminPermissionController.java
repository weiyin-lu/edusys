package online.weiyin.edusys.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.PermissionDTO;
import online.weiyin.edusys.entity.table.Permission;
import online.weiyin.edusys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static online.weiyin.edusys.entity.table.table.PermissionTableDef.PERMISSION;


/**
 * @ClassName AdminPermissionController
 * @Description 系统管理：权限配置
 * @Version 1.0.0
 * @Time 2023/11/06 下午 10:54
 * @Author 卢子昂
 */
@Tag(name = "系统管理：权限配置")
@RestController
@SaCheckLogin
@RequestMapping("/api/admins/permissions")
public class AdminPermissionController {
    @Autowired
    private PermissionService permissionService;

    @Operation(summary = "查询所有权限列表",description = "[admin.permission.show]分页查询全部权限信息，一页十条")
    @ApiResponse(responseCode = "data", description = "分页信息和权限列表数据")
    @SaCheckPermission("admin.permission.show")
    @GetMapping("/getPermissionList/{page}")
    public Result getPermissionListByPage(@PathVariable Integer page) {
        Page<Permission> pageInstance = new Page<>(page,10);
        Page<Permission> permissionPage = permissionService.page(pageInstance);
        return Result.success("查询成功",permissionPage);
    }

    // 使用了map返回分页查询结果，没有特定dto，分页固定在10条/页
    @Operation(summary = "根据条件模糊查找权限", description = "[admin.permission.show]分页查询条件匹配的权限，一页10条")
    @ApiResponse(responseCode = "data", description = "符合查找条件的数据")
    @Parameters({
            @Parameter(name = "permissionId", description = "权限id"),
            @Parameter(name = "permissionName", description = "权限含义"),
            @Parameter(name = "page", description = "页码，默认为1"),
    })
    @SaCheckPermission("admin.permission.show")
    @PostMapping("/getPermissionList")
    public Result getPermissionListByCondition(@RequestBody HashMap<String, String> condition) {
        Page<PermissionDTO> page = new Page(Integer.parseInt(condition.get("page")), 10);
        QueryWrapper wrapper = QueryWrapper.create()
                .from(PERMISSION)
                .where(PERMISSION.PERMISSION_ID.like(condition.get("permissionId"), If::notNull))
                .and(PERMISSION.PERMISSION_NAME.like(condition.get("permissionName"), If::notNull));
        Page<PermissionDTO> list = permissionService.pageAs(page, wrapper, PermissionDTO.class);
        return Result.success("查询成功", list);
    }

    @Operation(summary = "添加权限", description = "[admin.permission.add]添加一个新的权限")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("admin.permission.add")
    @PostMapping("/addPermission")
    public Result addPermission(@RequestBody PermissionDTO dto) {
//        构造对象
        Permission permission = new Permission(null, dto.getPermissionId(), dto.getPermissionId(), "0");
//        执行
        permissionService.save(permission);
        return Result.success("添加成功");
    }

}
