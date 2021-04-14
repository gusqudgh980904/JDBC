package day0409;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.dao.DbConnection;

@SuppressWarnings("serial")
public class Homework0409 extends JFrame {
	private JButton jbtn;
	private JTextArea jta;
	private JTextField jtf;
	
	public Homework0409() {
	super("테이블 생성");
	JLabel	jl=new JLabel("테이블명");
	JLabel	jl2=new JLabel("컬럼명");
	JLabel	jl3=new JLabel("데이터형");
	JLabel	jl4=new JLabel("크기");
	JLabel	jl5=new JLabel("Primary key");
		
		
	setBounds(100,100,900,900);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//Homework0409
	
	
	private void tableInput()throws SQLException{
		//bind변수는 컬럼 값에 대해서만 사용된다.(태이블명,컬럼명은 bind변수로 사용할 수 없다.)
		DbConnection dc=DbConnection.getInstance();
		
		//1.드라이버로딩
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try {
		//2.커넥션얻기
			con=dc.getConnection();
		//3.쿼리문 생성객체 얻기
//			String selectTname="select * from ?";//테이블명,컬럼명은 바인드 변수로 처리할 수 없다
//			String selectTname="select * from "+tname;//테이블명,컬럼명은 바인드 변수로 처리할 수 없다
//			pstmt=con.prepareStatement(selectTname);
		//4.bind변수에 값할당
//			pstmt.setString(1,tname);
		//5.쿼리문 수행 후 결과 얻기
			rs=pstmt.executeQuery();
			rsmd=rs.getMetaData();
			
			StringBuilder output=new StringBuilder();
			output.append(" ");
			
			int precision=0;
			for(int i=1;i<rsmd.getColumnCount()+1;i++) {
				output.append(rsmd.getColumnName(i)).append("\t")
				.append(rsmd.getColumnTypeName(i));
				
				precision=rsmd.getPrecision(i);
				if(precision!=0) {
					output.append("(").append(precision).append(")");
				}//if
				
				output.append(rsmd.isNullable(i)==0?"\tNot Null":"").append("\n");
				
			}//for
			
			//테이블 스키마 정보를 JTextArea에 설정
			jta.setText(output.toString());
			
		}finally {
		//6.연결끊기
		dc.close(rs, pstmt, con);	
		}//finally
		
		
	}//tableSchema
	
	
	public static void main(String[] args) {

		
	}//main

}//class
