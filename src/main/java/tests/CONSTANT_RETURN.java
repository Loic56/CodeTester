package tests;

/**
 * 
 * @author Loïc
 */
public enum CONSTANT_RETURN {
	CANDIDAT_REDIRECT("candidat?faces-redirect=true"), TEST("test"), TEST_REDIRECT(
			"test?faces-redirect=true"), HELLO_CANDIDAT("helloCandidat"), LOG_ADMIN(
			"log?faces-redirect=true"), INDEX("index?faces-redirect=true"), INFO_RESA(
			"info_reservation?faces-redirect=true"), LOG_CANDIDAT(
			"log_candidat?faces-redirect=true");

	private String retour;

	
	
	private CONSTANT_RETURN(String s) {
		retour = s;
	}

	public String getReturn() {
		return retour;
	}
	
}