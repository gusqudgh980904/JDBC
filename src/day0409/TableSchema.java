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
		super("���̺� �÷� ����");
		
		dcbm=new DefaultComboBoxModel<String>();
		jcb=new JComboBox<String>(dcbm);
		jbtn=new JButton("��ȸ");
		jta=new JTextArea();
		
		JPanel jpNorth=new JPanel();
		jpNorth.add(new JLabel("���̺� ����"));
		jpNorth.add(jcb);
		jpNorth.add(jbtn);
		
		JScrollPane jsp=new JScrollPane(jta);
		jsp.setBorder(new TitledBorder("�÷�����"));
		
		add("North",jpNorth);
		add("Center",jsp);
		
		jbtn.addActionListener(this);
		
		setBounds(100,100,800,800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//TableSchema
	
	
	/**
	 * DefaultComboBoxModel�� ���̺���� �����ϴ� ��
	 * @throws SQLException
	 */
	private void setTableName() throws SQLException{
		
		DbConnection dc=DbConnection.getInstance();
		
		//1.����̹��ε�
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		//2.Ŀ�ؼǾ��
			con=dc.getConnection();
		//3.������ ������ü ���
			String sql="select tname from tab";
			pstmt=con.prepareStatement(sql);
		//4.���ε庯���� �� �Ҵ�
		//5.�������� �� ��� ���
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				dcbm.addElement(rs.getString("tname"));
			}//while
			
		}finally {
		//6.�������
		dc.close(rs, pstmt, con);	
		}//finally
		
		
	}//setTableName

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		//��ư�� �������� ������ ���̺�� ���
//		String tname=(String)jcb.getSelectedItem();
		String tname=dcbm.getElementAt(jcb.getSelectedIndex());
		
		try {
			tableSchema(tname);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"��Ű�� ��ȸ�� ������ �߻�");
		}//catch
	}//actionPerformed
	
	
	private void tableSchema(String tname)throws SQLException{
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
			String selectTname="select * from "+tname;//���̺��,�÷����� ���ε� ������ ó���� �� ����
			pstmt=con.prepareStatement(selectTname);
		//4.bind������ ���Ҵ�
//			pstmt.setString(1,tname);
		//5.������ ���� �� ��� ���
			rs=pstmt.executeQuery();
			rsmd=rs.getMetaData();
			
			StringBuilder output=new StringBuilder();
			output.append("�÷���\t��������\t null���\n");
			
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
		TableSchema ts=new TableSchema();
		try {
			ts.setTableName();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ts,"���̺���� �ε��ϴ� �� ������ �߻��Ͽ����ϴ�.");
		}//catch
		
	}//main

}//class
