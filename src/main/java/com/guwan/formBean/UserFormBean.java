package com.guwan.formBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat.Style;

import com.guwan.support.MaskFormat;
import com.guwan.type.GenderType;

public class UserFormBean {

	@Size(min=6,max=20,message="validate.username.lengthError")
	private String username;

	@NotEmpty
	private String name;
	
	@Pattern(regexp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"
			,message="validate.email.patternError")
	private String email;
	
	@Size(min=6,max=20,message="validate.password.lengthError")
	private String password;
	
	private GenderType gender;
	
	@Min(21)
	private int age;

	@DateTimeFormat(iso=ISO.DATE)
	@Past
	private Date birthDate;

	@Pattern(regexp = "^((\\+86)|(86))?(1)\\d{10}$")
	private String phone;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}


	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("properties name=");
        sb.append("username=").append(username).append(", ");
        sb.append("name=").append(name).append(", ");
        sb.append("email=").append(email).append(", ");
        if (name != null) {
        	sb.append("'").append(name).append("', ");
        } else {
        	sb.append(name).append(", ");
        }
        sb.append("age=").append(age).append(", ");
        sb.append("birthDate=").append(birthDate).append(", ");
        sb.append("phone=");
        return sb.toString();
    }
}
