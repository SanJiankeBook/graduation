package com.yc.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.TeacherWorkdetailInterview;
import com.yc.vo.Workdetail;

@Component("ledgerRowMapperInterview")  
public class LedgerRowMapperInterview implements RowMapper {  
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		
		TeacherWorkdetailInterview twd=new TeacherWorkdetailInterview();
		twd.setClassName(rs.getString("className"));
		twd.setTotal(Integer.valueOf(rs.getString("total")));
        return twd;  
    }

	
} 