package day0412;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.dao.DbConnection;

@SuppressWarnings("serial")
public class TableCreate extends JFrame implements ActionListener {
	
	private JTextField jtfTableName;
	private JTextField jtfColName;
	private JTextField jtfSize;
	private JButton jbtnTableName;
	
	private JCheckBox jcbPmKey;
	private JCheckBox jcbNN;
	
	private JButton jbtnSetCol;
	private JButton jbtnCreate;
	private JButton jbtnReset;
	
	private DefaultComboBoxModel<String> dcbmDataType;
	private JComboBox<String> jcbDataType;
	private JTextArea jta;
	
	private StringBuilder createQuery;
	
	public TableCreate() {
		super("���̺� ����");
		
		createQuery=new StringBuilder();
		
		dcbmDataType=new DefaultComboBoxModel<String>();
		
		dcbmDataType.addElement("number");
		dcbmDataType.addElement("char");
		dcbmDataType.addElement("varchar2");
		dcbmDataType.addElement("date");
		dcbmDataType.addElement("long");
		dcbmDataType.addElement("clob");
		dcbmDataType.addElement("blob");

		JLabel jlTableName=new JLabel("���̺��");
		jtfTableName=new JTextField();
		jbtnTableName=new JButton("����");

		JLabel jlColName=new JLabel("�÷���");
		jtfColName=new JTextField();
		JLabel jlDataType=new JLabel("��������");
		jcbDataType=new JComboBox<String>(dcbmDataType);
		JLabel jlSize=new JLabel("ũ��");
		jtfSize=new JTextField();
		jcbPmKey=new JCheckBox();
		JLabel jlPmKey=new JLabel("Primary key");
		jcbNN=new JCheckBox();
		JLabel jlNN=new JLabel("Not Null");
		jbtnSetCol=new JButton("����");
		jbtnReset=new JButton("�ʱ�ȭ");

		jta=new JTextArea();
		
		
		JScrollPane jsp=new JScrollPane(jta);
		jsp.setBorder(new TitledBorder("�����ڵ�"));
		
		jbtnCreate=new JButton("����");
		
		
		setLayout(null);
		
		add(jlTableName);
		add(jtfTableName);
		add(jbtnTableName);

		add(jlColName);
		add(jtfColName);
		add(jlDataType);
		add(jcbDataType);
		
		add(jlSize);
		add(jtfSize);

		add(jcbPmKey);
		add(jlPmKey);
		add(jcbNN);
		add(jlNN);
		
		add(jbtnSetCol);
		add(jta);
		add(jbtnCreate);
		add(jbtnReset);
		
		jlTableName.setBounds(30, 30, 60, 40);
		jtfTableName.setBounds(100, 30, 150, 40);
		jbtnTableName.setBounds(270, 30, 80, 40);
		
		jlColName.setBounds(30, 90, 60, 40);
		jtfColName.setBounds(100, 90, 150, 40);
		jlDataType.setBounds(270, 90, 60, 40);
		jcbDataType.setBounds(340, 90, 120, 40);
		
		jlSize.setBounds(480, 90, 40, 40);
		jtfSize.setBounds(520, 90, 80, 40);
		
		jcbPmKey.setBounds(170, 150,30,40);
		jlPmKey.setBounds(200, 150, 90, 40);
		jcbNN.setBounds(290, 150,30,40);
		jlNN.setBounds(320, 150, 90, 40);
		
		jbtnSetCol.setBounds(410, 150, 80, 40);
		
		jta.setBounds(20, 220, 640, 300);
		jbtnCreate.setBounds(560, 540, 80, 40);
		jbtnReset.setBounds(460, 540, 80, 40);
		
		
		jbtnTableName.addActionListener(this);
		jbtnSetCol.addActionListener(this);
		jbtnReset.addActionListener(this);
		jbtnCreate.addActionListener(this);
		
		setBounds(100,100,700,650);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//TableCreate
	
	
	private void setTableName(String tName) {
		if(createQuery.toString().contains("create")) {
			JOptionPane.showMessageDialog(this, "create������ �̹� �����Ǿ��ֽ��ϴ�. ���� �� �ٽ� ������ּ���");
		return;
		}//if
		
		createQuery.append("create table ").append(tName).append("(\n)");
		jta.setText(createQuery.toString());
		
	}//setTableName
	
	private int columnCnt;//�߰��� �÷��� ���� ������ ����
	private void setColumn(String columnName,String dataType,String columnSize,boolean pk,boolean nn) {
		
		StringBuilder column=new StringBuilder();
		
		//�÷��� �߰��� �� ó���� �ƴϸ� ,�� �ִ´�
		if(columnCnt!=0) {
			column.append(",");
			column.append("\n");
		}//if
		columnCnt++;
		
		
		column.append(columnName).append(" ").append(dataType);
		
		if("char".equals(dataType)||"varchar2".equals(dataType)||
			("number".equals(dataType)&&!"".equals(columnSize))) {
			column.append("(").append(columnSize).append(")");
		}//if
		
		if(pk) {
			column.append(" constraint pk_").append( createQuery.substring(createQuery.indexOf("table")+6,
					createQuery.indexOf("("))).append(" primary key");
		}//if
		
		if(!pk&&nn) {
			column.append(" not null");
		}//if
		
		
		
		createQuery.insert(createQuery.lastIndexOf(")"),column.toString());
		jta.setText(createQuery.toString());
		
		jtfColName.setText("");
		jtfSize.setText("");
		jcbDataType.setSelectedIndex(0);
		jcbPmKey.setSelected(false);
		jcbNN.setSelected(false);
		
	}//setColumn
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==jbtnTableName) {
			String tableName=jtfTableName.getText().replace(" ","");
			if("".equals(jtfTableName.getText().trim())) {
				JOptionPane.showMessageDialog(this,"���̺���� �ʼ� �Է��Դϴ�.");
				return;
			}//if
			setTableName(tableName);
		}//if
		
