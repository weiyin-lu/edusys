package online.weiyin.edusys.listener;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.mybatisflex.core.table.TableManager;
import online.weiyin.edusys.entity.table.User;
import online.weiyin.edusys.entity.table.UserInfo;
import online.weiyin.edusys.mapper.UserInfoMapper;
import online.weiyin.edusys.mapper.UserMapper;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceListener
 * @description easyExcel用于读excel数据的监听器
 */
public class UserListener implements ReadListener<UserInfo> {

    private static final int BATCH_COUNT = 100; // 缓存列表的最大缓存数量
    // UserInfo缓存列表
    private List<UserInfo> userInfoCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    //User缓存列表
    private List<User> userCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private int operateCount = 0; // 操作量计数
    private UserMapper userMapper;
    private UserInfoMapper userInfoMapper;

    public UserListener(UserMapper userMapper, UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public void invoke(UserInfo userInfo, AnalysisContext analysisContext) {
        // 解析传入的excel数据源
        // userinfo的列表填写默认头像后直接加入
        userInfo.setImage("/image/default.jpg");
        userInfoCachedDataList.add(userInfo);
        // user列表根据userinfo构造一个，此处基于userinfo生成，无需对user实体类做excel映射
        User user = new User(null, userInfo.getUserId(), SecureUtil.md5(userInfo.getUserId()));
        userCachedDataList.add(user);
        // 操作计数
        operateCount++;
        if (userInfoCachedDataList.size() >= BATCH_COUNT) {
            saveData(); // 存储数据
            // 清理缓存列表
            userInfoCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            userCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData(); // 存储数据
        System.out.println("数据存储完成，共" + operateCount + "条");
    }

    /**
     * 批量保存数据
     */
    private void saveData() {
        // 每次把缓存列表里的数据批量保存
        try {
            userInfoMapper.insertBatch(userInfoCachedDataList);
            userMapper.insertBatch(userCachedDataList);
        } finally {
            TableManager.clear();
        }
    }
}
