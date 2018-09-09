package au.gov.hack.health.iot.core.domain;

public enum Gender {

	MALE("Male"),
	FEMALE("Female"),
	OTHER("Other");
	
	String longForm;
	
	public static Gender find(String potential) {
		if (potential == null) {
			return null;
		}
		for (Gender g : Gender.values()) {
			if (g.name().equalsIgnoreCase(potential)) {
				return g;
			}
		}
		return null;
	}
	
	private Gender(String longForm) {
		this.longForm = longForm;
	}

	public String getLongForm() {
		return longForm;
	}
	
	
}
