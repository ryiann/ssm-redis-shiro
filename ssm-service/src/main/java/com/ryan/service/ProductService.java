package com.ryan.service;

import com.ryan.utils.PageData;
import com.ryan.vo.ProductVo;

import java.util.List;

/**
 * @author YoriChen
 * @date 2018/6/25
 */
public interface ProductService {

    /**
     * 分页查询 产品数据
     * @param product
     * @param page
     * @return
     */
    PageData<List<ProductVo>> findProductListByPage(ProductVo product, Integer page);

    /**
     * 添加 产品数据
     * @param product
     * @return
     */
    Boolean insert(ProductVo product);

    /**
     * 查询 产品信息
     * @param id
     * @return
     */
    ProductVo findProductInfoByPrimaryKey(Integer id);

    /**
     * 删除 产品信息
     * @param id
     * @return
     */
    Boolean deleteByPrimaryKey(Integer id);

    /**
     * 修改 产品信息
     * @param product
     * @return
     */
    Boolean updateByPrimaryKey(ProductVo product);
}
