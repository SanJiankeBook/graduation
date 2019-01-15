package com.yc.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="satisfactionDetail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SatisfactionDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column
	private Integer said;
	@Column
	private Integer studentid;
	@Column(length=10,nullable=false)
	private String satisfaction;  
	@Column(length=10,nullable=false)
	private String year; 
	@Column(length=10,nullable=false)
	private String month;
	@Column(length=10,nullable=false)
	private String className; 
	@Column(length=10,nullable=false)
	private String teacherName; 
	@Column(length=10,nullable=false)
	private String unsatisfaction; 
	@Column
	private Integer a1;
	@Column
	private Integer a2;	
	@Column
	private Integer a3;
	@Column
	private Integer a4;	
	@Column
	private Integer a5;
	@Column
	private Integer a6;	
	@Column
	private Integer a7;
	@Column
	private Integer a8;	
	@Column
	private Integer a9;
	@Column
	private Integer a10;	
	@Column
	private Integer a11;
	@Column
	private Integer a12;	
	@Column
	private Integer a13;
	@Column
	private Integer a14;	
	@Column
	private Integer a15;
	@Column
	private Integer a16;	
	@Column
	private Integer a17;
	@Column
	private Integer a18;	
	@Column
	private Integer a19;
	@Column
	private Integer a20;
	@Column
	private Integer a21;
	@Column
	private Integer a22;	
	@Column
	private Integer a23;
	@Column
	private Integer a24;	
	@Column
	private Integer a25;
	@Column
	private Integer a26;	
	@Column
	private Integer totalScore;
	
	@Column
	private Integer sampleCount;
	
	@Column
	private Integer classPeopleCount;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSaid() {
		return said;
	}
	public void setSaid(Integer said) {
		this.said = said;
	}
	public Integer getStudentid() {
		return studentid;
	}
	public void setStudentid(Integer studentid) {
		this.studentid = studentid;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getUnsatisfaction() {
		return unsatisfaction;
	}
	public void setUnsatisfaction(String unsatisfaction) {
		this.unsatisfaction = unsatisfaction;
	}
	public Integer getA1() {
		return a1;
	}
	public void setA1(Integer a1) {
		this.a1 = a1;
	}
	public Integer getA2() {
		return a2;
	}
	public void setA2(Integer a2) {
		this.a2 = a2;
	}
	public Integer getA3() {
		return a3;
	}
	public void setA3(Integer a3) {
		this.a3 = a3;
	}
	public Integer getA4() {
		return a4;
	}
	public void setA4(Integer a4) {
		this.a4 = a4;
	}
	public Integer getA5() {
		return a5;
	}
	public void setA5(Integer a5) {
		this.a5 = a5;
	}
	public Integer getA6() {
		return a6;
	}
	public void setA6(Integer a6) {
		this.a6 = a6;
	}
	public Integer getA7() {
		return a7;
	}
	public void setA7(Integer a7) {
		this.a7 = a7;
	}
	public Integer getA8() {
		return a8;
	}
	public void setA8(Integer a8) {
		this.a8 = a8;
	}
	public Integer getA9() {
		return a9;
	}
	public void setA9(Integer a9) {
		this.a9 = a9;
	}
	public Integer getA10() {
		return a10;
	}
	public void setA10(Integer a10) {
		this.a10 = a10;
	}
	public Integer getA11() {
		return a11;
	}
	public void setA11(Integer a11) {
		this.a11 = a11;
	}
	public Integer getA12() {
		return a12;
	}
	public void setA12(Integer a12) {
		this.a12 = a12;
	}
	public Integer getA13() {
		return a13;
	}
	public void setA13(Integer a13) {
		this.a13 = a13;
	}
	public Integer getA14() {
		return a14;
	}
	public void setA14(Integer a14) {
		this.a14 = a14;
	}
	public Integer getA15() {
		return a15;
	}
	public void setA15(Integer a15) {
		this.a15 = a15;
	}
	public Integer getA16() {
		return a16;
	}
	public void setA16(Integer a16) {
		this.a16 = a16;
	}
	public Integer getA17() {
		return a17;
	}
	public void setA17(Integer a17) {
		this.a17 = a17;
	}
	public Integer getA18() {
		return a18;
	}
	public void setA18(Integer a18) {
		this.a18 = a18;
	}
	public Integer getA19() {
		return a19;
	}
	public void setA19(Integer a19) {
		this.a19 = a19;
	}
	public Integer getA20() {
		return a20;
	}
	public void setA20(Integer a20) {
		this.a20 = a20;
	}
	public Integer getA21() {
		return a21;
	}
	public void setA21(Integer a21) {
		this.a21 = a21;
	}
	public Integer getA22() {
		return a22;
	}
	public void setA22(Integer a22) {
		this.a22 = a22;
	}
	public Integer getA23() {
		return a23;
	}
	public void setA23(Integer a23) {
		this.a23 = a23;
	}
	public Integer getA24() {
		return a24;
	}
	public void setA24(Integer a24) {
		this.a24 = a24;
	}
	public Integer getA25() {
		return a25;
	}
	public void setA25(Integer a25) {
		this.a25 = a25;
	}
	public Integer getA26() {
		return a26;
	}
	public void setA26(Integer a26) {
		this.a26 = a26;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	
	
	public Integer getSampleCount() {
		return sampleCount;
	}
	public void setSampleCount(Integer sampleCount) {
		this.sampleCount = sampleCount;
	}
	public Integer getClassPeopleCount() {
		return classPeopleCount;
	}
	public void setClassPeopleCount(Integer classPeopleCount) {
		this.classPeopleCount = classPeopleCount;
	}
	@Override
	public String toString() {
		return "SatisfactionDetail [id=" + id + ", said=" + said + ", studentid=" + studentid + ", satisfaction="
				+ satisfaction + ", year=" + year + ", month=" + month + ", className=" + className + ", teacherName="
				+ teacherName + ", unsatisfaction=" + unsatisfaction + ", a1=" + a1 + ", a2=" + a2 + ", a3=" + a3
				+ ", a4=" + a4 + ", a5=" + a5 + ", a6=" + a6 + ", a7=" + a7 + ", a8=" + a8 + ", a9=" + a9 + ", a10="
				+ a10 + ", a11=" + a11 + ", a12=" + a12 + ", a13=" + a13 + ", a14=" + a14 + ", a15=" + a15 + ", a16="
				+ a16 + ", a17=" + a17 + ", a18=" + a18 + ", a19=" + a19 + ", a20=" + a20 + ", a21=" + a21 + ", a22="
				+ a22 + ", a23=" + a23 + ", a24=" + a24 + ", a25=" + a25 + ", a26=" + a26 + ", totalScore=" + totalScore
				+ ", sampleCount=" + sampleCount + ", classPeopleCount=" + classPeopleCount + "]";
	}

	
}
