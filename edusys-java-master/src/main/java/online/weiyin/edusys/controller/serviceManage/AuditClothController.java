package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.view.ClothAuditView;
import online.weiyin.edusys.mapper.SourceAuditMapper;
import online.weiyin.edusys.service.SourceAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname AuditClothController
 * @description 业务管理：服装业务指标
 */
@Tag(name = "业务管理：服装业务指标")
@RestController
@SaCheckLogin
@RequestMapping("/api/audit/cloth")
public class AuditClothController {

    @Autowired
    private SourceAuditService sourceAuditService;

    @Operation(summary = "获取服装分发指标", description = "[source.cloths]获取服装登记和领取的数据指标（班级精度）")
    @ApiResponse(responseCode = "data", description = "指标核算成功")
    @SaCheckPermission("audit.cloths.show")
    @GetMapping("/getClothAuditAtAcademy/{year}")
    public Result getClothAuditAtAcademy(@PathVariable String year) {
        List<ClothAuditView> viewList = sourceAuditService.getSourceAuditAtAcademy(year);
        return Result.success("指标核算成功", viewList);
    }
}
