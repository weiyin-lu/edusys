package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.UserRoleRelevance;
import online.weiyin.edusys.mapper.UserRoleRelevanceMapper;
import online.weiyin.edusys.service.UserRoleRelevanceService;
import org.springframework.stereotype.Service;

/**
 * 用户-角色关联关系 服务层实现。
 *
 * @author weiyin lu
 * @since 2023-11-04
 */
@Service
public class UserRoleRelevanceServiceImpl extends ServiceImpl<UserRoleRelevanceMapper, UserRoleRelevance> implements UserRoleRelevanceService {

}
