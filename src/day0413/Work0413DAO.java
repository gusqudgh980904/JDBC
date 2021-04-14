package day0413;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;




public class Work0413DAO {

	private static Work0413DAO wDAO;
	
	private Work0413DAO(){
		
	}//Work0413DAO
	
	public static Work0413DAO getInstance() {
		
		if(wDAO==null) {
			wDAO=new Work0413DAO();
		}//if
		
		
		return wDAO;
	}//getInstance
	
	
	/**
	 * ��� ���ڵ� ��ȸ
	 * @return
	 * @throws SQLException
	 */
	public List<WorkAllVO> selectAllWork()throws SQLException{
		List<WorkAllVO> list=new ArrayList<WorkAllVO>();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.����̹� �ε�
		try {
			
		//2.
			con=dc.getConnection();
		//3.
			String selectWork="select num,name,age,addr from work order by num";
			pstmt=con.prepareStatement(selectWork);
		//4.
		//5.
			rs=pstmt.executeQuery();
			
			WorkAllVO waVO=null;
			while(rs.next()) {
				//Ŀ���� �����ϴ� ���ڵ� ��ġ�� �÷����� �޾ƿͼ� VO�� ����
				waVO=new WorkAllVO(rs.getInt("num"),rs.getString("name"),rs.getInt("age"),rs.getString("addr"));
				//���ڵ��� ������ ���� VO�� ����Ʈ�� �߰�
				list.add(waVO);
			}//while
			
		}finally {
		//6.�������
			dc.close(rs, pstmt, con);
		}//finally
		
		
		return list;
	}//selectAllWork
	
	
	/**
	 * ���ڵ� �߰�
	 * @param waVO
	 * @throws SQLException
	 */
	public void insertWork(WorkAddVO waVO)throws SQLException{
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.
		try {
			
		//2.
			con=dc.getConnection();
		//3.
			String insertWork="insert into work(num,name,age,addr) values(seq_work.nextval,?,?,?)";
			pstmt=con.prepareStatement(insertWork);
		//4.
			pstmt.setString(1, waVO.getName());
			pstmt.setInt(2, waVO.getAge());
			pstmt.setString(3, waVO.getAddr());
			
		//5.
			pstmt.executeUpdate();
		}finally {
		//6.
		dc.close(null, pstmt, con);	
		}//finally
		
		
	}//insertWork
	
	
	public int deleteWork(int num)throws SQLException {
		int cnt=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.
		try {
			
		//2.
			con=dc.getConnection();
		//3.
			String deleteWork="delete from work where num=?";
			pstmt=con.prepareStatement(deleteWork);
		//4.
			pstmt.setInt(1,num);
		//5.
			cnt=pstmt.executeUpdate();
		}finally {
		//6.
		dc.close(null, pstmt, con);	
		}//finally
		
		return cnt;
	}//removeWork
	
	
	/**
	 * ��ȣ�� ������ �̸�,����,�ּҸ� �����ϴ� ��
	 * @param waVO
	 * @return
	 * @throws SQLException
	 */
	public int updateWork(WorkAllVO waVO)throws SQLException {
		int cnt=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		DbConnection dc=DbConnection.getInstance();
		//1.
		try {
		//2.
			con=dc.getConnection();
		//3.
			StringBuilder updateWork=new StringBuilder();
			updateWork
			.append("	update work			")
			.append("	set		name=?,age=?,addr=?	")
			.append("	where	num=?		");
			pstmt=con.prepareStatement(updateWork.toString());
		//4.
			pstmt.setString(1,waVO.getName());
			pstmt.setInt(2, waVO.getAge());
			pstmt.setString(3,waVO.getAddr());
			pstmt.setInt(4,waVO.getNum());
		//5.
			cnt=pstmt.executeUpdate();
		}finally {
		//6.
		dc.close(null, pstmt, con);	
		}//finally
		
		return cnt;
	}//updateWork
	
}//class
