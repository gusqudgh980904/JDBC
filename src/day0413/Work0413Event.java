package day0413;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Work0413Event extends WindowAdapter implements ActionListener, MouseListener {

	private RunCrudDAO rcDAO;
	private boolean selectFlag;
	
	public Work0413Event(RunCrudDAO rcDAO) {
		this.rcDAO=rcDAO;
	}//Work0413Event
	
	@Override
	public void windowClosing(WindowEvent we) {
		rcDAO.dispose();
	}//windowClosing

	@Override
	public void mouseClicked(MouseEvent me) {
		//���콺 ��ư�� Ŭ�� �Ǿ��� �� �����ϴ� method
		switch(me.getButton()) {
		case MouseEvent.BUTTON1:
			//���õ� �������� �޾ƿͼ�  JTextField�� �߰��Ѵ�
			String csvData=rcDAO.getJl().getSelectedValue();
			String[] arrData=csvData.split(",");
			
			rcDAO.getJtfNum().setText(arrData[0]);
			rcDAO.getJtfName().setText(arrData[1]);
			rcDAO.getJtfAge().setText(arrData[2]);
			rcDAO.getJtfAddr().setText(arrData[3]);
			//JList�� item�� ������ �Ǿ�����
			selectFlag=true;
		}//if
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		//�߰�
		if(ae.getSource()==rcDAO.getJbtnInsert()) {
			addWork();
		}//if
		
		//�ݱ�
		if(ae.getSource()==rcDAO.getJbtnClose()) {
			rcDAO.dispose();
		}//if
		
		//����
		if(ae.getSource()==rcDAO.getJbtnUpdate()) {
			if(!selectFlag) {
				JOptionPane.showMessageDialog(rcDAO,"�����Ͻ÷��� �������� ���� �������ּ���");
				return;
			}//if
			modifyWork();
		}//if
		
		//����
		if(ae.getSource()==rcDAO.getJbtnDelete()) {	if(!selectFlag) {
			JOptionPane.showMessageDialog(rcDAO,"�����Ͻ÷��� �������� ���� �������ּ���");
			return;
		}//if
		removeWork();
		}//if
		
		//��ư�� ������ �۾��� �����ϰ� �� ���Ŀ��� JList�� item���û��¸� �����Ѵ�
		selectFlag=false;
		
	}//actionPerformed

	public void addWork() {
		//JTextField�� ���� �޾ƿͼ� DB�� �߰�
		String name=rcDAO.getJtfName().getText().trim();
		String age=rcDAO.getJtfAge().getText().trim();
		String addr=rcDAO.getJtfAddr().getText().trim();
		
		if("".equals(name)) {
			JOptionPane.showMessageDialog(rcDAO,"�̸��� �ʼ� �Է�");
			rcDAO.getJtfName().requestFocus();
			return;
		}//if
		
		if("".equals(age)) {
			JOptionPane.showMessageDialog(rcDAO,"���̴� �ʼ� �Է�");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//if
		
		int intAge=0;
		try {
		intAge=Integer.parseInt(age);
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(rcDAO,"���̴� ������������ �Է�");
			rcDAO.getJtfAge().setText("");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//catch
		
		if("".equals(addr)) {
			JOptionPane.showMessageDialog(rcDAO,"�ּҴ� �ʼ� �Է�");
			rcDAO.getJtfAddr().requestFocus();
			return;
		}//if
		
		WorkAddVO waVO=new WorkAddVO(name,intAge,addr);
		Work0413DAO wDAO=Work0413DAO.getInstance();
		try {
			wDAO.insertWork(waVO);//�����͸� DBMS Table�� �߰��ϰ�,
			setJlistWork();//�Էµ� ������ �����͸� �����Ͽ� JList�� ����ϰ�
			JOptionPane.showMessageDialog(rcDAO,"�Է��Ͻ� ������ �߰��Ǿ����ϴ�.");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(rcDAO,"���ܹ߻�");
		}//catch
		
	}//addWork
	
	
	/**
	 * WORK���̺��� ��� ���ڵ带 ��ȸ�Ͽ� JList�� �����ϴ� ��
	 */
	public void setJlistWork() {
		Work0413DAO wDAO=Work0413DAO.getInstance();
		try {
			List<WorkAllVO> list=wDAO.selectAllWork();
			
			//������ Ŭ�������� ��(JList)�� �����͸� �����ϴ� ��ü(DefaultListModel)�� ��´�
			DefaultListModel<String> dlm=rcDAO.getDflm();
			//������ �����͸� �ʱ�ȭ
			dlm.clear();
			
			//��ȸ�� ����� Model��ü�� �����Ѵ�
			StringBuilder viewData=new StringBuilder();
			for(WorkAllVO waVO:list) {
				viewData.append(waVO.getNum()).append(",")
				.append(waVO.getName()).append(",")
				.append(waVO.getAge()).append(",")
				.append(waVO.getAddr());
				
				dlm.addElement(viewData.toString());
	
				viewData.delete(0,viewData.length());
			}//for
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rcDAO,"���񽺰� �������� ���մϴ�");
			e.printStackTrace();
		}//catch
		
	}//setJlist
	
	
	private void removeWork() {
		//JTextField�� ���� �޾ƿͼ� DB�� ����
		String num=rcDAO.getJtfNum().getText().trim();
		
		switch (JOptionPane.showConfirmDialog(rcDAO,num+"�� �����͸� �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			Work0413DAO wDAO=Work0413DAO.getInstance();
			
			try {
				int cnt=wDAO.deleteWork(Integer.parseInt(num));
				String outputMsg=num+"�� �����Ͱ� �������� �ʾҽ��ϴ�.��ȣ�� Ȯ���ϼ���";
				if(cnt==1) {
					outputMsg=num+"�� �����Ͱ� �����Ǿ����ϴ�";
					setJlistWork();
				}//if
				
				JOptionPane.showMessageDialog(rcDAO,outputMsg);
			} catch (SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(rcDAO,"��������");
			}//catch
		
		}//switch
		
	}//removeWork
	
	
	private void modifyWork() {
		//JTextField�� ���� �޾ƿͼ� DB�� ����
		String num=rcDAO.getJtfNum().getText().trim();
		String name=rcDAO.getJtfName().getText().trim();
		String age=rcDAO.getJtfAge().getText().trim();
		String addr=rcDAO.getJtfAddr().getText().trim();

		
		if("".equals(name)) {
			JOptionPane.showMessageDialog(rcDAO,"�̸��� �ʼ� �Է�");
			rcDAO.getJtfName().requestFocus();
			return;
		}//if
		
		if("".equals(age)) {
			JOptionPane.showMessageDialog(rcDAO,"���̴� �ʼ� �Է�");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//if
		
		int intAge=0;
		try {
		intAge=Integer.parseInt(age);
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(rcDAO,"���̴� ������������ �Է�");
			rcDAO.getJtfAge().setText("");
			rcDAO.getJtfAge().requestFocus();
			return;
		}//catch
		
		if("".equals(addr)) {
			JOptionPane.showMessageDialog(rcDAO,"�ּҴ� �ʼ� �Է�");
			rcDAO.getJtfAddr().requestFocus();
			return;
		}//if
		
		switch (JOptionPane.showConfirmDialog(rcDAO,name+"�� ������ �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			
			//��ȿ�� ������ ����Ǿ��ٸ� ���ҵǾ� �ִ� ���� VO�� �ִ´�
			WorkAllVO waVO=new WorkAllVO(Integer.parseInt(num),name,intAge,addr);
			
			//DB�۾��� ���� DAOŬ������ ��ü ���
			Work0413DAO wDAO=Work0413DAO.getInstance();

			try {
				int cnt=wDAO.updateWork(waVO);
				String outputMsg=waVO.getName()+"���� ������ ������� �ʾҽ��ϴ�";
				if(cnt==1) {
					outputMsg=waVO.getName()+"���� ������ ����Ǿ����ϴ�";
					setJlistWork();//���泻���� ����ڿ��� �����Ѵ�
				}//if
				
				JOptionPane.showMessageDialog(rcDAO,outputMsg);
			} catch (SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(rcDAO,"�����۾� �� �� �� ���� �����߻�");
			}//catch
			
		}//switch
		
	}//modifyWork

}//class
