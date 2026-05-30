package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.table.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表 映射层。
 *
 * @author weiyin lu
 * @since 2023-11-04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
