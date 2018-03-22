package com.yc.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;
/**
 * ledger的实体类
 * @author Administrator
 *
 */
@Component("ledgerRowMapper")  
public class LedgerRowMapper implements RowMapper {  
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		
		Workdetail twd=new Workdetail();
		twd.setExamineeclassid(rs.getInt("examineeclassid"));
        return twd;  
    }

	
} 