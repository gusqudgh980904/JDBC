package day0414;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import day0412.ZipcodeVO;
import kr.co.sist.dao.DbConnection;

/**
 *
 * @author user
 */
public class InjectionDAO {

	public String statementlogin(String id,String pass)throws SQLException{
		String name="";
		
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		DbConnection dc=DbConnection.getInstance();
		
		//1.
		try {
		//2.
		con=dc.getConnection();
		//3.
		stmt=con.createStatement();
		//4.
		StringBuilder selectQuery=new StringBuilder();
		selectQuery
		.append("	select 	name	")
		.append("	from	injection_test")
		.append("	where	id='").append(blockSql(id)).append("' and pass='").append(blockSql(pass)).append("'");
		rs=stmt.executeQuery(selectQuery.toString());
		
		
		if(rs.next()) {//입력한 id와 pass에 일치하는 레코드가 존재
			name=rs.getString("name");
//			name=rs.getString(name);//쿼리문이 맞더라도 ""를 넣지 않으면 오류 varchar2이기 때문
		}//if
		
		}finally {
		//5.
			if(rs!=null) {rs.close();} 
			if(stmt!=null) {stmt.close();} 
			if(con!=null) {con.close();} 
		}//finally
		
		
		return name;
	}//login
	
	
	public String preparedStatementlogin(String id,String pass)throws SQLException{
		String name="";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dc=DbConnection.getInstance();
		
		//1.
		try {
		//2.
		con=dc.getConnection();
		//3.
		StringBuilder selectQuery=new StringBuilder();
		selectQuery
		.append("	select 	name	")
		.append("	from	injection_test")
		.append("	where	id=? and pass=?");
		pstmt=con.prepareStatement(selectQuery.toString());
		//4.
		pstmt.setString(1,id);
		pstmt.setString(2,pass);
		
		rs=pstmt.executeQuery();
		if(rs.next()) {//입력한 id와 pass에 일치하는 레코드가 존재
			name=rs.getString("name");
//			name=rs.getString(name);//쿼리문이 맞더라도 ""를 넣지 않으면 오류 varchar2이기 때문
		}//if
		
		}finally {
		//5.
			dc.close(rs, pstmt, con);
		}//finally
		
		
		return name;
	}//login
	
	
	public List<ZipcodeVO> selectZipcode(String dong)throws SQLException{
		List<ZipcodeVO> list=new ArrayList<ZipcodeVO>();

		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		DbConnection dc=DbConnection.getInstance();
		
		//1.
		try {
		//2.
			con=dc.getConnection();
		//3.
			stmt=con.createStatement();
		//4.
			StringBuilder selectQuery=new StringBuilder();
			selectQuery
			.append("	select	zipcode,sido,gugun,dong,bunji")
			.append("	from	zipcode")
			.append("	where	dong like '").append(blockSql(dong)).append("%'");
			
			rs=stmt.executeQuery(selectQuery.toString());
			
			ZipcodeVO zVO=null;
			while(rs.next()) {
				zVO=new ZipcodeVO(rs.getString("zipcode"),rs.getString("sido"),rs.getString("gugun"),rs.getString("dong"),rs.getString("bunji"));
				
				list.add(zVO);
			}//while
			
		}finally {
		//5.
		if(rs!=null) {rs.close();}	
		if(stmt!=null) {stmt.close();}	
		if(con!=null) {con.close();}	
		}//finally
		
		
		return list;
	}//selectZipcode
	
	
	/**
	 * 입력되는 쿼리문에 ', ,--,쿼리문에 대한 부분을 치환
	 * @param originSql
	 * @return
	 */
	public String blockSql(String originSql) {
		String resultSql=originSql;
		
		resultSql=resultSql.replaceAll("'","").replaceAll(" ","").replaceAll("-","").replaceAll("select","")
				.replaceAll("where","");
		
		return resultSql;
	}//blockSql
	
}//class
