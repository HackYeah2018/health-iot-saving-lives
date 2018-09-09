package au.gov.hack.health.iot.core.domain;

public class Admittance {

	private String admittanceId;
	
	private String patientId;
	
	private String hospitalId;

	public String getAdmittanceId() {
		return admittanceId;
	}

	public void setAdmittanceId(String admittanceId) {
		this.admittanceId = admittanceId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	
	
}
