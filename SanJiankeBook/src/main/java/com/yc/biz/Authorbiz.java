package com.yc.biz;

import java.util.List;
import com.yc.bean.Author;
import com.yc.bean.Novel;
import com.yc.bean.User;

public interface Authorbiz {
	//注册成为作家
	public void insertAuthor(Object obj);
	
	//查找作家
	List<Author> FindAuthor();
	
	//根据aid查找作家
	List<Author> FindAuthorByaid(int aid);
	
	//删除作家
	void DelAuthor(int id);

	//分页查找作家
	List<Author> FindAuthorByPage(Integer start,Integer end);
	
	//根据小说id查询作者名
	List Show_Author(int nid);
	
	//根据用户id查询作者信息
	List<Author> inforByunumber(int uid);
	
	//根据作者id查询他写的小说
	List<Novel> inforByaid(int aid);
	
	//更新作者信息
	void updataAuthor(String pan_name,int aage,String acard,String atel,int aid);
	
	//作家登录,根据用户名查询
	List<User> inforByu_number(String u_number);
}
