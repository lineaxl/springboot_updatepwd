package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.IUserDao;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	public User getUserByUsername(String username) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username", username);
		return userDao.selectOne(queryWrapper);
	}


	@Override
	public User addUser(User user) {
		userDao.insert(user);
		return user;
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String passsword) {
	    QueryWrapper queryWrapper = new QueryWrapper();
	    queryWrapper.eq("username", username);
	    queryWrapper.eq("password", passsword);
	    return userDao.selectOne(queryWrapper);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateById(user);
	}

	@Override
	public void sendUpdatePwdUrl(User user) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<html>");
		sBuilder.append("<head>");
		sBuilder.append("<title>xxx</title>");
		sBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		sBuilder.append("<style type=\"text/css\">");
		sBuilder.append(
				".post {margin-bottom: 20px;background: #5BCE9E;}.title {padding: 5px 20px;}.posted {padding: 0 0 0 20px;font-size: small;}.story {padding: 20px;}.meta {height: 60px;padding: 40px 0 0 0;}.meta p {margin: 0;padding: 0 20px 0 0;	text-align: right;}");
		sBuilder.append("</style>");
		sBuilder.append("</head>");
		sBuilder.append("<body>");
		sBuilder.append("<div>");
		sBuilder.append("<div class=\"post\">");
		sBuilder.append("<h3 class=\"posted\">这是一条修改密码的邮件</h3>");
		sBuilder.append("<div class=\"story\"><a href='http://localhost:8080/userController/toUpdatePwd?username=" + user.getUsername()
				+ "'>立即前往修改密码！</a></div>");
		sBuilder.append("</div>");
		sBuilder.append("</div>");
		sBuilder.append("</body>");
		sBuilder.append("</html>");
		Map<String, Object> map = new HashMap<>();
		map.put("email", user.getEmail());
		map.put("title", "修改密码");
		map.put("sBuilder", sBuilder);
		SendEmail.sendEmail(map);
	}

	@Override
	public void userResiter(String email, String code) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<html>");
		sBuilder.append("<head>");
		sBuilder.append("<title>xxx</title>");
		sBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		sBuilder.append("<style type=\"text/css\">");
		sBuilder.append(
				".post {margin-bottom: 20px;background: #5BCE9E;}.title {padding: 5px 20px;}.posted {padding: 0 0 0 20px;font-size: small;}.story {padding: 20px;}.meta {height: 60px;padding: 40px 0 0 0;}.meta p {margin: 0;padding: 0 20px 0 0;	text-align: right;}");
		sBuilder.append("</style>");
		sBuilder.append("</head>");
		sBuilder.append("<body>");
		sBuilder.append("<div>");
		sBuilder.append("<div class=\"post\">");
		sBuilder.append("<h3 class=\"posted\">这是一条注册验证的邮件</h3>");
		sBuilder.append("<div class=\"story\">注册的验证码是：" + code + "</div>");
		sBuilder.append("</div>");
		sBuilder.append("</div>");
		sBuilder.append("</body>");
		sBuilder.append("</html>");
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("title", "用户注册");
		map.put("sBuilder", sBuilder);
		SendEmail.sendEmail(map);
	}

}
