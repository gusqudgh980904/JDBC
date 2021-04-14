package day0412;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;

public class UseCallableStatementDAO {
	
	private static UseCallableStatementDAO ucsDAO;
	
	private UseCallableStatementDAO() {
		
	}//UseCallableStatement
	
	
	public static UseCallableStatementDAO getInstance() {
		if (ucsDAO==null) {
			ucsDAO=new UseCallableStatementDAO();
		}//if
		
		return ucsDAO;
	}//UseCallableStatementDAO
	
	
	public String insertCpEmp4(CpEmp4VO ceVO) throws SQLException {
		
		String str="";
		
		Connection con=null;
		CallableStatement cstmt=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.����̹� �ε�
		try {
			
		//2.Ŀ�ؼ� ���
			con=dc.getConnection();
		//3.������ ������ü ���
			cstmt=con.prepareCall("{call proc_insert(?,?,?,?,?,?) }");
		//4.���ε� ������ ������
		 //in parameter
			cstmt.setInt(1,ceVO.getEmpno());
			cstmt.setString(2,ceVO.getEname());
			cstmt.setInt(3,ceVO.getDeptno());
			cstmt.setString(4,ceVO.getJob());
		 //out parameter
			cstmt.registerOutParameter(5,Types.NUMERIC);
			cstmt.registerOutParameter(6,Types.VARCHAR);
		//5.���ν��� ����
			cstmt.execute();
		//6.out parameter�� ������ �� �ޱ�
			int cnt=cstmt.getInt(5);//PL/SQL bind������ ����� ���� ��� ��
			System.out.println("�߰��� �� ��"+cnt);
			str=cstmt.getString(6);
			
		}finally {
		//7.�������
		dc.close(null, cstmt, con);
		}//finally
		
		return str;
	}//insertCpEmp4
	
	
	public String updateCpEmp4(CpEmpModifyVO cemVO)throws SQLException{
		String result="";
		
		Connection con=null;
		CallableStatement cstmt=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.
		try {
		//2.
			con=dc.getConnection();
		//3.
			cstmt=con.prepareCall("{call proc_update(?,?,?,?,?) }");
		//4.���ε� ������ �� ����
			//in parameter
			cstmt.setInt(1,cemVO.getEmpno());
			cstmt.setString(2,cemVO.getJob());
			cstmt.setInt(3,cemVO.getDeptno());
			//out parameter
			cstmt.registerOutParameter(4,Types.NUMERIC);
			cstmt.registerOutParameter(5,Types.VARCHAR);
		
		//5.���ν��� ����
			cstmt.execute();
		//6.out parameter�� �Ҵ�� �� �ޱ�
			int cnt=cstmt.getInt(4);
			result=cstmt.getString(5);
			System.out.println(cnt+"�� ����");
			
			
		}finally {
		//7.
			dc.close(null, cstmt, con);
		}//finally
		
		return result;
	}//updateCpEmp4
	
	
	public  String deleteCpEmp(int empno)throws SQLException{
		String result="";
		
		Connection con=null;
		CallableStatement cstmt=null;
		
		DbConnection dc=DbConnection.getInstance();
		
		//1.
		try {
		//2.
			con=dc.getConnection();
		//3.
			cstmt=con.prepareCall("{call proc_delete(?,?,?)}");
		//4.
			//in parameter
			cstmt.setInt(1,empno);
			
			//out parameter
			cstmt.registerOutParameter(2,Types.NUMERIC);
			cstmt.registerOutParameter(3,Types.VARCHAR);
		//5.
			cstmt.execute();
		//6.out parameter�� ������ ���ޱ�
			System.out.println(cstmt.getInt(2)+"�� ����");
			result=cstmt.getString(3);
		}finally {
		//7.
		dc.close(null, cstmt, con);	
		}//finally
		
		return result;
	}//deleteCpEmp
	
	
	public List<ZipcodeVO> selectZipcode(String dong)throws SQLException{
		List<ZipcodeVO> list=new ArrayList<ZipcodeVO>();
		
		Connection con=null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
				
		DbConnection dc=DbConnection.getInstance();		
		
		//1.
		try {
		//2.
			con=dc.getConnection();
		//3.
			cstmt=con.prepareCall("{call proc_zipcode(?,?)}");
		//4.
			//in parameter
			cstmt.setString(1,dong);
			//out parameter:OracleTypes.CURSOR�� ����Ѵ�
//			cstmt.registerOutParameter(2,OracleTypes.CURSOR);//12c������ OracleTypes.CURSOR�� ����ؾ� �Ѵ�
			cstmt.registerOutParameter(2,Types.REF_CURSOR);//19c������ ��밡��
		//5.
			cstmt.execute();
		//6.
			rs=(ResultSet)cstmt.getObject(2);
			
			ZipcodeVO zVO=null;
			while(rs.next()) {
				zVO=new ZipcodeVO(rs.getString("zipcode"),rs.getString("sido"),rs.getString("gugun"),
						rs.getString("dong"),rs.getString("bunji"));
				
				list.add(zVO);
			}//while
			
			
		}finally {
		//7.
		dc.close(rs, cstmt, con);
		}//finally
		
		return list;
	}//selectZipcode
	
	
}//class
