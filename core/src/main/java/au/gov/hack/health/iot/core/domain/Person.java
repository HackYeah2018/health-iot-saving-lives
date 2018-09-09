package au.gov.hack.health.iot.core.domain;

import org.joda.time.LocalDate;

public class Person {

	public static final String CF = "f1";
	
	private String id;
	
	private String name;
	
	private String username;
	
	private String passwordHash;
	
	private String email;
	
	private LocalDate dateOfBirth;
	
	private Gender gender;
	
	private boolean admin;
	
	private String bedId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + ", admin=" + admin + "]";
	}
	
	
	
}
