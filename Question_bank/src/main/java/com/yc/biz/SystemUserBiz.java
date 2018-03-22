package com.yc.biz;

import java.util.ArrayList;
import java.util.List;

import com.yc.po.SystemUser;
import com.yc.vo.Page;

public interface SystemUserBiz {
	/**
	 * 通过用户名和密码判断用户是否存在
	 * 
	 * @param userName
	 *          String
	 * @return Boolean
	 * @throws Exception
	 */
	public boolean isExist(String userName, String pwd);

	/**
	 * 通过用户名判断此用户是否存在
	 * 
	 * @param systemUser
	 *          SystemUser
	 * @return int
	 * @throws SQLException
	 */
	public boolean isExist(String userName) ;

	/**
	  删除用户,参数为 systemUser
	 * 
	 * @param systemUser
	 *          SystemUser
	 * @return int
	 * @throws SQLException
	 * @throws Exception
	 */
	public int deleteUser(SystemUser systemUser);

	// ================================updataUser===========================================
	/**
	 * 3月22日 更新用户信息,参数为systemUser,判断systemUser中的属性字段不为空的为更新条件
	 * 
	 * @param systemUser
	 *          SystemUser
	 * @return int
	 * @throws SQLException
	 */
	public int updateUserPassword(SystemUser systemUser);
	
	// ================================updataUser===========================================
	/**
	 * 3月22日 更新用户信息,参数为systemUser,判断systemUser中的属性字段不为空的为更新条件
	 * 
	 * @param systemUser
	 *          SystemUser
	 * @return int
	 * @throws SQLException
	 */
	public int updateSystemUser(SystemUser systemUser);

	// ==============================addUser=============================================
	/**
	 * 3月22日 添加用户,参数为systemUser
	 * 
	 * @return ArrayList
	 * @throws SQLException
	 */
	public int addUser(SystemUser systemUser);
	// ==============================searchUser=============================================
	/**
	 * 3月22日 查询所有用户
	 * 
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<SystemUser> searchUser();

	// ============================searchUser===============================================
	/**
	 * 3月22日 查询用户的权限,条件指定用户名
	 * 
	 * @param userName
	 *          String
	 * @return ArrayList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchUser(String userName) ;
	
	public List<SystemUser> searchUser(Integer status);

	/*
	 * 根据用户名和密码得到用户的角色
	 */
	public String getRole(String userName, String password);
	
	/**
	 *根据教员姓名查询教员信息
	 * @param uname：教员姓名
	 * @return
	 */
	public List<SystemUser> findSystemUserByName(String uname);
	/**
	 * 根据用户id和姓名，密码来查询该用户是否存在
	 * @param userId
	 * @param userName
	 * @param oldPassword
	 * @return 存在返回0 不存在返回1
	 */
	public int checkOldPassword(Integer userId, String userName,
			String oldPassword);
	/**
	 *	根据教员id查询教员信息
	 * @param id：教员id
	 * @return
	 */
	public List<SystemUser> findSystemUserByName(Integer id);
	/**
	 * 查询所有的用户角色
	 * @return
	 */
	public List<String> searchAllAuthorities();
	/**
	 * 显示用户分页查询
	 * @param page
	 * @param su
	 */
	public void searchSystemUserPageByRemark(Page<SystemUser> page, SystemUser su);
	/**
	 *根据所以用户信息（用来显示页面下拉框）
	 * @param authorities
	 * @return
	 * @fanhaohao
	 */
	public List<String> searchSystemUserInfo();

	/**
	 * 根据id查 姓名
	 * @param id
	 * @return
	 */
	public String findSystemUserById(Integer id);
}
