package au.gov.hack.health.iot.core.config;

public class KerberosProperties {

	private String principal;
	
	private String keytab;
	
	private boolean enabled;
	
	private String hbasePrincipal;
	
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getKeytab() {
		return keytab;
	}

	public void setKeytab(String keytab) {
		this.keytab = keytab;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void validate() {
		// TODO Auto-generated method stub
		
	}

	public String getHbasePrincipal() {
		return hbasePrincipal;
	}

	public void setHbasePrincipal(String hbasePrincipal) {
		this.hbasePrincipal = hbasePrincipal;
	}
	
	
	
}
