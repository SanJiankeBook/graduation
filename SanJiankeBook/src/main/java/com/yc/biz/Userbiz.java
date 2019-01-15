package com.yc.biz;

import java.util.List;
import java.util.Map;

import com.yc.bean.User;

public interface Userbiz {
	//注册成为用户需要一个id号
	public Integer InsertUser(Object obj);
	//注册用户
	void save(User use);
	
	//用户登录
	List<User> userLogin(String uname, String upassword);
	
	//查询所有用户
	List<User> findUser();
	
	//根据id查询用户
	List<User> findUserById(int id);
	
	//分页查询用户
	List<User> findUserByPage(Integer start,Integer end);
	
	//删除用户
	void DelUser(Integer id);
	
	//根据用户名查找用户信息
	List<User> findUserName(String u_number);
	//账号注册前的验证
	public List<User> findUserInfo(User userlist);
	//账号注册
	public void addUser(User userlist);
	//密码修改
	public void updateUser(User user);
	//用户信息修改
	public void updateUserInfo(User user);
	

}
