package day0408;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *����. 
JOptionPane.InputDialog�� ����Ͽ� �������� �Է¹޾�  
������,�𵨸�,�ɼ�,����,�Է����� JOptionPane.MessageDialog�� 
JTextArea�� ����Ͽ� ����ϼ���.

��, ����� �Է����� ������������ �������� �� 1~10��° ���ڵ常 ����մϴ�.
�ɼ��� ù��°, ������ ������ּ���.

 * @author user
 */
public class Homework0408 {

	public List<Homework0408VO> output(String country) throws SQLException {
	//���ڵ带 ���� ����Ʈ
	List<Homework0408VO> list=new ArrayList<Homework0408VO>();	
	
	//1.����̹��ε�
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
	//5.�������
	if(rs!=null) {rs.close();}
	if(con!=null) {con.close();}
	if(stmt!=null) {stmt.close();}
	}//finally
		
	return list;
	}//output
	
	
	public static void main(String[] args) {
		Homework0408 hm=new Homework0408();
		try {
			hm.output("����");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//main

}//class
