package com.yc.biz;

import java.util.List;

public interface AuthorityBiz {
	/**
	 * 得到所有的权限名称,存入集合中返回
	 * 
	 * @return ArrayList 权限名称集合
	 * @throws Exception
	 */
	public List<String> searchAuthority();
	/**
	 * 根据权限ID得到权限名称,存入字符串中返回
	 * 
	 * @return String 权限名称
	 * @throws Exception
	 */
	public String searchAuthority(int id);

}
