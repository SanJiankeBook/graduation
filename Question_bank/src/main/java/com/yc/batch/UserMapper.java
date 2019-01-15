package com.yc.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.yc.vo.Workdetail;


//读
public class UserMapper implements FieldSetMapper<Workdetail> {  

	@Override
	public Workdetail mapFieldSet(FieldSet arg0) throws BindException {
		Workdetail workdetail=new Workdetail();
		workdetail.setExamineeclassid(arg0.readInt(0));
		workdetail.setClassName(arg0.readString(1));
		workdetail.setClasscount(arg0.readInt(3));
		workdetail.setCheckcount(arg0.readInt(4));
		workdetail.setWorkcount(arg0.readInt(2));
		workdetail.setCompletionrate(arg0.readString(5));
		return workdetail;
	}  
}