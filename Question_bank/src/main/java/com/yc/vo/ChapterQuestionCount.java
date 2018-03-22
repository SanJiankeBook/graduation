package com.yc.vo;

public class ChapterQuestionCount {
	
	  private String subjectName;
	  private String chapterName;
	  private int questionCount;

	  public void setSubjectName(String subjectName) {
	    this.subjectName = subjectName;
	  }

	  public void setChapterName(String chapterName) {
	    this.chapterName = chapterName;
	  }

	  public void setQuestionCount(int questionCount) {
	    this.questionCount = questionCount;
	  }

	  public String getSubjectName() {
	    return subjectName;
	  }

	  public String getChapterName() {
	    return chapterName;
	  }

	  public int getQuestionCount() {
	    return questionCount;
	  }

}
