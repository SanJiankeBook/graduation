package com.yc.webexam.actions;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.yc.biz.SystemUserBiz;
import com.yc.po.SystemUser;
import com.yc.utils.Encrypt;
import com.yc.utils.JsonUtil;
import com.yc.vo.Page;

public class SystemUserAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemUserAction.class);

	private Integer userId;
	private String userName;
	private String oldPassword;
	private String newPassword;
	private String remark;
	private int displayRows; // 要显示的页数
	private int pageNume; // 当前请求的第几页数
	private String authorities;
	@Resource(name = "systemUserBiz")
	private SystemUserBiz systemUserBiz;

	/**
	 * 添加用户信息
	 * @author fanhaohao
	 */
	public void addUserInfo() {
		String jsonStr = "";
		int flag = -1;
		try {
			SystemUser su = new SystemUser();
			String password = Encrypt.md5AndSha(newPassword);
			su.setPassword(password);
			su.setAuthorities(authorities);
			su.setRemark(remark);
			su.setUserName(userName);
			flag = systemUserBiz.addUser(su);
			if (flag == 0) {
				jsonStr = super.writeJson(flag, "添加成功！");
			} else {
				jsonStr = super.writeJson(1, "添加失败！");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "添加失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据教师端用户的id，姓名，密码来查询该用户是否存在
	 * @author fanhaohao
	 */
	public void checkOldPassword() {
		String jsonStr = "";
		int flag = -1;
		try {
			oldPassword = Encrypt.md5AndSha(oldPassword);
			flag = systemUserBiz.checkOldPassword(userId, userName, oldPassword);
			if (flag == 0) {
				jsonStr = super.writeJson(flag, "查询成功！");
			} else {
				jsonStr = super.writeJson(1, "查询失败！");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @author fanhaohao
	 */
	public void checkUserNameIsExist() {
		String jsonStr = "";
		Boolean flag = true;
		try {
			flag = systemUserBiz.isExist(userName);
			if (flag) {
				jsonStr = super.writeJson(1, "用户名重复！");
			} else {
				jsonStr = super.writeJson(0, "用户名不存在！");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据教师端用户的id来更新密码
	 * 
	 * @author fanhaohao
	 */
	public void updatePasswordById() {
		String jsonStr = "";
		try {
			newPassword = Encrypt.md5AndSha(newPassword);
			SystemUser su = new SystemUser();
			su.setId(userId);
			su.setPassword(newPassword);
			systemUserBiz.updateUserPassword(su);
			jsonStr = super.writeJson(0, "更新成功！");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "更新失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据教师端用户的id来更新用户信息
	 * 
	 * @author fanhaohao   hwh  更新：添加旧密码验证
	 */
	public void updateSystemUserInfo() {
		String jsonStr = "";
		try {
			oldPassword= Encrypt.md5AndSha(oldPassword);
			SystemUser s=systemUserBiz.findSystemUserByName(userId).get(0);
			if(!(s.getPassword().equals(oldPassword))){
				jsonStr = super.writeJson(1, "旧密码错误！");
			}else{
				newPassword = Encrypt.md5AndSha(newPassword);
				SystemUser su = new SystemUser();
				su.setId(userId);
				su.setPassword(newPassword);
				su.setAuthorities(authorities);
				su.setRemark(remark);
				systemUserBiz.updateSystemUser(su);
				jsonStr = super.writeJson(0, "更新成功！");
			}
			
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "更新失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 查询所有的用户角色
	 * 
	 * @author fanhaohao
	 */
	public void findAuthorities() {
		String jsonStr = "";
		try {
			List<String> lists = systemUserBiz.searchAllAuthorities();
			jsonStr = super.writeJson(0, lists);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "更新失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 显示用户分页查询
	 * 
	 * @author fanhaohao
	 */
	public void showSystemUserPages() {
		String jsonStr = "";
		try {
			// 当请求的是查询笔试成绩时
			SystemUser su = new SystemUser();
			su.setRemark(remark);
			Page<SystemUser> page = new Page<SystemUser>();
			page.setPageSize(displayRows);
			page.setCurrentPage(pageNume);
			systemUserBiz.searchSystemUserPageByRemark(page, su);
			for (SystemUser s : page.getResult()) {
				s.setCheckings(null);
			}
			jsonStr = super.writeJson(0, page);

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "更新失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 显示用户名下拉框
	 * 
	 * @author fanhaohao
	 */
	public void showSystemUserInfo() {
		String jsonStr = "";
		try {
			List<String> lists = systemUserBiz.searchSystemUserInfo();
			jsonStr = super.writeJson(0, lists);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}

	public int getDisplayRows() {
		return displayRows;
	}

	public void setDisplayRows(int displayRows) {
		this.displayRows = displayRows;
	}

	public int getPageNume() {
		return pageNume;
	}

	public void setPageNume(int pageNume) {
		this.pageNume = pageNume;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

}
