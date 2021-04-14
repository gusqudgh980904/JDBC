package day0408;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *숙제. 
JOptionPane.InputDialog를 사용하여 제조국을 입력받아  
제조사,모델명,옵션,가격,입력일을 JOptionPane.MessageDialog에 
JTextArea를 사용하여 출력하세요.

단, 출력은 입력일의 내림차순으로 정렬했을 때 1~10번째 레코드만 출력합니다.
옵션은 첫번째, 까지만 출력해주세요.

 * @author user
 */
public class Homework0408 {

	public List<Homework0408VO> output(String country) throws SQLException {
	//레코드를 담을 리스트
	List<Homework0408VO> list=new ArrayList<Homework0408VO>();	
	
	//1.드라이버로딩
	try {
		Class.forName("oracle.jdbc.OracleDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}//catch
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	
	try {
	//2.Connection 얻기
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="scott";
	String pass="tiger";
		
	con=DriverManager.getConnection(url,id,pass);
	
	//3.쿼리문 생성 객체 얻기
	stmt=con.createStatement();
	//4.쿼리문 수행 후 결과 얻기
	String output="select  country,MAKER, MODEL,substr(car_option,0,instr(car_option,',')) car_option,price,hiredate,hiredate_rank\r\n"
			+ "\r\n"
			+ "from (select COUNTRY, cc.MAKER, cma.MODEL, cmo.CAR_YEAR, cmo.PRICE,cmo.car_option,\r\n"
			+ "	row_number() over(order by cmo.HIREDATE desc) as hiredate_rank,cmo.hiredate\r\n"
			+ "	from car_country cc,car_maker cma,car_model cmo\r\n"
			+ " where (cma.maker=cc.maker and cmo.model=cma.model) and country like '"+country+"')\r\n"
			+ "where hiredate_rank between 1 and 10";


	
	rs=stmt.executeQuery(output);
	
	int price=0;
	String maker="",model="",CarOption="",hiredate="";
	
	while(rs.next()) {
		country=rs.getString("country");
		maker=rs.getString("maker");
		model=rs.getString("model");
		CarOption=rs.getString("car_option");
		price=rs.getInt("price");
		hiredate=rs.getString("hiredate");
		
		System.out.println(country+"/"+maker+"/"+model+"/"+CarOption+"/"+price+"/"+hiredate);
		
	}//while
	
	
	
	
	
	}finally {
	//5.연결끊기
	if(rs!=null) {rs.close();}
	if(con!=null) {con.close();}
	if(stmt!=null) {stmt.close();}
	}//finally
		
	return list;
	}//output
	
	
	public static void main(String[] args) {
		Homework0408 hm=new Homework0408();
		try {
			hm.output("수입");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//main

}//class
