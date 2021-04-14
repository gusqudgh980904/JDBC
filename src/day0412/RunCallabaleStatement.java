package day0412;

import static java.lang.Integer.parseInt;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * ����
 * @author user
 */
public class RunCallabaleStatement {

	public void addCpEmp() {
		
		String inputData=JOptionPane.showInputDialog("�����ȣ,�����,�μ���ȣ,������ �Է��ϼ���");
		String[] data=inputData.split(",");
		
		UseCallableStatementDAO ucsDAO=UseCallableStatementDAO.getInstance();
		try {
			String msg=ucsDAO.insertCpEmp4(new CpEmp4VO(parseInt(data[0]),data[1],parseInt(data[2]),data[3]));
			JOptionPane.showMessageDialog(null,msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		 
	}//addCpEmp
	
	
	public void modifyCpEmp() {
		
		String inputData=JOptionPane.showInputDialog("����\n�����ȣ,�μ���ȣ,������ �Է��ϼ���");
		String[] data=inputData.split(",");
		
		UseCallableStatementDAO ucsDAO=UseCallableStatementDAO.getInstance();
		try {
			String msg=ucsDAO.updateCpEmp4(new CpEmpModifyVO(parseInt(data[0]),data[2],parseInt(data[1])));
			JOptionPane.showMessageDialog(null,msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		 
	}//modifyCpEmp
	
	
	public void removeCpEmp() {
		String inputData=JOptionPane.showInputDialog("����\n�����ȣ�� �Է��ϼ���");
		
		UseCallableStatementDAO ucsDAO=UseCallableStatementDAO.getInstance();
		try {
			String msg=ucsDAO.deleteCpEmp(parseInt(inputData));
			JOptionPane.showMessageDialog(null,msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
	}//deleteCpEmp
	
	
	public void searchZipcode() {
	String inputData=JOptionPane.showInputDialog("����ȣ��ȸ\n���� �Է��ϼ���");
		
		UseCallableStatementDAO ucsDAO=UseCallableStatementDAO.getInstance();
		try {
			List<ZipcodeVO> list=ucsDAO.selectZipcode(inputData);
			
			StringBuilder zipOutput=new StringBuilder();
			zipOutput.append(inputData).append(" �˻����\n")
			.append("---------------------------------------\n")
			.append("�����ȣ\n�ּ�\n")
			.append("---------------------------------------\n");
			
			if(list.isEmpty()) {
				zipOutput.append("�˻������ �������� �ʽ��ϴ�.\n���̸��� Ȯ�����ּ���");
			}//if
			
			for(ZipcodeVO zVO:list) {
				zipOutput.append(zVO.getZipcode()).append("\t").append(zVO.getSido()).append(" ")
				.append(zVO.getGugun()).append(" ").append(zVO.getDong()).append(" ").append(zVO.getBunji()).append("\n");
			}//for
			
			JTextArea jta=new JTextArea(zipOutput.toString(),10,60);
			JScrollPane jsp=new JScrollPane(jta);
			
			JOptionPane.showMessageDialog(null,jsp);
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		
		
	}//searchZipcode
	
	
	public static void main(String[] args) {
		RunCallabaleStatement rcs=new RunCallabaleStatement();
//		rcs.addCpEmp();
//		rcs.modifyCpEmp();
//		rcs.removeCpEmp();
		rcs.searchZipcode();
	}//main

}//class
