package online.weiyin.edusys.entity.dto.response;

import com.alibaba.excel.annotation.ExcelProperty;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import online.weiyin.edusys.entity.table.StudentSource;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname DictionaryParseVO
 * @description 服装信息转换VO
 */
@Schema(name = "服装信息转换VO", description = "转换生成的服装信息实体类")
@Data
@AllArgsConstructor
public class DictionaryParseVO {
    private String academy;
    private String clazz;
    private String name;
    private String sexual;
    private String id;
    private String description;
    private Integer checkStatus;
    private String height;
    private String weight;
    private String phone;
    private String shoeSize;
    private Integer receiveStatus;
    // 下面三个是转换得出的结果
    private String parseShoes;
    private String parseShirt;
    private String parseCloth;

    public DictionaryParseVO(StudentSource source) {
        this.academy = source.getAcademy();
        this.clazz = source.getClazz();
        this.name = source.getName();
        this.sexual = source.getSexual();
        this.id = source.getId();
        this.description = source.getDescription();
        this.checkStatus = source.getCheckStatus();
        this.height = source.getHeight();
        this.weight = source.getWeight();
        this.phone = source.getPhone();
        this.shoeSize = source.getShoeSize();
        this.receiveStatus = source.getReceiveStatus();
    }
}
