package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.dto.request.DistributeStudentDTO;
import online.weiyin.edusys.entity.dto.request.StudentAddDTO;
import online.weiyin.edusys.entity.dto.request.StudentCommitDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchBasicDTO;
import online.weiyin.edusys.entity.table.StudentSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceMapper
 * @description 操作StudentSource_xxx数据源表的mapper
 */
@Mapper
public interface SourceMapper extends BaseMapper<StudentSource> {

    /**
     * 创建一个数据源表（表名根据入学年份生成）
     *
     * @param tableName
     */
    @Update({"CREATE TABLE IF NOT EXISTS ${tableName} (" +
            "academy varchar(100) NULL COMMENT '学院'," +
            "clazz varchar(100) NULL COMMENT '班级'," +
            "card_id varchar(100) NULL COMMENT '身份证号'," +
            "name varchar(100) NULL COMMENT '姓名'," +
            "sexual varchar(100) NULL COMMENT '性别'," +
            "id varchar(100) NOT NULL COMMENT '学号'," +
            "description varchar(100) NULL COMMENT '备注'," +
            "check_status tinyint NOT NULL DEFAULT '0' NULL COMMENT '服装登记状态 0=未登记、1=已登记'," +
            "height varchar(20) NULL COMMENT '身高'," +
            "weight varchar(20) NULL COMMENT '体重'," +
            "phone varchar(100) NULL COMMENT '手机号'," +
            "shoe_size varchar(20) NULL COMMENT '鞋码'," +
            "receive_status tinyint NOT NULL DEFAULT '0' COMMENT '服装领取状态 0=未领取 1=已领取'," +
            "index_id int PRIMARY KEY AUTO_INCREMENT COMMENT '数据索引ID'," +
            "dept_id varchar(20) NULL COMMENT '所在连队（组织机构）'" +
            ")"})
    void createSource(@Param("tableName") String tableName);

    /**
     * 创建一个数据成绩表（表名根据入学年份生成）
     *
     * @param tableName
     */
    @Update({"CREATE TABLE IF NOT EXISTS ${tableName} (" +
            "id varchar(100) NOT NULL COMMENT '学号'," +
            "group_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '队列动作'," +
            "weapon_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '轻武器射击'," +
            "tactical_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '战术动作'," +
            "fight_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '格斗基础'," +
            "rescue_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '战场救护'," +
            "nuclear_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '核生化防护'," +
            "run_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '行军拉练'," +
            "count_score float(10,2) NOT NULL DEFAULT 0.00 COMMENT '出勤考核'" +
            ")"})
    void createScore(@Param("tableName") String tableName);

    /**
     * 清空指定数据源表的数据
     *
     * @param tableName
     */
    @Update({"TRUNCATE TABLE ${tableName}"})
    void clear(@Param("tableName") String tableName);

    /**
     * 修改指定数据源表的特定信息
     *
     * @param info
     * @return
     */
    @Update({"update ${year} set " +
            "height = #{height}, " +
            "weight = #{weight}, " +
            "phone = #{phone}, " +
            "shoe_size = #{shoeSize} " +
            "where id = #{id} and name = #{name}"})
    Integer updateSourceInfoByIdAndName(StudentCommitDTO info);

    /**
     * 修改check_status字段
     *
     * @param year
     * @param id
     * @param status
     * @return
     */
    @Update({"update ${year} set check_status = #{status} where id = #{id}"})
    Integer updateCheckStatusById(String year, String id, String status);

    /**
     * 修改receive_status字段
     *
     * @param year
     * @param id
     * @param status
     * @return
     */
    @Update({"update ${year} set receive_status = #{status} where id = #{id}"})
    Integer updateReceiveStatusById(String year, String id, String status);

    /**
     * 根据学号修改dept_id字段
     *
     * @param dto
     * @return
     */
    Integer updateDeptIdById(DistributeStudentDTO dto);

    /**
     * 根据若干条件修改dept_id字段
     *
     * @param studentSearchBasicDTO
     * @return
     */
    Integer updateDeptIdByCondition(StudentSearchBasicDTO studentSearchBasicDTO);

    /**
     * 根据id删除记录
     *
     * @param year 数据源学年
     * @param id   学号
     * @return
     */
    Integer deleteAloneById(String year, String id);

    /**
     * 添加一条记录
     * @param dto
     * @return
     */
    @Insert({"insert into ${year} (academy,clazz,card_id,name,sexual,id,description) values" +
            "(#{academy}, #{clazz}, #{cardId}, #{name}, #{sexual}, #{id}, #{description})"})
    Integer addInfo(StudentAddDTO dto);
}
