package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Code;
import online.weiyin.edusys.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname FileController
 * @description TODO
 */
@Tag(name = "业务管理：指导文件管理")
@RestController
@SaCheckLogin
@RequestMapping("/api/file")
public class FileController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadUrl;

    @Operation(summary = "更新文件",
            description = "[file.upload]更换指定类目的指导文件" +
                    "（JXDG=教学大纲 PBBF=评比办法 SSFA=实施方案 YJYA=应急预案）")
    @SaCheckPermission("file.upload")
    @PutMapping("/fileLoad/{fileName}")
    public Result fileLoad(
            @Parameter(description = "文件")
            @RequestBody MultipartFile file,
            @Parameter(description = "文件类目")
            @PathVariable String fileName) {
//        解析文件名，索引1是扩展名
        String[] split = file.getOriginalFilename().split("\\.");
//        文件接收
        File newFile = new File(uploadUrl + "/document/" + fileName + "." + split[1]);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failed("上传失败", Code.UPLOAD_ERROR);
        }
        return Result.success("上传成功，刷新后查看");
    }
}
