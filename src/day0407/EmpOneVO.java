package day0407;

import java.sql.Date;

public class EmpOneVO {
	
	private String ename;
	private int deptno;
	private String job;
	private Date hiredate;//조회할 때 Oracle의 date형을 저장하기 위해 java.sql.Date클래스 사용
	
	public EmpOneVO() {
	}

	public EmpOneVO(String ename, int deptno, String job, Date hiredate) {
		this.ename = ename;
		this.deptno = deptno;
		this.job = job;
		this.hiredate = hiredate;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	
	
	
	
	
	
}//class

