/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Loïc
 */
@SuppressWarnings("serial")
public class PamException extends RuntimeException {

    // code d'erreur 
    private int code;

    public PamException(int code) {
        super();
        System.out.println("\n \n   PAM EXCEPTION   \n \n ");
        this.code = code;
    }

    public PamException(String message, int code) {
        super(message);
        
        System.out.println(" ======================================== \n= "+ message+"\n=   PAM EXCEPTION   \n ======================================== \n ");
        this.code = code;    
    }

    public PamException(Throwable cause, int code) {
        super(cause);
        System.out.println("\n \n   PAM EXCEPTION   \n \n ");
        this.code = code;
    }

    public PamException(String message, Throwable cause, int code) {
        super(message, cause);
        System.out.println("\n \n   PAM EXCEPTION   \n \n ");
        this.code = code;
    }

    // getter et setter 
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
