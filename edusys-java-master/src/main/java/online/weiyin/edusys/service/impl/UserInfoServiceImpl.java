package online.weiyin.edusys.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.StudentSource;
import online.weiyin.edusys.entity.table.UserInfo;
import online.weiyin.edusys.listener.SourceListener;
import online.weiyin.edusys.listener.UserListener;
import online.weiyin.edusys.mapper.UserInfoMapper;
import online.weiyin.edusys.mapper.UserMapper;
import online.weiyin.edusys.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 用户个人信息 服务层实现。
 *
 * @author weiyin lu
 * @since 2023-11-06
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Boolean infoLoad(File file) {
        // 从excel导入user表和userinfo表
        EasyExcel.read(file, UserInfo.class, new UserListener(userMapper, userInfoMapper))
                .sheet().doRead();
        return true;
    }
}
