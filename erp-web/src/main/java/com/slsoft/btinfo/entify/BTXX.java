package com.slsoft.btinfo.entify;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.PersistableEntity;
@Entity
@Table(name = "cms_bt_btxx")
@MetaData(value = "补贴信息")
public class BTXX extends PersistableEntity<String>{
	
	private String id;
	
	private String name;//姓名
	
	private String departid;//机构代码
	private String project;//身份证号
	private String cardcode;
	private String nation;//民族
	private String hukou;//户口性质
	private String address;//住址
	private String phone;//电话
	private String postcode;//邮编
	private String bank; //银行
	private String bankaccount; //银行账户
	private String year;//时间
	private String minimumcode;//低保号码
	private String batch;//导入批次标识
	private double money;//金额
	private double population;//保障人口
	private double summoney;//累计金额
	private String remark;//
	
	private double field1;
	private double field2;
	private double field3;
	private double field4;
	
	private String field15;
	private String field16;
	private String field17;
	private String field18;
	private String field19;
	
	@Id
	@Column(length = 32)
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getCardcode() {
		return cardcode;
	}
	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getHukou() {
		return hukou;
	}
	public void setHukou(String hukou) {
		this.hukou = hukou;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankaccount() {
		return bankaccount;
	}
	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMinimumcode() {
		return minimumcode;
	}
	public void setMinimumcode(String minimumcode) {
		this.minimumcode = minimumcode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getPopulation() {
		return population;
	}
	public void setPopulation(double population) {
		this.population = population;
	}
	public double getSummoney() {
		return summoney;
	}
	public void setSummoney(double summoney) {
		this.summoney = summoney;
	}
	public double getField1() {
		return field1;
	}
	public void setField1(double field1) {
		this.field1 = field1;
	}
	public double getField2() {
		return field2;
	}
	public void setField2(double field2) {
		this.field2 = field2;
	}
	public double getField3() {
		return field3;
	}
	public void setField3(double field3) {
		this.field3 = field3;
	}
	public double getField4() {
		return field4;
	}
	public void setField4(double field4) {
		this.field4 = field4;
	}
	public String getField17() {
		return field17;
	}
	public void setField17(String field17) {
		this.field17 = field17;
	}
	public String getField18() {
		return field18;
	}
	public void setField18(String field18) {
		this.field18 = field18;
	}
	public String getField19() {
		return field19;
	}
	public void setField19(String field19) {
		this.field19 = field19;
	}
	
	public String getField15() {
		return field15;
	}
	public void setField15(String field15) {
		this.field15 = field15;
	}
	public String getField16() {
		return field16;
	}
	public void setField16(String field16) {
		this.field16 = field16;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
