package com.yc.biz;

import java.util.List;

public interface LabQuestionTypeBiz {

	/**
	 * 通过ID获取skillCode
	 * 
	 * @param id
	 *          int
	 * @return skillCode String
	 * @throws Exception
	 */
	public String getSkillCode(int id);
	/**
	 * 通过skillCode获取ID
	 * 
	 * @param skillCode
	 *          String
	 * @return id int
	 * @throws Exception
	 */
	public int getSkillCodeId(String skillCode);

	/**
	 * 通过学期得到技术组合类型集合
	 * 
	 * @param semester
	 *          String 学期名称
	 * @return ArrayList 技术组合集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSkillbySemester(String semester) ;
}
