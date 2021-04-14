package day0413;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;

public class Work0412DAO {
	private static Work0412DAO wDAO;
	
	private Work0412DAO() {
		
	}
	
	public static Work0412DAO getInstance() {
		if( wDAO == null) {
			wDAO = new Work0412DAO();
		}
		return wDAO;
	}
	
	public List<WorkAllVO> selectAllWork()throws SQLException{
		List<WorkAllVO> list = new ArrayList<WorkAllVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dc =DbConnection.getInstance();
		//1.
		try {
		//2.
			con = dc.getConnection();
		//3.
			String selectWork = "select num, name, age, addr from work12 order by num";
			pstmt = con.prepareStatement(selectWork);
		//4.
			 //���ε庯���� �����Ƿ� pass
		//5.
			rs = pstmt.executeQuery();
			
			WorkAllVO waVO = null;
			while(rs.next()) {
				//Ŀ���� �����ϴ� ���ڵ���ġ�� �÷����� �޾ƿͼ� VO����
				waVO = new WorkAllVO(rs.getInt("num"), rs.getString("name"), 
						rs.getInt("age"), rs.getString("addr"));
				//���ڵ��� ������ ���� VO�� list�� �߰�
				list.add(waVO);
			}
		}finally {
		//6.
			dc.close(rs, pstmt, con);
		}
		return list;
	}
	
	public void insertWork(WorkAddVO waVO)throws SQLException{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		DbConnection dc = DbConnection.getInstance();
		//1.
		try {
		//2.
			con = dc.getConnection();
		//3.
			String insertWork = "insert into work12(num,name,age,addr)values(seq_work.nextval,?,?,?)";
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
		}
	}
	
	public int deleteWork(int num)throws SQLException{
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		DbConnection dc = DbConnection.getInstance();
		//1.����̹��ε�	
		try {
		//2.Ŀ�ؼǾ��
		con = dc.getConnection();
		//3.������ ������ü ���
		String deleteWork = "delete from work12 where num = ?";
		pstmt = con.prepareStatement(deleteWork);
		//4.���ε庯���� �� ����
		pstmt.setInt(1, num);
		//5.������ ���� �� ��� ���
		cnt = pstmt.executeUpdate();
		}finally {
		//6.���� ����
			dc.close(null, pstmt, con);
		}
		return cnt;
	}
	
	public int updateWork(String name, int age, String addr, int num)throws SQLException{
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		DbConnection dc = DbConnection.getInstance();
		
		//1. ����̹� �ε�
		try {
		//2. Ŀ�ؼ� ���
			con = dc.getConnection();
		//3. ������ ������ü ���
			StringBuilder updateWork = new StringBuilder();
			updateWork
			.append("	update work12		")
			.append("   set 	name=?, age=?, addr=?")
			.append("	where num =?	");
			
			pstmt = con.prepareStatement(updateWork.toString());			
		//4. ���ε忡 ������ �� �Ҵ�
			pstmt.setString(1,  name);
			pstmt.setInt(2, age);
			pstmt.setString(3, addr);
			pstmt.setInt(4, num);
		//5. ������ ���� �� ��� ���
			cnt = pstmt.executeUpdate();
			
		}finally {
		//6. �������
			dc.close(null, pstmt, con);
		}		
		return cnt;
	}

	
	
}//class
