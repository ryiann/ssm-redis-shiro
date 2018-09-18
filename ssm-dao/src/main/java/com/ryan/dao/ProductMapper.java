package com.ryan.dao;

import com.ryan.pojo.ProductDO;
import com.ryan.vo.ProductVo;

import java.util.List;

/**
 * 产品数据访问层
 * @author YoriChen
 * @date 2018/5/21
 */
public interface ProductMapper {

    /**
     * 分页查询 产品数据
     * @param product
     * @return
     */
    List<ProductVo> selectProductListByPage(ProductDO product);

    /**
     * 查询 产品信息
     * @param id
     * @return
     */
    ProductVo selectProductInfoByPrimaryKey(Integer id);

    /**
     * 添加 产品数据
     * @param product
     * @return
     */
    int insert(ProductDO product);

    /**
     * 删除 产品信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 修改 产品信息
     * @param product
     * @return
     */
    int updateByPrimaryKey(ProductDO product);

}