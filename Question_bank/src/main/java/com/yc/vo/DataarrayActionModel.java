package com.yc.vo;

import java.io.Serializable;

public class DataarrayActionModel implements Serializable{
	private Integer displayRows; // 要显示的页数
	private Integer pageNume; // 当前请求的第几页数
	private Integer version;//版本号
	private String semester;//班级
	private Integer difficult;//难易度
	
	private String questionType;
	private Integer subject;
	private String txtQuestion;
	private Integer id;//题目编号
	private String ids;//批量删除的题目编号
	private String falg;//next 或   prev
	private String wpid;//试卷编号
	private Integer rows;//每页显示的记录数 
	private Integer page;////当前第几页  
	private String sort;//排序方式
	private String order;//排序规则
	private String idField;//标识列
	private Integer chapterId;//章节编号
	private String sname;
	private String cname;
	
	
	
	

	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getChapterId() {
		return chapterId;
	}
	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getWpid() {
		return wpid;
	}
	public void setWpid(String wpid) {
		this.wpid = wpid;
	}
	public String getFalg() {
		return falg;
	}
	public void setFalg(String falg) {
		this.falg = falg;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public Integer getSubject() {
		return subject;
	}
	public void setSubject(Integer subject) {
		this.subject = subject;
	}
	public String getTxtQuestion() {
		return txtQuestion;
	}
	public void setTxtQuestion(String txtQuestion) {
		this.txtQuestion = txtQuestion;
	}
	public Integer getDisplayRows() {
		return displayRows;
	}
	public void setDisplayRows(Integer displayRows) {
		this.displayRows = displayRows;
	}
	public Integer getPageNume() {
		return pageNume;
	}
	public void setPageNume(Integer pageNume) {
		this.pageNume = pageNume;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public Integer getDifficult() {
		return difficult;
	}
	public void setDifficult(Integer difficult) {
		this.difficult = difficult;
	}
	
	
}
