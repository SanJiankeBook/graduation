package com.yc.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;

@Component("teacherLedgerRowMapper")  
public class TeacherLedgerRowMapper implements RowMapper {  
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		TeacherWorkdetail teacherWorkdetail=new TeacherWorkdetail();
		teacherWorkdetail.setCheckcreator(rs.getString("checkcreator"));
		teacherWorkdetail.setCheckcount(0);
        return teacherWorkdetail;  
    }

	
} 