package online.weiyin.edusys.entity.table;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典映射表 实体类。
 *
 * @author weiyi
 * @since 2024-04-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_dictionary")
public class Dictionary implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 标准集id
     */
    private String dicId;

    /**
     * 标准集含义
     */
    private String description;

    /**
     * 原始值
     */
    private String rawValue;

    /**
     * 映射值
     */
    private String parseValue;

}
