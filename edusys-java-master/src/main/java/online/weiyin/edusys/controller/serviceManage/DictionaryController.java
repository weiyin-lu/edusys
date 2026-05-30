package online.weiyin.edusys.controller.serviceManage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import online.weiyin.edusys.common.Result;
import online.weiyin.edusys.entity.dto.request.DictionaryDTO;
import online.weiyin.edusys.entity.dto.response.DictionaryTypeVO;
import online.weiyin.edusys.entity.table.Dictionary;
import online.weiyin.edusys.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.distinct;
import static online.weiyin.edusys.entity.table.table.DictionaryTableDef.DICTIONARY;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname DictionaryController
 * @description 业务管理：字典管理
 */
@Tag(name = "业务管理：字典管理")
@RestController
@SaCheckLogin
@RequestMapping("/api/services/dictionarys")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @Operation(summary = "查询字典转换表内容", description = "[source.score.dictionary]查询指定字典id下的信息")
    @ApiResponse(responseCode = "data", description = "转换表内数据列表")
    @SaCheckPermission("source.cloths.dictionary")
    @GetMapping("/searchDicList/{dicId}")
    public Result searchDicList(
            @Parameter(description = "字典id")
            @PathVariable String dicId) {
        QueryWrapper wrapper = QueryWrapper.create().where(DICTIONARY.DIC_ID.eq(dicId));
        List<Dictionary> list = dictionaryService.list(wrapper);
        return Result.success("查询成功", list);
    }

    @Operation(summary = "修改字典值内容", description = "[source.score.dictionary]根据数据id修改字典值")
    @ApiResponse(responseCode = "data", description = "修改成功")
    @SaCheckPermission("source.cloths.dictionary")
    @PutMapping("/updateDicById")
    public Result updateDicById(@RequestBody DictionaryDTO dictionaryDTO) {
        // 查询条件
        QueryWrapper wrapper = QueryWrapper.create().where(DICTIONARY.ID.eq(dictionaryDTO.getId()));
        // 更新对象
        Dictionary of = UpdateEntity.of(Dictionary.class);
        of.setRawValue(dictionaryDTO.getRawValue());
        of.setParseValue(dictionaryDTO.getParseValue());
        // 执行
        dictionaryService.update(of, wrapper);
        return Result.success("修改成功");
    }

    @Operation(summary = "添加字典字段", description = "[source.score.dictionary]添加一个字典字段")
    @ApiResponse(responseCode = "data", description = "添加成功")
    @SaCheckPermission("source.cloths.dictionary")
    @PutMapping("/addDic")
    public Result addDic(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return Result.success("添加成功");
    }

    @Operation(summary = "删除一个字典字段", description = "[source.score.dictionary]根据数据id删除字典字段")
    @ApiResponse(responseCode = "data", description = "删除成功")
    @SaCheckPermission("source.cloths.dictionary")
    @DeleteMapping("/removeDicById/{id}")
    public Result removeDicById(
            @Parameter(description = "字典的数据id")
            @PathVariable String id) {
        dictionaryService.remove(QueryWrapper.create().where(DICTIONARY.ID.eq(id)));
        return Result.success("删除成功");
    }

}
