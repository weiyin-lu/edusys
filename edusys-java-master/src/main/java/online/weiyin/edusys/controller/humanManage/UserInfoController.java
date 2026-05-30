package online.weiyin.edusys.controller.humanManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.core.util.UpdateEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.controller.authorize.AuthController;
import online.weiyin.edusys.entity.dto.request.*;
import online.weiyin.edusys.entity.dto.response.UserInfoListBasicDTO;
import online.weiyin.edusys.entity.table.User;
import online.weiyin.edusys.entity.table.UserInfo;
import online.weiyin.edusys.service.UserInfoService;
import online.weiyin.edusys.service.UserRoleRelevanceService;
import online.weiyin.edusys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.count;
import static online.weiyin.edusys.entity.table.table.DepartmentTableDef.DEPARTMENT;
import static online.weiyin.edusys.entity.table.table.UserInfoTableDef.USER_INFO;
import static online.weiyin.edusys.entity.table.table.UserRoleRelevanceTableDef.USER_ROLE_RELEVANCE;
import static online.weiyin.edusys.entity.table.table.UserTableDef.USER;


/**
 * @ClassName InfoController
 * @Description 人员管理：工作人员管理
 * @Version 1.0.0
 * @Time 2023/11/06 下午 09:50
 * @Author 卢子昂
 */
