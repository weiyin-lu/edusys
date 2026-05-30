package online.weiyin.edusys.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.mybatisflex.core.table.TableManager;
import online.weiyin.edusys.entity.table.StudentSourceScore;
import online.weiyin.edusys.entity.table.StudentSource;
import online.weiyin.edusys.mapper.ScoreMapper;
import online.weiyin.edusys.mapper.SourceMapper;

import java.util.List;

/**
 * @classname SourceListener
 * @description easyExcel用于读excel数据的监听器
 * @version 1.0.0
 * @author 卢子昂
 */
public class SourceListener implements ReadListener<StudentSource> {

    private static final int BATCH_COUNT = 100; // 缓存列表的最大缓存数量
    private List<StudentSource> sourceCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT); //缓存列表
    private List<StudentSourceScore> scoreCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    //缓存列表
    private int operateCount = 0; // 操作量计数
    private SourceMapper sourceMapper;
    private ScoreMapper scoreMapper;
    private String tableName;

    public SourceListener(SourceMapper sourceMapper,ScoreMapper scoreMapper,String tableName) {
        this.tableName = tableName;
        this.sourceMapper = sourceMapper;
        this.scoreMapper = scoreMapper;
    }

    @Override
    public void invoke(StudentSource studentSource, AnalysisContext analysisContext) {
        // 解析传入的excel数据源
        // System.out.println("解析到数据：" + JSONUtil.toJsonStr(excelDO));
        sourceCachedDataList.add(studentSource);
        scoreCachedDataList.add(new StudentSourceScore(studentSource.getId(),0.00,0.00,0.00,0.00,0.00,0.00,
                0.00,0.00));
        operateCount++;
        if (sourceCachedDataList.size() >= BATCH_COUNT) {
            saveData(); // 存储数据
            // 清理缓存列表
            sourceCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            scoreCachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
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
            TableManager.setHintTableMapping("StudentSource", "StudentSource_" + tableName);
            TableManager.setHintTableMapping("StudentSource_score", "StudentSource_score_" + tableName);
            sourceMapper.insertBatch(sourceCachedDataList);
            scoreMapper.insertBatch(scoreCachedDataList);
        } finally {
            TableManager.clear();
        }
    }
}
