package com.newcoder.community.controller;

import com.newcoder.community.Dao.AlphaDao;
import com.newcoder.community.service.AlphaService;
import com.newcoder.community.util.CommunityUtil;
import jdk.nashorn.internal.codegen.CompileUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller// 注册成一个Bean
@RequestMapping("/alpha")// URL 类级别映射
public class AlphaContrller {
    @RequestMapping("/hello")//URL的方法级映射
    @ResponseBody  //声明返回的是一个字符串
    public String sayHello(){
        return "Hello SpringBoot!";
    }


    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/data")//URL的方法级映射
    @ResponseBody  //声明返回的是一个字符串
    public String getData(){
        return  alphaService.find();
    }



    //比较复杂的获取request和response请求
    @RequestMapping("http")
    public void http(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(httpServletRequest.getMethod());
        System.out.println(httpServletRequest.getServletPath());
        Enumeration<String> enumeration = httpServletRequest.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = httpServletRequest.getHeader(name);
            System.out.println("name=" + name + " value=" + value);
        }
        System.out.println(httpServletRequest.getParameter("code"));

        //返回响应数据
        httpServletResponse.setContentType("text/html;charset=utf-8");
        try(
                PrintWriter printWriter = httpServletResponse.getWriter();
        ) {
            printWriter.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //GET请求
    //student?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10")int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some sturents";
    }
    // student/123  //参数成为路径的一部分
    @RequestMapping(path = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public  String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }


    //Post请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name + " " + age);
        return "success";
    }

    //相应html数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return  mav;
    }


    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","Anhui University");
        model.addAttribute("age",95);
        return "demo/view";
    }

    //相应json数据 {异步请求}
    // java对象-----JSON字符串------JS对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",18);
        emp.put("salary",8000.0);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object> > getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",18);
        emp.put("salary",8000.0);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李四");
        emp.put("age",28);
        emp.put("salary",1000.0);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age",48);
        emp.put("salary",80000.0);
        list.add(emp);


        return list;
    }

    //cookie 示例
    @RequestMapping(value = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效范围
        cookie.setPath("/community/alpha");
        //cookie设置生存时间, 单位是秒
        cookie.setMaxAge(60*10);
        //f放入 response
        response.addCookie(cookie);
        return "set cookie";
    }

//    cookie 示例
    @RequestMapping(value = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "get cookie";
    }

    //session 实例
    @RequestMapping(value = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","test");
        return "set Session";
    }

    //session 实例
    @RequestMapping(value = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id")+ " " + session.getAttribute("name"));
        return "get Session";
    }


    //ajax实例
    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age){
        System.out.println(name);
        System.out.println(age);


        return CommunityUtil.getJSONString(0,"操作成功");
    }

}
