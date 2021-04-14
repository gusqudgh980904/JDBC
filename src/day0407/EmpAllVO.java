package day0407;

public class EmpAllVO {
private int empno;
private String ename;
private int deptno;
private String job;
private String hiredate;


public EmpAllVO() {
}//EmpAllVO


public EmpAllVO(int empno, String ename, int deptno, String job, String hiredate) {
	this.empno = empno;
	this.ename = ename;
	this.deptno = deptno;
	this.job = job;
	this.hiredate = hiredate;
}//EmpAllVO


public int getEmpno() {
	return empno;
}


public void setEmpno(int empno) {
	this.empno = empno;
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


public String getHiredate() {
	return hiredate;
}


public void setHiredate(String hiredate) {
	this.hiredate = hiredate;
}


	

	
}//class
