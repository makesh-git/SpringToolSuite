package com.model.admin;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.model.common.Batches;
import com.model.common.Groups;

@Entity
public class Users
{
	@Id
	private String userid;
	private String name;
	private String age;
	private String email;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="batch_name")  
	private Batches batch;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="group_name")  
	private Groups group;


	private String gender;
	private String mobile;

	private String address;
	private String country;
	private String state;
	private String city;
	private String pincode;
	private String height;
	private String weight;
	private String bmi;
	private String reason;
	private String medRes;
	private String dietRes;
	private String food;
	private String preg;
	private String ref;
	private String regDate;
	private String refId;
	private String pass;
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(String userid, String name, String age, String email, Batches batch, Groups group, String gender,
			String mobile, String address, String country, String state, String city, String pincode, String height,
			String weight, String bmi, String reason, String medRes, String dietRes, String food, String preg,
			String ref, String regDate, String refId, String pass) {
		super();
		this.userid = userid;
		this.name = name;
		this.age = age;
		this.email = email;
		this.batch = batch;
		this.group = group;
		this.gender = gender;
		this.mobile = mobile;
		this.address = address;
		this.country = country;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.height = height;
		this.weight = weight;
		this.bmi = bmi;
		this.reason = reason;
		this.medRes = medRes;
		this.dietRes = dietRes;
		this.food = food;
		this.preg = preg;
		this.ref = ref;
		this.regDate = regDate;
		this.refId = refId;
		this.pass = pass;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Batches getBatch() {
		return batch;
	}
	public void setBatch(Batches batch) {
		this.batch = batch;
	}
	public Groups getGroup() {
		return group;
	}
	public void setGroup(Groups group) {
		this.group = group;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBmi() {
		return bmi;
	}
	public void setBmi(String bmi) {
		this.bmi = bmi;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMedRes() {
		return medRes;
	}
	public void setMedRes(String medRes) {
		this.medRes = medRes;
	}
	public String getDietRes() {
		return dietRes;
	}
	public void setDietRes(String dietRes) {
		this.dietRes = dietRes;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getPreg() {
		return preg;
	}
	public void setPreg(String preg) {
		this.preg = preg;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	@Override
	public String toString() {
		return "Users [userid=" + userid + ", name=" + name + ", age=" + age + ", email=" + email + ", batch=" + batch
				+ ", group=" + group + ", gender=" + gender + ", mobile=" + mobile + ", address=" + address
				+ ", country=" + country + ", state=" + state + ", city=" + city + ", pincode=" + pincode + ", height="
				+ height + ", weight=" + weight + ", bmi=" + bmi + ", reason=" + reason + ", medRes=" + medRes
				+ ", dietRes=" + dietRes + ", food=" + food + ", preg=" + preg + ", ref=" + ref + ", regDate=" + regDate
				+ ", refId=" + refId + ", pass=" + pass + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
