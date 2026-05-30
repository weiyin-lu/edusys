package online.weiyin.edusys.mapper;

import com.mybatisflex.core.BaseMapper;
import online.weiyin.edusys.entity.dto.request.StudentUpdateScoreBasicDTO;
import online.weiyin.edusys.entity.table.StudentSourceScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceMapper
 * @description 操作StudentScore_score_xxx数据源表的mapper
 */
@Mapper
public interface ScoreMapper extends BaseMapper<StudentSourceScore> {

    Integer updateScoreByIDAndRaw(StudentUpdateScoreBasicDTO dto);

    @Update({"update ${year} set count_score = ${score} where id = ${id}"})
    Integer updateCountScoreById(String year,Double score,String id);
}
