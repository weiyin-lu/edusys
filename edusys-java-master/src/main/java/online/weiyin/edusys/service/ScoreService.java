package online.weiyin.edusys.service;

import com.mybatisflex.core.service.IService;
import online.weiyin.edusys.entity.dto.request.StudentSearchScoreFileBO;
import online.weiyin.edusys.entity.dto.request.StudentSearchScoreFileDTO;
import online.weiyin.edusys.entity.dto.request.StudentUpdateScoreBasicDTO;
import online.weiyin.edusys.entity.table.StudentSourceScore;

import java.util.List;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname ScoreService
 * @description 学生成绩表 service
 */
public interface ScoreService extends IService<StudentSourceScore> {
    Boolean updateScoreByIDAndRaw(StudentUpdateScoreBasicDTO dto);

    String writeScoreFile(StudentSearchScoreFileDTO dto, List<StudentSearchScoreFileBO> studentList);
}
