package day0412;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.dao.DbConnection;

/**
 * car_model ���̺��� �Է��� �ɼ��� ������ �ִ� ������ �𵨸�,����,�ɼ�,������ ��ȸ
 * @author user
 */
public class UseLike {

	public UseLike() throws SQLException{
		String option=JOptionPane.showInputDialog("�ɼ��Է�");
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.����̹��ε�
		try {
		//2.Ŀ�ؼǾ��
		con=dc.getConnection();
		//3.������ ������ü���
		String selectCar
//		="select model,price,car_option,car_year from car_model where car_option like %?%";
		="select model,price,car_option,car_year from car_model where car_option like '%'||?||'%'";
		pstmt=con.prepareStatement(selectCar);
		//4.���ε� ������ ������
		pstmt.setString(1,option);
		//5.������ ���� �� ��� ���
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			System.out.println(rs.getString("model")+"/"+rs.getInt("price")+"/"+rs.getString("car_option")+"/"+
					rs.getString("car_year"));
		}//while
		
		}finally {
		//6.���� ����
		dc.close(rs, pstmt, con);
		}//finally
		
	}//UseLike
	
	
	public static void main(String[] args) {
		try {
			new UseLike();
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		
	}//main

}//class
