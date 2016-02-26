package com.guwan.formBean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

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

import com.guwan.domain.User;
import com.guwan.support.MaskFormat;
import com.guwan.type.GenderType;

public class UserFormBean {
	@NotEmpty
	@Size(min=6,max=20,message="validate.username.lengthError")
	private String username;

	@NotEmpty
	private String name;
	@NotEmpty
	@Pattern(regexp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"
			,message="validate.email.patternError")
	private String email;
	@NotEmpty
	@Size(min=6,max=20,message="validate.password.lengthError")
	private String password;
	
	private GenderType gender;
	 

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

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getFilledUser(){
		User user=new User();
		user.setUsername(username);
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setGender(gender.toString());
		user.setBirthDate(birthDate);
		return user;
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("properties username=").append(username).append(", ");
        sb.append("name=").append(name).append(", ");
        sb.append("email=").append(email).append(", ");
        sb.append("gender=").append(gender).append(", ");
        sb.append("birthDate=").append(birthDate).append(", ");
        sb.append("phone=").append(phone).append(";");
        return sb.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFormBean other = (UserFormBean) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static String getTimezone(int year, int month, int day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		Date date = calendar.getTime();
		TimeZone timezone = TimeZone.getDefault();
		boolean inDaylight = timezone.inDaylightTime(date);
		return timezone.getDisplayName(inDaylight, TimeZone.SHORT);
	}
}
