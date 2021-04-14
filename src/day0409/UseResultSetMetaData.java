package day0409;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import kr.co.sist.dao.DbConnection;

public class UseResultSetMetaData {

	
	public UseResultSetMetaData()throws SQLException{
		DbConnection dc=DbConnection.getInstance();
		
		//1.����̹� �ε�
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try {
			
		//2.Ŀ�ؼ� ���
			con=dc.getConnection();
		//3.������ ���� ��ü ���
			String select="select * from car_model";
			pstmt=con.prepareStatement(select);
		//4.���ε庯���� �� ����
		//5.������ ���� �� ��� ���
			rs=pstmt.executeQuery();
			//ResultSetMetaData���
			rsmd=rs.getMetaData();
//			System.out.println(rsmd.getColumnCount()+"/"+rsmd.getColumnName(1)+"/"+rsmd.getColumnTypeName(1)+
//			"/"+rsmd.getPrecision(1)+"/"+rsmd.isNullable(1));
			int precision=0;
			for(int i=1;i<rsmd.getColumnCount()+1;i++) {
				precision=rsmd.getPrecision(i);
				System.out.print(rsmd.getColumnName(i)+" "+rsmd.getColumnTypeName(i));
				
				if(precision!=0) {
					System.out.print("("+precision+")");
				}//if
				System.out.println(rsmd.isNullable(i)==0?"N.N":"");
			}//for
			
		}finally {
		//6.�������
		dc.close(rs, pstmt, con);	
		}//finally
		
	}//UseResultSetMetaData
	
	
	public static void main(String[] args) {

			try {
				new UseResultSetMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
			}//catch
	}//main

}//class
