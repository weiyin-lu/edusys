package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.Role;
import online.weiyin.edusys.mapper.RoleMapper;
import online.weiyin.edusys.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务层实现。
 *
 * @author weiyin lu
 * @since 2023-11-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
