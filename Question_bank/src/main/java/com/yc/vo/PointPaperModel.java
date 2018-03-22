package com.yc.vo;

import java.util.Date;

public class PointPaperModel {
	private Integer sid;//课程编号
	private Integer cid; //测试班级编号
	private String paperPwd;//测评密码
	private Date examDate;//测评日期
	private int pstatus;//试卷状态
	private String pstatusname;//状态名
	private String pname;//试卷名称
	private String descript;//备注说明
	private String ptitle;//试题
	private Integer pid;//试卷编号
	private String className;//测评班级
	private String subjectName;//课程名
	private Integer rows;//每页显示的记录数 
	private Integer page;////当前第几页  
	private String sort;//排序方式
	private String order;//排序规则
	private String idField;//标识列
	private String date;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPstatusname() {
		return pstatusname;
	}
	public void setPstatusname(String pstatusname) {
		this.pstatusname = pstatusname;
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
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getPaperPwd() {
		return paperPwd;
	}
	public void setPaperPwd(String paperPwd) {
		this.paperPwd = paperPwd;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public int getPstatus() {
		return pstatus;
	}
	public void setPstatus(int pstatus) {
		this.pstatus = pstatus;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	@Override
	public String toString() {
		return "PointPaperModel [sid=" + sid + ", cid=" + cid + ", paperPwd=" + paperPwd + ", examDate=" + examDate
				+ ", pstatus=" + pstatus + ", pstatusname=" + pstatusname + ", pname=" + pname + ", descript="
				+ descript + ", ptitle=" + ptitle + ", pid=" + pid + ", className=" + className + ", subjectName="
				+ subjectName + ", rows=" + rows + ", page=" + page + ", sort=" + sort + ", order=" + order
				+ ", idField=" + idField + ", date=" + date + "]";
	}
	
	
}
