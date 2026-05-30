package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.table.RolePermissionRelevance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色-权限关联关系 映射层。
 *
 * @author weiyin lu
 * @since 2023-11-04
 */
@Mapper
public interface RolePermissionRelevanceMapper extends BaseMapper<RolePermissionRelevance> {

}
