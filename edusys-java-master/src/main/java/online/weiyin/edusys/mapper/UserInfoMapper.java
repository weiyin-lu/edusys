package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.table.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户个人信息 映射层。
 *
 * @author weiyin lu
 * @since 2023-11-06
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
