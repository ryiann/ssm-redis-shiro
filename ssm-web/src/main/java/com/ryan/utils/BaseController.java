package com.ryan.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public class BaseController {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    protected HttpSession session;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 将对象转换成JSON字符串，并响应回前台
     * @param object
     * @throws IOException
     */
    public void writeJson(Object object) {
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * View
     * @param view
     * @return
     */
    protected String View(String view) {
        return view;
    }

    /**
     * View
     * @param view
     * @param model
     * @param object
     * @return
     */
    protected String View(String view, Model model, Object object) {
        model.addAttribute("model",object);
        return view;
    }

    /**
     * Redirect
     * @param url
     * @return
     */
    protected String RedirectTo(String url) {
        return "redirect:" + url;
    }
}