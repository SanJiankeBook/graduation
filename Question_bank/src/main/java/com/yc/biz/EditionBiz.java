package com.yc.biz;


import java.util.ArrayList;

import java.io.Serializable;

import java.util.List;


import com.yc.po.Edition;
import com.yc.po.Examinee;
import com.yc.po.Edition;
import com.yc.po.Subject;



import java.util.List;



public interface EditionBiz {
	/**
	 * <p>
	 * Title:版本号DAO类 
	 * 
	 * @version 1.0
	 */
	public String getEditionName();
	/**
	 * 得到所有的版本名称,存入集合中返回
	 * 
	 * @return ArrayList 版本名称集合
	 * @throws Exception
	 */
	public List searchEdition();
	/**
	 * 通过版本号得到版本ID
	 * 
	 * @param editionName
	 *          String
	 * @return int
	 * @throws Exception
	 */
	public int getEditionId(String editionName);
	/**
	 * 得到所有的Edition放入ArrayList中，每个是一个Edition对象
	 */
	public List<Edition> getAllEdition();
	
	public List<Edition> searchAllEdition();
	
	public void updateEdition(Edition edition);
	
	public void deleteEdition(Edition edtion);
	
	public Serializable addEdition(Edition edition);
	
	public String searchEditionName(int id);
	
	public void updateCurrentUse(int id);
	
	
	
}
