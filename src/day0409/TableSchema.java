package day0409;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import kr.co.sist.dao.DbConnection;

@SuppressWarnings("serial")
public class TableSchema extends JFrame implements ActionListener {

	private JButton jbtn;
	private DefaultComboBoxModel<String> dcbm;
	private JComboBox<String> jcb;
	private JTextArea jta;
	
	public TableSchema() {
		super("테이블 컬럼 정보");
		
		dcbm=new DefaultComboBoxModel<String>();
		jcb=new JComboBox<String>(dcbm);
		jbtn=new JButton("조회");
		jta=new JTextArea();
		
		JPanel jpNorth=new JPanel();
		jpNorth.add(new JLabel("테이블 선택"));
		jpNorth.add(jcb);
		jpNorth.add(jbtn);
		
		JScrollPane jsp=new JScrollPane(jta);
		jsp.setBorder(new TitledBorder("컬럼정보"));
		
		add("North",jpNorth);
		add("Center",jsp);
		
		jbtn.addActionListener(this);
		
		setBounds(100,100,800,800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//TableSchema
	
	
	/**
	 * DefaultComboBoxModel에 테이블명을 설정하는 일
	 * @throws SQLException
	 */
	private void setTableName() throws SQLException{
		
		DbConnection dc=DbConnection.getInstance();
		
		//1.드라이버로딩
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		//2.커넥션얻기
			con=dc.getConnection();
		//3.쿼리문 생성객체 얻기
			String sql="select tname from tab";
			pstmt=con.prepareStatement(sql);
		//4.바인드변수에 값 할당
		//5.쿼리수행 후 결과 얻기
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				dcbm.addElement(rs.getString("tname"));
			}//while
			
		}finally {
		//6.연결끊기
		dc.close(rs, pstmt, con);	
		}//finally
		
		
	}//setTableName

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		//버튼이 눌려지면 선택한 테이블명 얻기
//		String tname=(String)jcb.getSelectedItem();
		String tname=dcbm.getElementAt(jcb.getSelectedIndex());
		
		try {
			tableSchema(tname);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"스키마 조회중 문제가 발생");
		}//catch
	}//actionPerformed
	
	
	private void tableSchema(String tname)throws SQLException{
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
			String selectTname="select * from "+tname;//테이블명,컬럼명은 바인드 변수로 처리할 수 없다
			pstmt=con.prepareStatement(selectTname);
		//4.bind변수에 값할당
//			pstmt.setString(1,tname);
		//5.쿼리문 수행 후 결과 얻기
			rs=pstmt.executeQuery();
			rsmd=rs.getMetaData();
			
			StringBuilder output=new StringBuilder();
			output.append("컬럼명\t데이터형\t null허용\n");
			
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
		TableSchema ts=new TableSchema();
		try {
			ts.setTableName();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ts,"테이블명을 로딩하는 중 문제가 발생하였습니다.");
		}//catch
		
	}//main

}//class
