package day0412;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import kr.co.sist.dao.DbConnection;

public class UseCallableStatement {

	public UseCallableStatement() throws SQLException{
		
		Connection con=null;
		CallableStatement cstmt=null;
		
	DbConnection dc=DbConnection.getInstance();
		//1.����̹��ε�
		try {
		//2.Ŀ�ؼ� ���
			con=dc.getConnection();
		//3.������ ������ü ���
			cstmt=con.prepareCall("{call proc_plus(?,?,?,?)}");
		//4.���ε� ������ ���Ҵ�
		 //in parameter
			cstmt.setInt(1,4);
			cstmt.setInt(2,12);
		 //out parameter
			cstmt.registerOutParameter(3,Types.NUMERIC);
			cstmt.registerOutParameter(4,Types.VARCHAR);
		//5.���ν��� ����
			cstmt.execute();
			
		//6.out parameter�� ������ �� ���
			int sum=cstmt.getInt(3);
			String msg=cstmt.getString(4);
			System.out.println(sum+"/"+msg);
			
		}finally {
		//7.�������
			//PreparedStatement�� CallableStatement�� �θ��̱� ������
			//CallableStatement�� ���� �� �ִ�
		dc.close(null, cstmt, con);	
		}//finally
		
		
	}//UseCallableStatement
	
	
	public static void main(String[] args) {

		try {
			new UseCallableStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		
	}//main

}//class
