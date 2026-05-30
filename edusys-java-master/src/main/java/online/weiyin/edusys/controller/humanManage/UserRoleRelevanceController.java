package online.weiyin.edusys.controller.humanManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.RelevanceURDTO;
import online.weiyin.edusys.entity.table.UserRoleRelevance;
import online.weiyin.edusys.service.UserRoleRelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static online.weiyin.edusys.entity.table.table.UserRoleRelevanceTableDef.USER_ROLE_RELEVANCE;


/**
 * @ClassName RoleController
 * @Description 人员管理：工作人员管理
 * @Version 1.0.0
 * @Time 2023/11/06 下午 10:17
 * @Author 卢子昂
 */
@Tag(name = "人员管理：工作人员管理")
@RestController
@SaCheckLogin
@RequestMapping("/api/manages/users/roles")
public class UserRoleRelevanceController {

    @Autowired
    private UserRoleRelevanceService userRoleRelevanceService;

    @Operation(summary = "为用户添加角色", description = "[user.role.add]根据用户id和角色id为其添加角色")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("user.role.add")
    @PostMapping("/addRoleForUser")
    public Result addRoleForUser(@RequestBody RelevanceURDTO dto) {
        UserRoleRelevance relevance = new UserRoleRelevance(null, dto.getUserId(), dto.getRoleId());
        userRoleRelevanceService.save(relevance);
        return Result.success("添加成功");
    }

    @Operation(summary = "为用户移除角色", description = "[user.role.remove]根据用户id和角色id为其移除角色")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckOr(permission = @SaCheckPermission("user.role.remove"))
    @PutMapping("/removeRoleForUser")
    public Result removeRoleForUser(@RequestBody RelevanceURDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where(USER_ROLE_RELEVANCE.USER_ID.eq(dto.getUserId()))
                .and(USER_ROLE_RELEVANCE.ROLE_ID.eq(dto.getRoleId()));
        boolean remove = userRoleRelevanceService.remove(wrapper);
        if (remove) {
            return Result.success("移除成功");
        } else {
            return Result.failed(Code.REMOVE_ERROR);
        }
    }
}
