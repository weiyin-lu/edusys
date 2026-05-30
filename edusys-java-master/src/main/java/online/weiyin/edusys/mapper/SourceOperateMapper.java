package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.table.SourceOperateRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @classname SourceOperateMapper
 * @description 操作source_operate_record数据源操作记录表的mapper
 * @version 1.0.0
 * @author 卢子昂
 */
@Mapper
public interface SourceOperateMapper extends BaseMapper<SourceOperateRecord> {
}
