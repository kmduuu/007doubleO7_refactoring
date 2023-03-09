package com.model2.mvc.service.domain;

import java.sql.Date;

//==> 상품정보를 모델링(추상화/캡슐화)한 Bean
public class Product {
	
	//field
	private int prodNo;
	private String prodDetail;
	private String prodName;
	private String fileName;
	private Date regDate;
	private int price;
	private String proTranCode;
	private String manuDate; // 등록일자
	
	//constructor
	public Product() {
	}
	
	//method
	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getProdDetail() {
		return prodDetail;
	}

	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getRegDate() {
		
		return regDate;
	}

	public void setRegDate(Date date) {
		this.regDate = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getManuDate() {
		
		System.out.println("대체 왜 시발ㄹㄹㄹ");
		String[] manuArr = manuDate.split("[-]");
		String resultManuDate="";
		for(String str : manuArr) {
			resultManuDate += str;
		}
		System.out.println(manuDate);
		return resultManuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public String getProTranCode() {
		return proTranCode;
	}

	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}
	
	
	public String toString() {
		return "Product : [prodNo] "+prodNo+" [prodName] "+prodName+" [prodDetail] "+prodDetail
			+" [price] "+price+" [fileName] "+fileName+" [regDate] "+regDate+ "[manuDate]" +manuDate;
	}// 값이 잘 초기화 되었는지 확인 가능, 오버라이딩 필수
	
}
