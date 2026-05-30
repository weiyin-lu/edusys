package online.weiyin.edusys.service;

import com.mybatisflex.core.service.IService;
import online.weiyin.edusys.entity.dto.response.DepartmentTreeVO;
import online.weiyin.edusys.entity.table.Department;

import java.util.List;

/**
 *  服务层。
 *
 * @author weiyin lu
 * @since 2023-11-06
 */
public interface DepartmentService extends IService<Department> {

    List<DepartmentTreeVO> getDeptList(String deptCode);
}
