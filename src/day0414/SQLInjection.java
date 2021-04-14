package day0414;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SQLInjection extends JFrame implements ActionListener {

	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JLabel jlOutput;
	
	public SQLInjection() {
		super("SQLInjection ����");
		jtfId=new JTextField();
		jpfPass=new JPasswordField();
		jlOutput=new JLabel("��� ���");
		
		jtfId.setBorder(new TitledBorder("���̵�"));
		jpfPass.setBorder(new TitledBorder("��й�ȣ"));
		jlOutput.setBorder(new TitledBorder("���"));
		
		setLayout(new GridLayout(3,1));
		
		add( jtfId );
		add( jpfPass );
		add( jlOutput );
		
		jtfId.addActionListener(this);
		jpfPass.addActionListener(this);
		
		setBounds(100, 100, 300, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//SQLInjection
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==jtfId) {
			chkNull();
		}//if
		
		if(ae.getSource()==jpfPass) {
			chkNull();
		}//if
		
	}//actionPerformed 
	
	
	private void chkNull() {
		String id=jtfId.getText();
		String pass=new String(jpfPass.getPassword()).replaceAll(" ","");
		
		if("".equals(id)) {
			JOptionPane.showMessageDialog(this,"���̵�� �ʼ� �Է�");
			jtfId.requestFocus();
			return;
		}//if
		
		if("".equals(pass)) {
			JOptionPane.showMessageDialog(this,"��й�ȣ�� �ʼ� �Է�");
			jpfPass.requestFocus();
			return;
		}//if
		
		login(id,pass);
		
	}//chkNull
	
	private void login(String id,String pass) {
		InjectionDAO iDao=new InjectionDAO();
		try {
			String name=iDao.preparedStatementlogin(id,pass);
//			String name=iDao.Statementlogin(id,pass);
			if("".equals(name)) {
				jlOutput.setText("���̵� ��й�ȣ�� �Է��ϼ���");
			}else {
				jlOutput.setText(name+"���� �α����ϼ̽��ϴ�.");
			}//else
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		
		
		
	}//login
	

	public static void main(String[] args) {
		new  SQLInjection();
	}//main

}//class
