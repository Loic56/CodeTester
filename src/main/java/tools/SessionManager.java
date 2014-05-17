/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import web.bean.DaoProvider;

/**
 *
 * @author Loïc
 */
public class SessionManager extends DaoProvider {

    private  static FacesContext context;
    private static ExternalContext externalContext;
    protected  static Map<String, Object> SharedData;

    /**
     * @return the externalContext
     */
    public static ExternalContext getExternalContext() {
        return externalContext;
    }
    
    
    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        externalContext = getContext().getExternalContext();
        SharedData = sharedData();
        
    }

    public void deconnectFromSession() {
        getExternalContext().invalidateSession();
    }

    
    public static Map<String, Object> sharedData(){
         return getExternalContext().getSessionMap();
    }
    

    public String getFromSession(String param) {
        return getExternalContext().getRequestParameterMap().get(param);
    }

    /**
     * @return the context
     */
    public static FacesContext getContext() {
        return context;
    }
}
