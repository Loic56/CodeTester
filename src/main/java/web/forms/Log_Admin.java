/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import dao.IAdminDao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.Admin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "admin")
@SessionScoped
public class Log_Admin implements Serializable {

    private String login;
    private String mp;
    private boolean rememberMe;
    private String remember1 = "hi";
    private String error;

    private IAdminDao adminDao = null;

    public Log_Admin() {
        error = "0";
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.adminDao = (IAdminDao) ctx.getBean("adminDao");
        checkCookie();

        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("info = " + fc.getExternalContext().getRequestParameterMap().get("testid"));

    }

    public void checkCookie() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String cookieName = null;
        Cookie cookie[] = ((HttpServletRequest) facesContext.getExternalContext().
                getRequest())
                .getCookies();

        if (cookie != null && cookie.length > 0) {
            System.out.println("des cookies existent");
            for (int i = 0; i < cookie.length; i++) {
                cookieName = cookie[i].getName();
                if (cookieName.equals("user")) {
                    System.out.println("user name");
                    login = cookie[i].getValue();
                } else if (cookieName.equals("pwd")) {
                    System.out.println("password");
                    mp = cookie[i].getValue();
                } else if (cookieName.equals("btremember")) {
                    System.out.println("remendert");
                    remember1 = cookie[i].getValue();
                    if (remember1.equals("false")) {
                        rememberMe = false;
                    } else if (remember1.equals("true")) {
                        rememberMe = true;
                    }
                }
            }
        } else {
            System.out.println("Cannot find any cookie");
        }
    }

    public String Link() {
        System.out.println("Link() ");

        List<Admin> list = adminDao.findAll();
        for (Admin ad : list) {
            System.out.println("admin : " + ad.toString());
            if (ad.getAdminlogin().equals(getLogin()) && ad.getAdminpassword().equals(getMp())) {
                System.out.println(" ok ");

                FacesContext facesContext = FacesContext.getCurrentInstance();
                Cookie btuser = new Cookie("user", getLogin());
                Cookie btpasswd = new Cookie("pwd", getMp());

                if (rememberMe == false) {
                    remember1 = "false";
                } else {
                    remember1 = "true";
                }

                Cookie btremember = new Cookie("btremember", remember1);
                btuser.setMaxAge(3600);
                btpasswd.setMaxAge(3600);
                ((HttpServletResponse) facesContext.getExternalContext().getResponse()).
                        addCookie(btuser);
                ((HttpServletResponse) facesContext.getExternalContext().getResponse()).
                        addCookie(btpasswd);
                ((HttpServletResponse) facesContext.getExternalContext().getResponse()).
                        addCookie(btremember);

                return "candidat?faces-redirect=true";
            }
        }
        setError("1");
        return "log?faces-redirect=true";
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
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
     * @param mp the mp to set
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
     * @param rememberMe the rememberMe to set
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
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

}
