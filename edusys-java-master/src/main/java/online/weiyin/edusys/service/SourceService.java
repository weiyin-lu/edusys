package online.weiyin.edusys.service;

import com.mybatisflex.core.service.IService;
import online.weiyin.edusys.entity.dto.request.DistributeStudentDTO;
import online.weiyin.edusys.entity.dto.request.StudentAddDTO;
import online.weiyin.edusys.entity.dto.request.StudentCommitDTO;
import online.weiyin.edusys.entity.dto.request.StudentSearchBasicDTO;
import online.weiyin.edusys.entity.table.SourceOperateRecord;
import online.weiyin.edusys.entity.table.StudentSource;

import java.io.File;

/**
 * @classname SourceService
 * @description SourceMapper的业务层接口
 * @version 1.0.0
 * @author 卢子昂
 */
public interface SourceService extends IService<StudentSource> {
    /**
     * 创建数据源并传入数据
     * @param record 数据源操作记录
     * @param file 数据源文件
     * @return
     */
    Boolean operateSource(SourceOperateRecord record, File file);

    /**
     * 更新数据源中的登记信息
     * @param info
     * @return
     */
    Integer updateSourceInfoByIdAndName(StudentCommitDTO info);

    /**
     * 更新登记状态
     * @param year 入学年份（表名）
     * @param id 学号
     * @param status 新的状态（1/0）
     * @return
     */
    Integer updateCheckStatusById(String year, String id, String status);
    /**
     * 更新领取状态
     * @param year 入学年份（表名）
     * @param id 学号
     * @param status 新的状态（1/0）
     * @return
     */
    Integer updateReceiveStatusById(String year, String id, String status);

    /**
     * 根据复杂条件更新学生所属组织（连队）
     * @param dto 更新条件和更新值
     */
    Boolean updateDeptIdByCondition(StudentSearchBasicDTO dto);

    /**
     * 根据id更新学生所属组织（连队）
     * @param dto
     * @return
     */
    Boolean updateDeptIdById(DistributeStudentDTO dto);
    /**
     * 根据id删除一个学生
     * @param year
     * @param id
     * @return
     */
    Boolean deleteById(String year, String id);

    /**
     * 添加一个学生
     * @param dto
     * @return
     */
    Boolean addForYear(StudentAddDTO dto);

}
