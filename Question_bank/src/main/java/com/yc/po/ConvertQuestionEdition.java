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
@Table(name = "ConvertQuestionEdition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConvertQuestionEdition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 序号
	@Column
	private Integer oldQuestionId; 
	@Column
	private Integer newQuestionId; 
	@Column(length = 20)
	private Integer oldEditionId; 
	@Column(length = 20)
	private Integer newEditionId; 
	@Column(length = 20)
	private String time; 
	@Column(length = 20)
	private Integer convertpersonid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOldQuestionId() {
		return oldQuestionId;
	}
	public void setOldQuestionId(Integer oldQuestionId) {
		this.oldQuestionId = oldQuestionId;
	}
	public Integer getNewQuestionId() {
		return newQuestionId;
	}
	public void setNewQuestionId(Integer newQuestionId) {
		this.newQuestionId = newQuestionId;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	public Integer getOldEditionId() {
		return oldEditionId;
	}
	public void setOldEditionId(Integer oldEditionId) {
		this.oldEditionId = oldEditionId;
	}
	public Integer getNewEditionId() {
		return newEditionId;
	}
	public void setNewEditionId(Integer newEditionId) {
		this.newEditionId = newEditionId;
	}
	public Integer getConvertpersonid() {
		return convertpersonid;
	}
	public void setConvertpersonid(Integer convertpersonid) {
		this.convertpersonid = convertpersonid;
	}
	
	@Override
	public String toString() {
		return "ConvertQuestionEdition [id=" + id + ", oldQuestionId=" + oldQuestionId + ", newQuestionId="
				+ newQuestionId + ", oldEditionId=" + oldEditionId + ", newEditionId=" + newEditionId + ", time=" + time
				+ ", convertpersonid=" + convertpersonid + "]";
	}

}
