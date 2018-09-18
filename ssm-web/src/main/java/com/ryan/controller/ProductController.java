package com.ryan.controller;

import com.ryan.service.ProductService;
import com.ryan.utils.BaseController;
import com.ryan.utils.MapUtil;
import com.ryan.utils.PageData;
import com.ryan.vo.ProductVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

/**
 * 处理产品类别业务相关
 * @author YoriChen
 * @date 2018/6/25
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String list(Model model, ProductVo product,
                       @RequestParam(value="p", defaultValue="1")Integer page){
        PageData<List<ProductVo>> productList = productService.findProductListByPage(product, page);
        return View("list",model,productList);
    }

    @GetMapping("/create")
    public String create(){
        return View("create");
    }

    @PostMapping("/create")
    public String createSubmit(Model model, @Valid ProductVo product, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            productService.insert(product);
            return RedirectTo("/product/list");
        }else{
            model.addAttribute("errors", MapUtil.objectErrorsToMap(bindingResult.getAllErrors()));
        }
        return View("create",model,product);
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model,@PathVariable("id") Integer id) {
        ProductVo product = productService.findProductInfoByPrimaryKey(id);
        return View("delete",model,product);
    }

    @GetMapping("/deleteSubmit/{id}")
    public String deleteSubmit(@PathVariable("id") Integer id){
        productService.deleteByPrimaryKey(id);
        return RedirectTo("/product/list");
    }

    @GetMapping("/details/{id}")
    public String details(Model model,@PathVariable("id") Integer id) {
        ProductVo product = productService.findProductInfoByPrimaryKey(id);
        return View("details",model,product);
    }

    @GetMapping("/update/{id}")
    public String update(Model model,@PathVariable("id") Integer id) {
        ProductVo product = productService.findProductInfoByPrimaryKey(id);
        return View("edit",model,product);
    }

    @PostMapping("/update")
    public String updateSubmit(Model model, @Validated ProductVo product, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            productService.updateByPrimaryKey(product);
            return RedirectTo("/product/list");
        }else{
            model.addAttribute("errors", MapUtil.objectErrorsToMap(bindingResult.getAllErrors()));
        }

        return View("edit",model,product);
    }

}
