package day0409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern�� ������ Ŭ����
 * @author user
 */
public class PreparedStatementDAO {
	
	private static PreparedStatementDAO psDAO;
	
	private PreparedStatementDAO() {
		
	}//PreparedStatementDAO
	
	
	/**
	 * Singleton���� ������ instance�� ��ȯ�ϴ� ��
	 * @return �ϳ��� ��ü
	 */
	public static PreparedStatementDAO getInstace() {
		if(psDAO==null) {
			psDAO=new PreparedStatementDAO();
		}//if
		
		return psDAO;
	}//getInstace
	
	
	/**
	 * Ŀ�ؼ��� ��ȯ�ϴ� ��
	 * @return DB�� ����� Connection
	 */
	private Connection getConnection() throws SQLException {
		//1.����̹� �ε�
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2.Connection ���
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id="scott";
		String pass="tiger";
		
		Connection con=DriverManager.getConnection(url,id,pass);
		
		return con;
	}//getConnection
	
	
	/**
	 * �μ������� �߰��ϴ� ��
	 * @param dVO �μ���ȣ,�μ���,��ġ
	 * @throws SQLException
	 */
	public void insertCpDept(DeptVO dVO)throws SQLException {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		//1.DB Vendor�翡�� ������ ����̹� �ε�
		try {
		//2.�ε��� ����̹��� ����Ͽ� Connection ���
		con=getConnection();
		//3.�������� �Ҵ��Ͽ� ������ ���� ��ü ���
		String insertCpDept="insert into cp_dept(deptno,dname,loc) values(?,?,?)";
		pstmt=con.prepareStatement(insertCpDept);
		
		//4.���ε庯���� �� �Ҵ�
		pstmt.setInt(1,dVO.getDeptno());
		pstmt.setString(2,dVO.getDname());
		pstmt.setString(3,dVO.getLoc());
		
		//5.������ ���� �� ��� ���
		pstmt.executeUpdate();
		
		}finally {
		//6.�������
		close(null,pstmt,con);
		}//finally
		
		
	}//insertCpEmp
	
	
	/**
	 * �μ������� �����ϴ� ��
	 * �μ���ȣ�� ��ġ�ϴ� �μ���� ��ġ�� ����
	 * @param dVO
	 * @return
	 * @throws SQLException
	 */
	public int updateCpDept(DeptVO dVO)throws SQLException{
	int cnt=0;	
	
	Connection con=null;
	PreparedStatement pstmt=null;
	
	//1.����̹��ε�
	try {
	//2.�ε��� ����̹����� Connection ���
		con=getConnection();
	//3.���������� ��ü ���
		StringBuilder updateCpDept=new StringBuilder();
		updateCpDept
		.append("	update cp_dept			")
		.append("	set		dname=?,loc=?	")
		.append("	where	deptno=?		");
		
		pstmt=con.prepareStatement(updateCpDept.toString());
		
	//4.���ε庯���� ���Ҵ�
		pstmt.setString(1,dVO.getDname());
		pstmt.setString(2,dVO.getLoc());
		pstmt.setInt(3,dVO.getDeptno());
		
	//5.������ ���� �� ��� ���
		cnt=pstmt.executeUpdate();
		
	}finally {
	//6.�������
	close(null,pstmt,con);	
	}//finally
		
	return cnt;	
	}//updateCpEmp
	
	
	/**
	 * �μ���ȣ�� �Է¹޾� �μ������� �����ϴ� ��
	 * @param deptno ������ �μ���ȣ
	 * @return
	 * @throws SQLException
	 */
	public int deleteCpDept(int deptno)throws SQLException{
	int cnt=0;
	
	Connection con=null;
	PreparedStatement pstmt=null;
	
	//1.����̹��ε�
	try {
	//2.Ŀ�ؼǾ��
	con=getConnection();
	//3.������ ���� ��ü ���
	String deleteCpDept="delete from cp_dept where deptno=?";
	pstmt=con.prepareStatement(deleteCpDept);
	//4.���ε庯���� �� ����
	pstmt.setInt(1,deptno);
	//5.������ ���� �� ��� ���
	cnt=pstmt.executeUpdate();
	}finally {
	//6.���� ����
	close(null,pstmt,con);	
	}//finally
	
	return cnt;	
	}//deleteCpEmp
	
	
	/**
	 * ��� �μ����� ��ȸ
	 * @return
	 */
	public List<DeptVO> selectAllCpDept()throws SQLException{
		List<DeptVO> list=new ArrayList<DeptVO>();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//1.����̹� �ε�
		try {
		//2.Ŀ�ؼǾ��
			con=getConnection();
		//3.������ ���� ��ü ���
			String selectCpDept="select deptno,dname,loc from cp_dept";
			pstmt=con.prepareStatement(selectCpDept);
		//4.���κ����� ���Ҵ�
		//5.������ ���� �� ��� ���
		rs=pstmt.executeQuery();	

		DeptVO dVO=null;
		while(rs.next()) {
			//��ȸ�� �÷� ���� VO�� �Ҵ�
			dVO=new DeptVO(rs.getInt("deptno"),rs.getString("dname"),rs.getString("loc"));
			//������ VO(���ڵ� ���� ������ ����)�� List�� �߰�
			list.add(dVO);
		}//while
		
		}finally {
		//6.���� ����
		close(rs,pstmt,con);
		}//finally
		
		return list;
	}//selectAllCpDept
	
	
	/**
	 * �μ���ȣ�� �Է¹޾� �� �μ���ȣ�� �ش��ϴ� ������ ��ȸ
	 * @param deptno ��ȸ�� �μ���ȣ
	 * @return
	 * @throws SQLException
	 */
	public DeptOneVO selectOneCpDept(int deptno)throws SQLException {
	DeptOneVO doVO=null;
	
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	//1.����̹��ε�
	try {
	//2.Ŀ�ؼǾ��
		con=getConnection();
	//3.������ ������ü ���
		StringBuilder selectCpDept=new StringBuilder();
		selectCpDept
		.append("	select	dname,loc")
		.append("	from	cp_dept")
		.append("	where	deptno=?");
		
		pstmt=con.prepareStatement(selectCpDept.toString());
	//4.���ε庯���� ������
		pstmt.setInt(1,deptno);
	//5.������ ���� �� ��� ���
		rs=pstmt.executeQuery();
		
		if(rs.next()){
			doVO=new DeptOneVO(rs.getString("dname"),rs.getString("loc"));
		}//if
		
	}finally {
	//6.�������
	close(rs,pstmt,con);	
	}//finally
	
	return doVO;
	}//selectOneCpDept
	
	
	/**
	 * ResultSet,PreparedStatement,Connection�� ������ ���� ��
	 * @param rs
	 * @param ptmt
	 * @param con
	 * @throws SQLException
	 */
	public void close(ResultSet rs,PreparedStatement pstmt,Connection con)throws SQLException{
	if(rs!=null) {rs.close();}
	if(pstmt!=null) {pstmt.close();}
	if(con!=null) {con.close();}
		
	}//close

}//class
