package com.vms.cnlearning.mapper;

import com.vms.cnlearning.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源Mapper接口
 */
@Mapper
public interface ResourceMapper {
    
    /**
     * 插入资源
     * @param resource 资源对象
     * @return 影响行数
     */
    int insert(Resource resource);
    
    /**
     * 根据条件查询资源列表
     * @param type 资源类型，可选
     * @return 资源列表
     */
    List<Resource> selectList(@Param("type") String type);
    
    /**
     * 根据ID查询资源
     * @param resId 资源ID
     * @return 资源对象
     */
    Resource selectById(Integer resId);
} 