package day0408;

import java.sql.Date;

public class Homework0408VO {
	
private String maker,model,option;
private int price;
private Date hiredate;

public Homework0408VO() {
}

public Homework0408VO(String maker, String model, String option, int price, Date hiredate) {
	this.maker = maker;
	this.model = model;
	this.option = option;
	this.price = price;
	this.hiredate = hiredate;
}

public String getMaker() {
	return maker;
}

public void setMaker(String maker) {
	this.maker = maker;
}

public String getModel() {
	return model;
}

public void setModel(String model) {
	this.model = model;
}

public String getOption() {
	return option;
}

public void setOption(String option) {
	this.option = option;
}

public int getPrice() {
	return price;
}

public void setPrice(int price) {
	this.price = price;
}

public Date getHiredate() {
	return hiredate;
}

public void setHiredate(Date hiredate) {
	this.hiredate = hiredate;
}








}
