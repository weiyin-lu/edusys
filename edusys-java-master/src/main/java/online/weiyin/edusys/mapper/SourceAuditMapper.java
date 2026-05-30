package online.weiyin.edusys.mapper;

import online.weiyin.edusys.entity.view.ClothAuditView;
import online.weiyin.edusys.entity.view.DistributeDeptView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceAuditMapper
 * @description 服装领取指标mapper
 */
@Mapper
public interface SourceAuditMapper {
    @Select("select s.academy" +
            ",count(1) as total" +
            ",sum(case when s.check_status = 0 then 1 else 0 end) as not_check" +
            ",sum(case when s.check_status = 1 then 1 else 0 end) as checked" +
            ",sum(case when s.receive_status = 0 then 1 else 0 end) as not_receive" +
            ",sum(case when s.receive_status = 1 then 1 else 0 end) as received " +
            "from ${year} as s " +
            "group by s.academy")
    List<ClothAuditView> selectClothAuditAtAcademy(@Param("year") String year);

    @Select("select " +
            "ifnull(sd.dept_name,'未分配连队') as dept_name" +
            ",count(distinct s.id) as student_total" +
            ",ifnull(group_concat(distinct concat(sui.name,'(',sui.sexual,')')),'未分配教官') as user_list " +
            ",count(distinct sui.name) as user_otal " +
            "from ${year} s " +
            "left join sys_department sd on sd.dept_code = s.dept_id " +
            "left join sys_user_info sui on sd.dept_code = sui.department " +
            "group by sd.dept_code " +
            "order by sd.dept_name")
    List<DistributeDeptView> selectDistributeAtDept(@Param("year") String year);



}
