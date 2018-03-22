package com.yc.biz;

import java.io.Serializable;
import java.util.List;

import com.yc.po.ADailyTalk;
import com.yc.po.Book;
import com.yc.po.Category;
import com.yc.po.ResourceType;
import com.yc.vo.Page;

public interface ResourceTypeBiz {

	/**
	 * 获取所有的资源类型
	 * @return
	 */
	public List<ResourceType> getAllResourceType();
	/**
	 * 获取书籍的所有类型
	 * @return
	 */
	public List<Category> getAllType();
	/**
	 * 将资源的地址上传到服务器中
	 * @param book
	 */
	public void insertInfo(Book book);
	/**
	 * 通过类型编号或者书籍名称来搜索
	 * @param book
	 * @param page 
	 * @return
	 */
	public Page<Book> searchBookInfo(Book book, Page<Book> page);
}
