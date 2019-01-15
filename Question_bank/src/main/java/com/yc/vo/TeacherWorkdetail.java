package com.yc.vo;

import java.io.Serializable;

public class TeacherWorkdetail implements Serializable{

private static final long serialVersionUID = 1L;
	
	private String checkcreator;
	
	private Integer checkcount;

	public String getCheckcreator() {
		return checkcreator;
	}

	public void setCheckcreator(String checkcreator) {
		this.checkcreator = checkcreator;
	}

	public Integer getCheckcount() {
		return checkcount;
	}

	public void setCheckcount(Integer checkcount) {
		this.checkcount = checkcount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return checkcreator+","+checkcount;
		}
	
	
	
	
}
