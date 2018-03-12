package com.yc.biz;

import java.util.List;

import com.yc.bean.User;
import com.yc.bean.UserBook;

public interface UserBookbiz {
	
	//根据用户id查询书架
	public List finduserbook(UserBook userbook);
	
	//查询这本书是否存在于书架
	public List getUserbook(UserBook userbook);
	
	//添加这本书到书架
	public void addUserBook(UserBook userbook);
	
	//根据用户查询书架信息
	public List finduserbookInfo(UserBook ub, int start, int end);

	//用户删除书架中的书
	public void delUserbook(UserBook userbook);
	
	
	

}