@Tag(name = "人员管理：工作人员管理")
@RestController
@SaCheckLogin
@RequestMapping("/api/manages/users")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRelevanceService userRoleRelevanceService;

    @Autowired
    private AuthController authController;

    @Value("${spring.servlet.multipart.location}")
    private String uploadUrl;

    @Operation(summary = "查询所有用户列表", description = "[user.basic.show]分页查询全部用户，一页10条")
    @ApiResponse(responseCode = "data", description = "分页信息和用户列表数据")
    @SaCheckPermission("user.basic.show")
    @GetMapping("/getUserInfoByPage/{page}")
    public Result getUserInfoByPage(
            @Parameter(description = "页码")
            @PathVariable Integer page) {
        // 分页对象
        Page<UserInfoListBasicDTO> pageInstance = new Page<>(page, 10);
        // 多表连接条件
        QueryWrapper wrapper = QueryWrapper.create()
                .from(USER_INFO)
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(USER_INFO.DEPARTMENT));
        // 查询
        Page<UserInfoListBasicDTO> userInfoPage = userInfoService.pageAs(pageInstance, wrapper,
                UserInfoListBasicDTO.class);
        // 内部调用AuthController，组装每个用户的角色列表。减少网络请求次数
        for (int i = 0; i < userInfoPage.getRecords().size(); i++) {
            UserInfoListBasicDTO record = userInfoPage.getRecords().get(i);
            Result result = authController.getRoleListByUserId(record.getUserId());
            record.setRoleList((List<RoleDTO>) result.getData());
        }
        // 为每个查询得出的用户装填
        return Result.success("查询成功", userInfoPage);
    }

    // 使用了专用dto返回分页查询结果，可调整每页条目数量
    @Operation(summary = "根据条件模糊查找用户", description = "[user.basic.show]分页查询条件匹配的用户")
    @ApiResponse(responseCode = "data", description = "符合查找条件的数据")
    @SaCheckPermission("user.basic.show")
    @PostMapping("/searchUserInfoListByCondition")
    public Result searchUserInfoListByCondition(@RequestBody UserInfoDTO userInfoDTO) {
        // 构造分页对象
        Page<UserInfoListBasicDTO> page = new Page(userInfoDTO.getPageNumber(), userInfoDTO.getPageSize());
        // 构造查询条件
        QueryWrapper wrapper = QueryWrapper.create()
                .from(USER_INFO)
                .where(USER_INFO.USER_ID.eq(userInfoDTO.getUserId(), StringUtil::isNotBlank))
                .where(USER_INFO.NAME.like(userInfoDTO.getName(), StringUtil::isNotBlank))
                .where(USER_INFO.PHONE.like(userInfoDTO.getPhone(), StringUtil::isNotBlank))
                .where(USER_INFO.DEPARTMENT.like(userInfoDTO.getDepartment(), StringUtil::isNotBlank))
                .where(USER_INFO.POST.like(userInfoDTO.getPost(), StringUtil::isNotBlank))
                .where(USER_INFO.SEXUAL.eq(userInfoDTO.getSexual(), StringUtil::isNotBlank))
                .leftJoin(DEPARTMENT).on(DEPARTMENT.DEPT_CODE.eq(USER_INFO.DEPARTMENT));
        Page<UserInfoListBasicDTO> userInfoPage = userInfoService.pageAs(page, wrapper, UserInfoListBasicDTO.class);
        // 内部调用AuthController，组装每个用户的角色列表。减少网络请求次数
        for (int i = 0; i < userInfoPage.getRecords().size(); i++) {
            UserInfoListBasicDTO record = userInfoPage.getRecords().get(i);
            Result result = authController.getRoleListByUserId(record.getUserId());
            record.setRoleList((List<RoleDTO>) result.getData());
        }
        return Result.success("查询成功", userInfoPage);
    }

    @Operation(summary = "创建单个用户", description = "[user.basic.upload]根据信息创建单个用户")
    @ApiResponse(responseCode = "data", description = "无")
    @SaCheckPermission("user.basic.upload")
    @Transactional
    @PostMapping("/registerAlone")
    public Result registerAlone(@RequestBody UserInfoRegisterDTO reg) {
//        构造对象
        User user = new User(null, reg.getUserId(), SecureUtil.md5(reg.getUserId()));
        UserInfo userInfo = new UserInfo(null, reg.getUserId(), reg.getName(), reg.getPhone(),
                null, reg.getPost(), reg.getSexual(), "/image/default.jpg");
//        在用户表中插入登录的基本信息
        userService.save(user);
//        在个人信息表中插入一条基本的占位记录
        userInfoService.save(userInfo);
        return Result.success("注册成功");
    }

    @Operation(summary = "批量创建用户", description = "[user.basic.upload]解析传入文件批量创建用户")
    @ApiResponse(responseCode = "data", description = "创建完成")
    @SaCheckPermission("user.basic.upload")
    @PutMapping("/registerBatch")
    public Result registerBatch(
            @Parameter(description = "文件")
            @RequestBody MultipartFile file) {
//        文件接收
        File excelFile = new File(uploadUrl + "/excel/" + file.getOriginalFilename());
        try {
            file.transferTo(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failed("上传失败", Code.UPLOAD_ERROR);
        }
        userInfoService.infoLoad(excelFile);
        return Result.success("上传完成");
    }

    @Operation(summary = "修改密码", description = "[user.info.update]解析传入文件批量创建用户")
    @ApiResponse(responseCode = "data", description = "创建完成")
    @SaCheckPermission("user.info.update")
    @PostMapping("/rePassword")
    public Result rePassword(@RequestBody PasswordDTO dto) {
        // 根据账号查询原密码是否正确
        QueryWrapper checkWrapper = QueryWrapper.create()
                .select(count())
                .where(USER.USER_ID.eq(dto.getUsername()))
                .and(USER.PASSWORD.eq(SecureUtil.md5(dto.getOldPwd())));
        Integer checkResult = userService.getOneAs(checkWrapper, Integer.class);
        if (checkResult == 1) {
            // 校验正确则更新
            UpdateChain.of(User.class)
                    .set(User::getPassword, SecureUtil.md5(dto.getNewPwd()))
                    .where(User::getUserId).eq(dto.getUsername())
                    .update();
            return Result.success("密码修改成功");
        } else {
            return Result.failed("旧密码错误", Code.AUTHORIZE_ERROR);
        }
    }

    @Operation(summary = "修改工作人员个人信息", description = "[user.basic.change]修改指定账户的个人信息")
    @ApiResponse(responseCode = "data", description = "更新信息成功")
    @SaCheckPermission("user.basic.change")
    @PutMapping("/updateUserInfoByUserId")
    public Result updateUserInfoByUserId(@RequestBody UserInfo userInfo) {
//        构造更新对象
        UserInfo of = UpdateEntity.of(UserInfo.class);
        of.setName(userInfo.getName());
        of.setPhone(userInfo.getPhone());
        of.setPost(userInfo.getPost());
        of.setSexual(userInfo.getSexual());
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create().where(USER_INFO.USER_ID.eq(userInfo.getUserId()));
        userInfoService.update(of, wrapper);
        return Result.success("更新信息成功");
    }

    @Operation(summary = "删除工作人员账号", description = "[user.basic.change]根据userId删除工作人员账号")
    @ApiResponse(responseCode = "data", description = "删除成功")
    @SaCheckPermission("user.basic.change")
    @Transactional
    @DeleteMapping("/removeUser/{userId}")
    public Result removeUser(
            @Parameter(description = "用户账号")
            @PathVariable String userId) {
        if ("admin".equals(userId)) {
            return Result.failed("无法删除系统账号", Code.REMOVE_ERROR);
        }
        // 删除信息
        userInfoService.remove(QueryWrapper.create().where(USER_INFO.USER_ID.eq(userId)));
        // 删除账号
        userService.remove(QueryWrapper.create().where(USER.USER_ID.eq(userId)));
        // 删除持有的所有角色
        userRoleRelevanceService.remove(QueryWrapper.create().where(USER_ROLE_RELEVANCE.USER_ID.eq(userId)));
        return Result.success("删除成功");
    }

    @Operation(summary = "修改基本信息", description = "[user.info.update]修改当前账户的基本信息")
    @ApiResponse(responseCode = "data", description = "更新信息成功")
    @SaCheckPermission("user.info.update")
    @PutMapping("/setInfo")
    public Result setInfoByUserId(@RequestBody UserInfoSimpleDTO info) {
//        构造插入列表
        UserInfo of = UpdateEntity.of(UserInfo.class);
        of.setName(info.getName());
        of.setSexual(info.getSexual());
        of.setPhone(info.getPhone());
//        构造查询条件
        QueryWrapper wrapper = QueryWrapper.create().where(USER_INFO.USER_ID.eq(StpUtil.getLoginId()));
//        执行
        boolean update = userInfoService.update(of, wrapper);
        if (update) {
            UserInfo one = userInfoService.getOne(wrapper);
            return Result.success("更新信息成功", one);
        } else {
            return Result.failed(Code.UPDATE_ERROR);
        }
    }

    @Operation(summary = "修改个人头像", description = "[user.info.update]修改当前账户的头像")
    @ApiResponse(responseCode = "data", description = "修改头像后的个人信息")
    @SaCheckPermission("user.info.update")
    @PutMapping("/setHead")
    public Result setHead(
            @Parameter(description = "文件")
            @RequestBody MultipartFile file) {
//        文件接收和重命名
        String newFileName = StpUtil.getLoginId() + "_" + UUID.fastUUID() + "." + file.getOriginalFilename().split("\\.")[1];
        try {
            file.transferTo(new File(uploadUrl + "/headerImage/" + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failed("上传失败", Code.UPLOAD_ERROR);
        }
//        把访问的相对路径存储到数据库
        QueryWrapper wrapper = QueryWrapper.create()
                .where(USER_INFO.USER_ID.eq(StpUtil.getLoginId()));
        UserInfo entity = UpdateEntity.of(UserInfo.class);
        entity.setImage("/sources/headerImage/" + newFileName);
        userInfoService.update(entity, wrapper);
//        获取一次新的用户信息
        UserInfo userInfo = userInfoService.getOne(wrapper);
        return Result.success("头像修改成功", userInfo);
    }
}
