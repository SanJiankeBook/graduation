package com.yc.bean;

import org.springframework.stereotype.Repository;

@Repository
public class Rank {
	public String ranknum; //排名
	public String novelname; //小说名
	public String doll; //点击量
	public Integer nid; // 小说ID
	public String panname;// 作者笔名
	
	
	
	public Integer getNid() {
		return nid;
	}
	public void setNid(Integer nid) {
		this.nid = nid;
	}
	public String getPanname() {
		return panname;
	}
	public void setPanname(String pan_name) {
		this.panname = pan_name;
	}
	public String getRanknum() {
		return ranknum;
	}
	public void setRanknum(String ranknum) {
		this.ranknum = ranknum;
	}
	public String getNovelname() {
		return novelname;
	}
	public void setNovelname(String novelname) {
		this.novelname = novelname;
	}
	public String getDoll() {
		return doll;
	}
	public void setDoll(String doll) {
		this.doll = doll;
	}
	
	@Override
	public String toString() {
		return "Rank [ranknum=" + ranknum + ", novelname=" + novelname + ", doll=" + doll + "]";
	}
	
	
	
	
	
}
