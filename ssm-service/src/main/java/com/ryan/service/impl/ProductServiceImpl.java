package com.ryan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryan.dao.ProductMapper;
import com.ryan.enums.ResponseCode;
import com.ryan.interceptor.NullException;
import com.ryan.pojo.Page;
import com.ryan.pojo.ProductDO;
import com.ryan.service.ProductService;
import com.ryan.utils.PageData;
import com.ryan.vo.ProductVo;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YoriChen
 * @date 2018/6/21
 */
@Service
public class ProductServiceImpl extends Page implements ProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageData<List<ProductVo>> findProductListByPage(ProductVo productVo, Integer page) {
        //拷贝DO --> VO
        ProductDO product = new ProductDO();
        BeanUtils.copyProperties(productVo, product);
        try {
            PageHelper.startPage(page, getPageSize());
            List<ProductVo> prodList = productMapper.selectProductListByPage(product);
            PageInfo<ProductVo> pageInfo = new PageInfo<ProductVo>(prodList, getPageSize());
            return new PageData<>(page, getPageSize(), pageInfo.getPages() , getIndexCount(), prodList);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            //转换为运行时异常
            throw new RuntimeException(ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public Boolean insert(ProductVo productVo) {
        ProductDO product = new ProductDO();
        BeanUtils.copyProperties(productVo, product);
        return 0 != productMapper.insert(product) ? true : false;
    }

    @Override
    public ProductVo findProductInfoByPrimaryKey(Integer id) {
        return productMapper.selectProductInfoByPrimaryKey(id);
    }

    @Override
    public Boolean deleteByPrimaryKey(Integer id) {
        return 0 != productMapper.deleteByPrimaryKey(id) ? true : false;
    }

    @Override
    public Boolean updateByPrimaryKey(ProductVo productVo) {
        ProductDO product = new ProductDO();
        BeanUtils.copyProperties(productVo, product);
        return 0 != productMapper.updateByPrimaryKey(product) ? true : false;
    }

}
