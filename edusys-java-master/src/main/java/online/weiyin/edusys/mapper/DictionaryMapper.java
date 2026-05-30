package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.table.Dictionary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典映射表 映射层。
 *
 * @author weiyi
 * @since 2024-04-14
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary> {

}
