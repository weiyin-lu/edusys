package online.weiyin.edusys.util;

import com.mybatisflex.core.query.QueryWrapper;
import online.weiyin.edusys.entity.dto.response.DictionaryParseVO;
import online.weiyin.edusys.entity.table.Dictionary;
import online.weiyin.edusys.entity.table.StudentSource;
import online.weiyin.edusys.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static online.weiyin.edusys.entity.table.table.DictionaryTableDef.DICTIONARY;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname ClothUtil
 * @description 服装信息转换工具类
 */
@Component
public class ClothUtil {

    private final DictionaryService dictionaryService;

    public ClothUtil(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public DictionaryParseVO parse(StudentSource studentSource) {
        // 通过构造方法复制参数中的必要数据
        DictionaryParseVO parseVO = new DictionaryParseVO(studentSource);
        // 转换
        String parseShoes = parseShoes(studentSource.getShoeSize());
        String parseShirt = parseShirt(studentSource.getHeight());
        String parseCloth = parseCloth(studentSource.getHeight());
        // 存储
        parseVO.setParseShoes(parseShoes);
        parseVO.setParseShirt(parseShirt);
        parseVO.setParseCloth(parseCloth);

        return parseVO;
    }

    private String parseShoes(String raw) {
        QueryWrapper wrapper = QueryWrapper.create().where(DICTIONARY.DIC_ID.eq("shoes"));
        List<Dictionary> list = dictionaryService.list(wrapper);
        String parse = ClothUtil.check(raw, list);

        return parse;
    }

    private String parseShirt(String raw) {
        QueryWrapper wrapper = QueryWrapper.create().where(DICTIONARY.DIC_ID.eq("shirt"));
        List<Dictionary> list = dictionaryService.list(wrapper);
        String parse = ClothUtil.check(raw, list);

        return parse;
    }

    private String parseCloth(String raw) {
        QueryWrapper wrapper = QueryWrapper.create().where(DICTIONARY.DIC_ID.eq("cloth"));
        List<Dictionary> list = dictionaryService.list(wrapper);
        String parse = ClothUtil.check(raw, list);

        return parse;
    }

    // 算法：查找与输入值差值最小的字典条目，将转换值保存
    private static String check(String raw, List<Dictionary> list) {
        // 返回的转换结果
        String parse = "目测判断";
        // 检查是否包含有效数据
        if (raw != null) {
            // 输入的数值转换
            double input = Double.parseDouble(raw);
            // 字典内值和输入值的差值，默认是一个足够大数
            double minus = 10000;
            for (Dictionary dic : list) {
                // 字典中保存的参考原始值
                double dicValue = Double.parseDouble(dic.getRawValue());
                // 判断本次循环的差值和记录差值的大小
                if (Math.abs(input - dicValue) <= minus) {
                    // 本次的值更小，就变更转换结果，并更新最小值
                    parse = dic.getParseValue();
                    minus = Math.abs(input - dicValue);
                } else {
                    continue; // 原来的值更小就保持不变
                }
            }
        }
        return parse;
    }
}
