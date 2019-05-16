package com.qf.controller;

import com.google.gson.Gson;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.utils.CreateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/userController")
public class UserController {
	
	@Autowired
	private IUserService userService;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 用户密码
     * @param model
     * @return
     */
	@RequestMapping(value="/getUserByUsernameAndPassword")
	public String getUserByUsernameAndPassword(String username, String password, Model model){
		User user = userService.getUserByUsernameAndPassword(username, password);
		System.out.println(user);
		model.addAttribute("user", user);
		return "welcome";
	}

    /**
     * 查询到的用户邮箱，并替换一些字符显示到页面
     * @param username
     * @param model
     * @return
     */
	@RequestMapping(value="/sendController")
	public String sendController(String username, Model model){
		String replace = "****";
		User user = userService.getUserByUsername(username);
		userService.sendUpdatePwdUrl(user);
		StringBuffer stringBuffer = new StringBuffer(user.getEmail());
		stringBuffer.replace(3, 7, replace);
		model.addAttribute("stringBuffer", stringBuffer);
		return "email";
	}

    /**
     * 用户的密码修改
     * @param user
     * @return
     */
	@RequestMapping(value="/updateUser")
	public String updateUser(User user){
		User userByUsername = userService.getUserByUsername(user.getUsername());
		userByUsername.setPassword(user.getPassword());
		userService.updateUser(userByUsername);
		return "updateSuccess";
	}

    /**
     * 判断用户名是否存在
     * @param username
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value="/isUser")
	@ResponseBody
	public String isUser(String username, HttpServletResponse response) throws IOException{
		User user = userService.getUserByUsername(username);
		String typeCode = "";
		if (user != null) {
			typeCode="1";
		}
		else {
			typeCode="0";
		}
		return new Gson().toJson(typeCode);
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/addUser")
	public String addUser(User user, String code, Model model, HttpServletRequest request){
		String code2 = (String) request.getSession().getAttribute("code");
		if (code2.equals(code)) {
			userService.addUser(user);
			return "registerSuccess";
		}
		else {
			request.getSession().setAttribute("msg", "验证码错误");
			return "register";
		}
	}

	@RequestMapping(value="/createNumber")
	public void createNumber(String email, HttpServletRequest request){
		String code = CreateNumber.createNumber();
		request.getSession().setAttribute("code", code);
		userService.userResiter(email, code);
	}

    /**
     * 跳转到忘记密码页面
     * @return
     */
	@RequestMapping(value = "/toForgetPwd")
	public String toForgetPwd(){
	    return "forgetPwd";
    }

	/**
	 * 跳转到重置密码页面
	 * @return
	 */
	@RequestMapping(value = "/toUpdatePwd")
    public String toUpdatePwd(String username, Model model){
		model.addAttribute("username", username);
		return "updatePwd";
	}

	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping(value = "/toRegister")
	public String toRegister(){
		return "register";
	}
	
}
