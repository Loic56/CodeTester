/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import jpa.Admin;
import tools.CONSTANT_RETURN;
import tools.Utils;
import adapter.BeanAdapter;

/**
 * 
 * @author Loïc
 */
@ManagedBean(name = "admin")
@SessionScoped
public class Log_Admin extends BeanAdapter {

	private String login;
	private String mp;
	private boolean rememberMe;
	private String remember1 = "hi";
	private String error;

	public Log_Admin() {
		super();
		setError("0");
		// Utils.checkCookies(login, mp, remember1, rememberMe);
		// System.out.println("info = " + getFromSession("info"));
	}

	public String Link() {
		System.out.println("Link() ");
		

		Utils.Log(PROVIDER.ADMINDAO.getConnexionCount());
		
		List<Admin> list = PROVIDER.ADMINDAO.findAll();
		for (Admin ad : list) {
			System.out.println("admin : " + ad.toString());

			// si des valeurs ont été entrées
			if (getLogin() != null && !getLogin().equals("") && getMp() != null
					&& !getMp().equals("")) {

				// si les valeurs match
				if (ad.getAdminlogin().equals(getLogin())
						&& ad.getAdminpassword().equals(getMp())) {
					System.out.println(" match fuck ");

					if (!rememberMe) {
						remember1 = "false"; // inverse non ?
					} else {
						remember1 = "true";
					}

					// Utils.setCookies(getLogin(), getMp(), remember1);
					System.out.println(" ok 1 ");
					RETOUR = CONSTANT_RETURN.CANDIDAT_REDIRECT.getReturn();
				}

			} else {
				System.out.println(" ok 2");
				RETOUR = CONSTANT_RETURN.LOG_ADMIN.getReturn();
				System.out.println(" ok 3");
				setError("1");
			}
		}

		return RETOUR;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the mp
	 */
	public String getMp() {
		return mp;
	}

	/**
	 * @param mp
	 *            the mp to set
	 */
	public void setMp(String mp) {
		this.mp = mp;
	}

	/**
	 * @return the rememberMe
	 */
	public boolean isRememberMe() {
		return rememberMe;
	}

	/**
	 * @param rememberMe
	 *            the rememberMe to set
	 */
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
