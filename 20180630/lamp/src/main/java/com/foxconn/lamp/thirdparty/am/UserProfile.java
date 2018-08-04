package com.foxconn.lamp.thirdparty.am;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author liupingan
 *
 */
public class UserProfile
{

	private String account;
	private String role;
	private String custName;
	private String firstName;
	private String lastName;
	private String photoFileName;
	private String gender;
	private String email;
	private String mobilePhone;
	private String tel;
	private String address;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	private String locale;
	private String timezone;
	
	@JSONField(name="SourceName")
	private String sourceName;
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getRole()
	{
		return role;
	}
	public void setRole(String role)
	{
		this.role = role;
	}
	public String getCustName()
	{
		return custName;
	}
	public void setCustName(String custName)
	{
		this.custName = custName;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getPhotoFileName()
	{
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName)
	{
		this.photoFileName = photoFileName;
	}
	public String getGender()
	{
		return gender;
	}
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getMobilePhone()
	{
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getBirthYear()
	{
		return birthYear;
	}
	public void setBirthYear(String birthYear)
	{
		this.birthYear = birthYear;
	}
	public String getBirthMonth()
	{
		return birthMonth;
	}
	public void setBirthMonth(String birthMonth)
	{
		this.birthMonth = birthMonth;
	}
	public String getBirthDay()
	{
		return birthDay;
	}
	public void setBirthDay(String birthDay)
	{
		this.birthDay = birthDay;
	}
	public String getLocale()
	{
		return locale;
	}
	public void setLocale(String locale)
	{
		this.locale = locale;
	}
	public String getTimezone()
	{
		return timezone;
	}
	public void setTimezone(String timezone)
	{
		this.timezone = timezone;
	}
	public String getSourceName()
	{
		return sourceName;
	}
	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}
	
	
}
