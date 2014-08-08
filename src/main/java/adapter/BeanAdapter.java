/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adapter;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * 
 * @author Loïc
 */
public class BeanAdapter implements Serializable {
	
	protected String RETOUR;
	public DaoProvider PROVIDER;
    
	public BeanAdapter() {
		super();
	
        
		PROVIDER = new DaoProvider();
		
	}

}
