package tools;

import javax.faces.context.FacesContext;

public class FacesUtil {

	
	 // Getters -----------------------------------------------------------------------------------

    public static Object getApplicationMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
    }

    // Setters -----------------------------------------------------------------------------------

    public static void setApplicationMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
    }

	public static void invalidateSession() {
		// TODO Auto-generated method stub
		 FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public static void remove(String string) {
		// TODO Auto-generated method stub
		 FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().remove(string);
	}
    
}
