package online.weiyin.edusys.entity.table;

import com.alibaba.excel.annotation.ExcelProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户个人信息 实体类。
 *
 * @author weiyin lu
 * @since 2023-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user_info")
public class UserInfo implements Serializable {

    /**
     * 数据主键
     */
    @Id(keyType = KeyType.Auto)
    private Integer rowKey;
    /**
     * 账号
     */
    @ExcelProperty("账号")
    private String userId;
    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String phone;

    /**
     * 部门
     */
    @ExcelProperty("机构部门")
    private String department;

    /**
     * 岗位及其他
     */
    @ExcelProperty("岗位及其他")
    private String post;

    /**
     * 邮箱
     */
    @ExcelProperty("性别")
    private String sexual;

    /**
     * 头像
     */
    private String image;
}
