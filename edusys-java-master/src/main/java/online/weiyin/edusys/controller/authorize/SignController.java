package online.weiyin.edusys.controller.authorize;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.LoginDTO;
import online.weiyin.edusys.entity.dto.response.UserInfoLoginVO;
import online.weiyin.edusys.entity.table.User;
import online.weiyin.edusys.entity.table.UserInfo;
import online.weiyin.edusys.service.UserInfoService;
import online.weiyin.edusys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static online.weiyin.edusys.entity.table.table.DepartmentTableDef.DEPARTMENT;
import static online.weiyin.edusys.entity.table.table.UserInfoTableDef.USER_INFO;
import static online.weiyin.edusys.entity.table.table.UserTableDef.USER;


/**
 * @ClassName SignController
 * @Description 认证管理：登录和注销
 * @Version 1.0.0
 * @Time 2023/11/06 下午 01:54
 * @Author 卢子昂
 */
@Tag(name = "认证管理：登录和注销")
@RestController
@RequestMapping("/api/authorizes/signs")
public class SignController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userinfoService;

    @Operation(summary = "登录", description = "[null]根据账号密码登录系统")
    @ApiResponse(responseCode = "data", description = "当前账号的认证、角色、权限、个人信息")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO login) {
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create()
                .where(USER.USER_ID.eq(login.getUsername()))
                .and(USER.PASSWORD.eq(SecureUtil.md5(login.getPassword())));
//        查询是否有对应的记录
        User one = userService.getOne(wrapper);
        if (one != null) {
//            登录
            StpUtil.login(login.getUsername());
//            封装一个map，返回当前账号的登录、角色、权限、个人信息等
            QueryWrapper where = QueryWrapper.create()
                    .select(USER_INFO.DEFAULT_COLUMNS,DEPARTMENT.DEPT_NAME)
                    .from(USER_INFO)
                    .where(USER_INFO.USER_ID.eq(login.getUsername()))
                    .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(USER_INFO.DEPARTMENT));
            UserInfoLoginVO info = userinfoService.getOneAs(where, UserInfoLoginVO.class);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("token", StpUtil.getTokenInfo());
            resultMap.put("role", StpUtil.getRoleList());
            resultMap.put("permission", StpUtil.getPermissionList());
            resultMap.put("info", info);
//            返回
            return Result.success("登录成功", resultMap);
        } else {
            return Result.failed("登录失败，账号或密码错误", Code.AUTHORIZE_ERROR);
        }
    }

    @Operation(summary = "注销", description = "[null]登出当前用户")
    @ApiResponse(responseCode = "data", description = "无")
    @GetMapping("/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success("已注销");
    }
}
