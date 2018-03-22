package com.yc.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.yc.vo.TeacherInterviewdetail;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;
@Component("teacherInterviewMapper")  
public class TeacherInterviewMapper implements RowMapper {  
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		
		TeacherInterviewdetail TId=new TeacherInterviewdetail();
		TId.setTeacherName(rs.getString("teacherName"));
		 TId.setNum(rs.getInt("num"));
        return TId;  
    }
} 