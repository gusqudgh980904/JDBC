package day0409;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunPreparedStatementDAO {

	public void selectMenu() {
		boolean exitFlag=false;
		String menu="�μ�����\n 1.�μ��Է� 2.�μ����� 3.�μ����� 4.���μ���ȸ 5.Ư���μ���ȸ 6.����";
		String inputMenu="";
		do {
			inputMenu=JOptionPane.showInputDialog(menu);
			
			switch(inputMenu) {
			case "1":addCpDept(); break;
			case "2":modifyCpDept(); break;
			case "3":removeCpDept(); break;
			case "4":searchAllCpDept(); break;
			case "5":searchOneCpDept(); break;
			case "6":exitFlag=true; break;
			default:
				JOptionPane.showMessageDialog(null, "�޴��� ��ȣ�� 1~6������ �Է�");
			}//case
		}while(!exitFlag);
		
	}//selectMenu
	
	
	public void addCpDept() {
		String inputData
		=JOptionPane.showInputDialog("�μ������߰�\n�Է� ��)�μ���ȣ,�μ���,��ġ\n�� �������� �ۼ����ּ���");
		
		String[] tempData=inputData.replaceAll(" ","").split(",");
		
		if(tempData.length!=3) {
			JOptionPane.showMessageDialog(null,"�Է� ������������ �ùٸ��� �ʽ��ϴ�.\n��)�μ���ȣ,�μ���,��ġ");
			//��ȯ���� void�� �� ����ϴ� return;
			//��ȯ���� void�� �� return�� ����ϸ� �ڵ��� ������ ���߰� ȣ���� ������ ���ư���� �ǹ�
			return;
		}//if
		
		//���� �� �Էµ� ���
		DeptVO dVO=new DeptVO(Integer.parseInt(tempData[0]),tempData[1],tempData[2]);
		
		//Singleton Patter�� ���Ե� DAOŬ�������
		PreparedStatementDAO psDAO=PreparedStatementDAO.getInstace();
		
		try {
			psDAO.insertCpDept(dVO);
			JOptionPane.showMessageDialog(null,dVO.getDeptno()+"�� �μ������� �߰��Ǿ����ϴ�");
		} catch (SQLException se) {
			se.printStackTrace();
			
			String errMsg=se.getMessage();
			if(se.getErrorCode()==1) {
			errMsg="�μ���ȣ�� �̹� �����մϴ�";
			}//if
			
			if(se.getErrorCode()==1438) {
				errMsg="�μ���ȣ�� �ʰ��Ǿ����ϴ�.�μ���ȣ 2�ڸ�";
			}//if
			
			if(se.getErrorCode()==12899) {
				errMsg="�μ��� �Ǵ� ��ġ�� �ʰ��Ǿ����ϴ�.�μ��� �ѱ�4�� �Ǵ� ����14��,��ġ �ѱ�4�� �Ǵ� ����13��";
			}//if
			
			JOptionPane.showMessageDialog(null, errMsg);
			
		}//catch
		
	}//addCpDept
	
	
	public void modifyCpDept() {
		String inputData
		=JOptionPane.showInputDialog("�μ���������\n�Է� ��)�μ���ȣ,�μ���,��ġ\n�� �������� �ۼ����ּ���");
		
		String[] tempData=inputData.replaceAll(" ","").split(",");
		
		if(tempData.length!=3) {
			JOptionPane.showMessageDialog(null,"�Է� ������������ �ùٸ��� �ʽ��ϴ�.\n��)�μ���ȣ,�μ���,��ġ");
			//��ȯ���� void�� �� ����ϴ� return;
			//��ȯ���� void�� �� return�� ����ϸ� �ڵ��� ������ ���߰� ȣ���� ������ ���ư���� �ǹ�
			return;
		}//if
		
		//���� �� �Էµ� ���
		DeptVO dVO=new DeptVO(Integer.parseInt(tempData[0]),tempData[1],tempData[2]);
		
		//Singleton Patter�� ���Ե� DAOŬ�������
		PreparedStatementDAO psDAO=PreparedStatementDAO.getInstace();
		
		try {
			int cnt=psDAO.updateCpDept(dVO);
			if(cnt==1) {
				JOptionPane.showMessageDialog(null,dVO.getDeptno()+"�� �μ������� ����Ǿ����ϴ�");
			}else {
				JOptionPane.showMessageDialog(null,dVO.getDeptno()+"�� �μ������� ������� �ʾҽ��ϴ�");
			}//else
		} catch (SQLException se) {
			se.printStackTrace();
			
			String errMsg=se.getMessage();
			
			if(se.getErrorCode()==12899) {
				errMsg="�μ��� �Ǵ� ��ġ�� �ʰ��Ǿ����ϴ�.�μ��� �ѱ�4�� �Ǵ� ����14��,��ġ �ѱ�4�� �Ǵ� ����13��";
			}//if
			
			JOptionPane.showMessageDialog(null, errMsg);
			
		}//catch
		
		
		
	}//updateCpDept
	
	
	public void removeCpDept() {
		String inputData=JOptionPane.showInputDialog("�μ���������\n������ �μ���ȣ�� �Է����ּ���");
		
		if(inputData!=null && !"".equals(inputData)) {
			try {
				int deptno=Integer.parseInt(inputData);
				//singleton patter�� ���Ե� ��ü ���
				PreparedStatementDAO psDAO=PreparedStatementDAO.getInstace();
				int cnt=psDAO.deleteCpDept(deptno);
				
				if(cnt==1) {
					JOptionPane.showMessageDialog(null,deptno+"�μ������� ���� �Ǿ����ϴ�");
				}else {
					JOptionPane.showMessageDialog(null,deptno+"�μ������� �������� �ʽ��ϴ�");
				}//else
				
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null,"�μ���ȣ�� ���ڿ��� �մϴ�");
			}catch (SQLException e) {
				e.printStackTrace();
			}//catch
			
		}//if
		
	}//deleteCpDept
	
	
	public void searchAllCpDept() {
		//Singleton Pattern�� ���Ե� Ŭ������ ����Ͽ� ��ü�� ���
		PreparedStatementDAO psDAO=PreparedStatementDAO.getInstace();
		
		try {
			List<DeptVO> list=psDAO.selectAllCpDept();
			
			StringBuilder output=new StringBuilder();
			output.append("�μ���ȣ\t�μ���\t��ġ\n")
			.append("------------------------------\n");
			
			if(list.isEmpty()) {
				output.append("�μ������� �������� �ʽ��ϴ�\n");
			}//if
			
			for(DeptVO dv:list) {
				output.append(dv.getDeptno()).append("\t");
				output.append(dv.getDname()).append("\t");
				output.append(dv.getLoc()).append("\n");
			}//for
			
			output.append("------------------------\n");
			if(!list.isEmpty()) {
				output.append(list.size()+"���� ������ ��ȸ�Ǿ����ϴ�.");
			}//if
			
			JTextArea jta=new JTextArea(output.toString(),10,50);
			jta.setEditable(false);
			JScrollPane jsp=new JScrollPane(jta);
			
			JOptionPane.showMessageDialog(null, jsp);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
		
		
		
	}//searchAllCpDept
	
	
	public void searchOneCpDept() {
		String inputData=JOptionPane.showInputDialog("�μ�������ȸ\n��ȸ�� �μ���ȣ�� �Է����ּ���");
		
		if(inputData!=null && !"".equals(inputData)) {
			try {
				int deptno=Integer.parseInt(inputData);
				//singleton patter�� ���Ե� ��ü ���
				PreparedStatementDAO psDAO=PreparedStatementDAO.getInstace();
				
				DeptOneVO doVO=psDAO.selectOneCpDept(deptno);
				
				StringBuilder sb=new StringBuilder();
				
				if(doVO==null) {
					sb.append(deptno+"�μ���ȣ�� �������� �ʽ��ϴ�");
					JOptionPane.showMessageDialog(null,sb);
				}else {
					sb.append(deptno)
					.append("�μ���ȣ ��ȸ��� �Դϴ�\n")
					.append(doVO.getDname())
					.append("/")
					.append(doVO.getLoc());
					JOptionPane.showMessageDialog(null,sb);
				}//else
				
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null,"�μ���ȣ�� ���ڿ��� �մϴ�");
			}catch (SQLException e) {
				e.printStackTrace();
			}//catch
			
		}//if	
		
		
	}//searchOneCpDept
	
	
	public static void main(String[] args) {
		RunPreparedStatementDAO rpsDAO=new RunPreparedStatementDAO();
		rpsDAO.selectMenu();
		
	}//main

}//class
