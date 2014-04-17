package fr.netapsys.devoxx.repository;

public class AccountInformation {
	
	private String lastname = "";
	private String firstname = ""; 
	private String email = ""; 
	private String login = "";

	public AccountInformation() {
		
	}
	
	public boolean isValid() {
		return !login.isEmpty();
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
	
	
}
