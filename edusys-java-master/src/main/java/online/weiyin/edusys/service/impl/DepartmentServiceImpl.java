package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.dto.response.DepartmentTreeVO;
import online.weiyin.edusys.entity.table.Department;
import online.weiyin.edusys.mapper.DepartmentMapper;
import online.weiyin.edusys.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author weiyin lu
 * @since 2023-11-06
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentTreeVO> getDeptList(String deptCode) {
//        调用mapper
        List<DepartmentTreeVO> list = departmentMapper.getDeptList(deptCode);
//        启动递归
        List<DepartmentTreeVO> collect = list.stream()
//                过滤 从查询列表的根节点开始遍历
                .filter(a -> a.getDeptCode().equals(deptCode))
//                批量操作 为每个节点插入其子节点
                .peek(m -> m.setChildList(listToTree(m, list)))
//                转换为列表
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 链表转树（递归）
     * @param root 父节点
     * @param list 子节点（列表）
     * @return
     */
    public static List<DepartmentTreeVO> listToTree(DepartmentTreeVO root, List<DepartmentTreeVO> list) {
        return list.stream()
//                过滤 当前节点下的子节点
                .filter(a -> Objects.equals(a.getSuperior(), root.getDeptCode()))
//                批量操作 为每个子节点插入其子节点
                .peek(m -> m.setChildList(listToTree(m, list)))
//                转换为列表
                .collect(Collectors.toList());
    }
}