		if(ae.getSource()==jbtnReset) {
			switch(JOptionPane.showConfirmDialog(this, "DDL �������� ���� �Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				columnCnt=0;
				jta.setText("");
				createQuery.delete(0,createQuery.length());
				
			}//switch
		}//if
		
		if(ae.getSource()==jbtnSetCol) {
			
			if(!createQuery.toString().contains("create table")) {
			JOptionPane.showMessageDialog(this,"���̺���� ������ �� �÷��� �߰��� �� �ֽ��ϴ�.");
			jtfTableName.requestFocus();
			return;
			
			}//if

			//�÷���
			if("".equals(jtfColName.getText().trim())) {
				JOptionPane.showMessageDialog(this,"�÷����� �ʼ� �Է��Դϴ�.");
				jtfColName.requestFocus();
			}//if
			
			//�÷�ũ��
			String dataType=(String) jcbDataType.getSelectedItem();
			if(dataType.equals("char")||dataType.equals("varchar2")){
			if("".equals(jtfSize.getText().trim())) {
				JOptionPane.showMessageDialog(this,"�÷�ũ��� �ʼ� �Է��Դϴ�.");
				jtfSize.requestFocus();
				return;
			}//if
		}//if
			
			
		//primary key
		if(jcbPmKey.isSelected()&&createQuery.toString().contains("primary key")) {
				JOptionPane.showMessageDialog(this,"���̺� Primary key�� �̹� �����մϴ�");
				return;
		}//if
			
		//Not null
		if(jcbNN.isSelected()&&jcbPmKey.isSelected()) {
				jcbNN.setSelected(false);//Primary key�� ���¿����� N.N�� �����Ѵ�.
		}//if
		
		setColumn(jtfColName.getText().replaceAll(" ",""),dataType,jtfSize.getText().replaceAll(" ",""),
				jcbPmKey.isSelected(),jcbNN.isSelected());
	}//if
		
		if(ae.getSource()==jbtnCreate) {
			if("".equals(createQuery.toString())) {
				JOptionPane.showMessageDialog(this,"�������� ���� �������ּ���");
				return;
			}//if
			
			try {
				createTable(createQuery.toString());
				JOptionPane.showMessageDialog(this,"���̺��� �����Ǿ����ϴ�.");
				createQuery.delete(0,createQuery.length());
				columnCnt=0;
				jta.setText("");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this,"���̺��� �̹� �����մϴ�.");
				e.printStackTrace();
			}//catch
			
			
		}//if
	
	}//actionPerformed
	
	
	public void createTable(String code) throws SQLException {
		DbConnection dc=DbConnection.getInstance();
		//1. ����̹��ε�
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
		//2. Ŀ�ؼǾ��
			con=dc.getConnection();
		//3. ������ ������ü ���
			String sql=code;
			pstmt=con.prepareStatement(sql);
		//4. ���ε庯���� ������
		//5. �������� �� ��� ���
			pstmt.execute();
			columnCnt=0;
			
		}finally {
		//6. ���� ����
			dc.close(null, pstmt, con);
		}//finally
		
	}//createTable

	public static void main(String[] args) {
		new TableCreate();
	}//main
}//class
