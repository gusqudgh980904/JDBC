package day0407;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *	DBMS�� �۾��� ���õ� �ڵ带 �����ϴ� Ŭ���� 
 *	�������� ���� �۾��� ����
 * @author user
 */
public class StatementDAO {
	
	/**
	 * cp_emp4���̺� VO�� ���� �߰��ϴ� ��
	 * �߰������ƴϸ� ���ܻ�Ȳ ���� �ϳ��� ����� ���´�
	 * @param evo �����ȣ,�����,�μ���ȣ,������ ���� VO
	 * @throws SQLException 
	 */
	public void insertCpEmp4(EmpVO eVO) throws SQLException {
		//1.����̹��ε�(ojdbc8.jar)
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//catch
		
		//2.Ŀ�ؼǾ��
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		Statement stmt=null;
		Connection con=null;
		
		try {
			con=DriverManager.getConnection(url,id,pass);
			
		
		//3.���������� ��ü ���
			stmt=con.createStatement();
			
		//4.������ ���� �� ��� ���
			String insertCpEmp=
					"insert into cp_emp4(empno,ename,deptno,job,hiredate) values("+eVO.getEmpno()+
					",'"+eVO.getEname()+"',"+eVO.getDeptno()+",'"+eVO.getJob()+"',sysdate)";
			
			int cnt=stmt.executeUpdate(insertCpEmp);
			
			System.out.println(cnt+"�� �߰�");
			
		}finally {
			//5.�������.�ݵ�� ����Ǿ���ϱ� ������ finally ������ ����
			if(stmt!=null) {stmt.close();}
			if(con!=null) {con.close();}
			

		}//finally
		
	}//insertCpEmp4
	
	
	/**
	 * �����ȣ,�����,�μ���ȣ,������ �Է¹޾� �����ȣ�� �ش��ϴ� ���������
	 * �����ϴ� ��
	 * @param eVO ������ �������
	 * @return ����� ���� ��
	 */
	public int updateCpEmp4(EmpVO eVO) throws SQLException{
	int cnt=0;
	//1.����̹��ε�
	try {
		Class.forName("oracle.jdbc.OracleDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}//catch
	
	Connection con=null;
	Statement stmt=null;
	
	try {
	//2.Connection ���
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="scott";
	String pass="tiger";
	
	con=DriverManager.getConnection(url,id,pass);
	
	//3.������ ������ü ���
	stmt=con.createStatement();
	
	//4.������ ���� �� ��� ���
//	String updateCpEmp="update cp_emp4 set ename='"+eVO.getEname()+"',deptno="+eVO.getDeptno()+",job='"+
//	eVO.getJob()+"' where empno="+eVO.getEmpno();
	StringBuilder updateCpEmp=new StringBuilder();
	updateCpEmp.append("update cp_emp4 set ename='").append(eVO.getEname()).append("',deptno=")
	.append(eVO.getDeptno()).append(",job='").append(eVO.getJob()).append("' where empno=").append(eVO.getEmpno());
	
	cnt=stmt.executeUpdate(updateCpEmp.toString());
	
//	if(cnt==1) {
//		System.out.println("��������� ����Ǿ����ϴ�");
//	}else {
//		System.out.println("����� Ȯ���ϼ���");
//	}//end if
	
	
	
	}finally {
	//5.��������
	if(con!=null) {con.close();}
	if(stmt!=null) {stmt.close();}
	}//finally
	
	return cnt;	
	}//updateCpEmp4
	
	
	public int deleteCpEmp4(int empno) throws SQLException {
		int cnt=0;
		//1.����̹��ε�
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//catch
		
		Connection con=null;
		Statement stmt=null;
		
		try {
		//2.Connection ���
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		
		con=DriverManager.getConnection(url,id,pass);
		
		//3.������ ������ü ���
		stmt=con.createStatement();
		//4.������ ���� �� ��� ���
		String deleteCpEmp="delete from cp_emp4 where empno="+empno;
		
		cnt=stmt.executeUpdate(deleteCpEmp);
		
		}finally {
		//5.�������
		if(con!=null) {con.close();}
		if(stmt!=null) {stmt.close();}
		}//finally
		
		
		
		return cnt;	
	}//deleteCpEmp4
	
	
	public List<EmpAllVO> selectAllCpEmp4() throws SQLException{
		List<EmpAllVO> list=new ArrayList<EmpAllVO>();
		
		//1.����̹� �ε�
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//catch
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
		//2.Connection ���
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		
		con=DriverManager.getConnection(url,id,pass);
		//3.������ ���� ��ü ���
		stmt=con.createStatement();
		
		//4.������ ���� �� ��� ���
		String selectCpEmp=
		//hiredate�� to_char�Լ��� ����Ͽ� ���ڿ��� ��ȯ �� ��� ���
		"select empno,ename,deptno,job,to_char(hiredate,'yyyy-mm-dd') hiredate from cp_emp4";
		
		
		rs=stmt.executeQuery(selectCpEmp);
		
		int empno=0,deptno=0;
		String ename="",job="",hiredate="";
		
		EmpAllVO eaVO=null;
		
		while(rs.next()) {//���ڵ� �����Ͱ� �����ϴ� �� ���� ���� �����ϴ���?
			//cursor�� ��ġ�� ���ڵ��� �÷� ���� �����ͼ� ������ ����(�������� ��) 
		//�÷����� ����Ͽ� ��ȸ ������ �����
//			empno=rs.getInt("empno");
//			ename=rs.getString("ename");
//			deptno=rs.getInt("deptno");
//			job=rs.getString("job");
//			hiredate=rs.getString("hiredate");
		//�÷��� �ε����� ����Ͽ� �� ���(ó�� ���� �÷��� �ε����� 1������ �����Ѵ�
			empno=rs.getInt(1);
			ename=rs.getString(2);
			deptno=rs.getInt(3);
			job=rs.getString(4);
			hiredate=rs.getString(5);
			System.out.println(empno+"/"+ename);
			
			//�������� ���� ������ ���Ǹ� ���� VO�� ��� ����(��ȸ�� �� ���� ���ڵ尡 ����)
			eaVO=new EmpAllVO(empno, ename, deptno, job, hiredate);
			//n���� ���� �ϳ��� ��� �����ϱ� ���� List�� �߰�
			list.add(eaVO);
			
		}//while
		
		}finally {
		//5.�������
		if(rs!=null) {rs.close();}
		if(con!=null) {con.close();}
		if(stmt!=null) {stmt.close();}
		}//finally
		
	return list;	
	}//selectAllCpEmp4
	
	
	public EmpOneVO selectOneCpEmp4(int empno) throws SQLException {
		EmpOneVO eoVO=null;
		
		//1.����̹� �ε�
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//catch
		
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
		//2.Connection ���
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		
		con=DriverManager.getConnection(url,id,pass);
			
		//3.������ ���� ��ü ���
		stmt=con.createStatement();
		//4.������ ���� �� ��� ���
		StringBuilder selectCpEmp=new StringBuilder();
		//date ���������� to_char�Լ��� ������� �ʰ� �״�� ��ȸ
		//����ڰ� ���ϴ� ��¥������ �ڹٿ��� ó���ؾ��Ѵ�
		selectCpEmp.append("select ename,deptno,job,hiredate from cp_emp4 where empno=").append(empno);
		
		rs=stmt.executeQuery(selectCpEmp.toString());
		
		if(rs.next()) {//��ȸ�� ���ڵ尡 ������ ��
			eoVO=new EmpOneVO(rs.getString("ename"),rs.getInt("deptno"),rs.getString("job"),rs.getDate("hiredate"));
		}//if
		
		}finally {
		//5.���� ����
		if(rs!=null) {rs.close();}
		if(con!=null) {con.close();}
		if(stmt!=null) {stmt.close();}	
		}
		
		return eoVO;
	}//selectOneCpEmp4
	
	
	public static void main(String[] args) {
		StatementDAO s=new StatementDAO();
		try {
//			s.deleteCpEmp4(new EmpVO(1111,"������",50,"�븮"));
			s.selectAllCpEmp4();
		} catch (SQLException e) {
			e.printStackTrace();
		}//catch
	}//main

}//class
