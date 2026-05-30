package online.weiyin.edusys.entity.table;

import com.alibaba.excel.annotation.ExcelProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentSource
 * @description studentsource_xxx实体类
 */
@Data
@EqualsAndHashCode
@Table("StudentSource")
public class StudentSource {
    @ExcelProperty("学院")
    @Column("academy")
    private String academy;
    @ExcelProperty("班级")
    @Column("clazz")
    private String clazz;
    @ExcelProperty("姓名")
    @Column("name")
    private String name;
    @ExcelProperty("身份证号")
    @Column("card_id")
    private String cardId;
    @ExcelProperty("性别")
    @Column("sexual")
    private String sexual;
    @ExcelProperty("学号")
    @Column("id")
    private String id;
    @ExcelProperty("备注")
    @Column("description")
    private String description;
    @Column(value = "check_status", onInsertValue = "0")
    private Integer checkStatus;
    @Column("height")
    private String height;
    @Column("weight")
    private String weight;
    @Column("phone")
    private String phone;
    @Column("shoe_size")
    private String shoeSize;
    @Column(value = "receive_status", onInsertValue = "0")
    private Integer receiveStatus;
    @Column("index_id")
    private Integer indexId;
    @ExcelProperty("连队编号")
    @Column("dept_id")
    private String deptId;
}
