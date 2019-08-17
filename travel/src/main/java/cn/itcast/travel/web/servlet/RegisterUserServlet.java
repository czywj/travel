package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //验证码校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String s = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(s == null || s.equalsIgnoreCase(check) != true){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //序列化信息为josn数据
            ObjectMapper mapper = new ObjectMapper();
            String josn = mapper.writeValueAsString(info);
            //设置josn数据编码application/json
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(josn);
            return;

        }
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //处理数据
        UserService userService = new UserServiceImpl();
        boolean flag = userService.register(user);
        ResultInfo info = new ResultInfo();

        if (flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("用户名重复注册失败!");
        }
        //序列化信息为josn数据
        ObjectMapper mapper = new ObjectMapper();
        String josn = mapper.writeValueAsString(info);
        //设置josn数据编码
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(josn);
    }
}
