package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.User;
import online.weiyin.edusys.mapper.UserMapper;
import online.weiyin.edusys.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务层实现。
 *
 * @author weiyin lu
 * @since 2023-11-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
