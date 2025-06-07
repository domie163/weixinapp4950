package com.dao;

import com.entity.BianminEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.BianminView;

/**
 * 便民服务 Dao 接口
 *
 * @author 
 */
public interface BianminDao extends BaseMapper<BianminEntity> {

   List<BianminView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
