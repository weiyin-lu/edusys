package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.Menu;
import online.weiyin.edusys.mapper.MenuMapper;
import online.weiyin.edusys.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单信息表 服务层实现。
 *
 * @author weiyin lu
 * @since 2023-11-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
