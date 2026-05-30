package online.weiyin.edusys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import online.weiyin.edusys.entity.table.Dictionary;
import online.weiyin.edusys.mapper.DictionaryMapper;
import online.weiyin.edusys.service.DictionaryService;
import org.springframework.stereotype.Service;

/**
 * 字典映射表 服务层实现。
 *
 * @author weiyi
 * @since 2024-04-14
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

}
