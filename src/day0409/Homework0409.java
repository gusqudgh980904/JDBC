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
	super("���̺� ����");
	JLabel	jl=new JLabel("���̺��");
	JLabel	jl2=new JLabel("�÷���");
	JLabel	jl3=new JLabel("��������");
	JLabel	jl4=new JLabel("ũ��");
	JLabel	jl5=new JLabel("Primary key");
		
		
	setBounds(100,100,900,900);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//Homework0409
	
	
	private void tableInput()throws SQLException{
		//bind������ �÷� ���� ���ؼ��� ���ȴ�.(���̺��,�÷����� bind������ ����� �� ����.)
		DbConnection dc=DbConnection.getInstance();
		
		//1.����̹��ε�
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try {
		//2.Ŀ�ؼǾ��
			con=dc.getConnection();
		//3.������ ������ü ���
//			String selectTname="select * from ?";//���̺��,�÷����� ���ε� ������ ó���� �� ����
//			String selectTname="select * from "+tname;//���̺��,�÷����� ���ε� ������ ó���� �� ����
//			pstmt=con.prepareStatement(selectTname);
		//4.bind������ ���Ҵ�
//			pstmt.setString(1,tname);
		//5.������ ���� �� ��� ���
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
			
			//���̺� ��Ű�� ������ JTextArea�� ����
			jta.setText(output.toString());
			
		}finally {
		//6.�������
		dc.close(rs, pstmt, con);	
		}//finally
		
		
	}//tableSchema
	
	
	public static void main(String[] args) {

		
	}//main

}//class
