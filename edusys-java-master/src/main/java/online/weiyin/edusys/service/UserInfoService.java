package online.weiyin.edusys.service;

import com.mybatisflex.core.service.IService;
import online.weiyin.edusys.entity.table.UserInfo;

import java.io.File;

/**
 * 用户个人信息 服务层。
 *
 * @author weiyin lu
 * @since 2023-11-06
 */
public interface UserInfoService extends IService<UserInfo> {

    Boolean infoLoad(File file);
}
