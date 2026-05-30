package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.SourceOperateRecord;
import online.weiyin.edusys.mapper.SourceOperateMapper;
import online.weiyin.edusys.service.SourceOperateRecordService;
import org.springframework.stereotype.Service;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname SourceOperateRecordServiceImpl
 * @description TODO
 */
@Service
public class SourceOperateRecordServiceImpl extends ServiceImpl<SourceOperateMapper, SourceOperateRecord>
        implements SourceOperateRecordService {
}
