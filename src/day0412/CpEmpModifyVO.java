package day0412;

public class CpEmpModifyVO {

	private int empno;
	private String job;
	private int deptno;
	
	public CpEmpModifyVO() {
	}
	
	public CpEmpModifyVO(int empno, String job, int deptno) {
		this.empno = empno;
		this.job = job;
		this.deptno = deptno;
	}
	
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	
	
	
	
}//CpEmpModifyVO
