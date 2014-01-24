/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.forms;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.examples.domain.Document;  



/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create")
@SessionScoped
public class CreateTest implements Serializable {

    private String type;
    private String categorie;
    private String theme;
    private String duree;
    private String niveau;
    private String description;
    
    
    // 
    private TreeNode root; 
    
    
    public CreateTest() {
        
        
         root = new DefaultTreeNode("root", null);  
          
        TreeNode documents = new DefaultTreeNode(new Document("Documents", "-", "Folder"), root);  
        TreeNode pictures = new DefaultTreeNode(new Document("Pictures", "-", "Folder"), root);  
        TreeNode music = new DefaultTreeNode(new Document("Music", "-", "Folder"), root); 
        
        
        
        
        
        
        
        
        
        
        
    }
    
    public String suivant(){
        System.out.println("suivant");
         return "createTest_2?faces-redirect=true";
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return the duree
     */
    public String getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(String duree) {
        this.duree = duree;
    }

    /**
     * @return the niveau
     */
    public String getNiveau() {
        return niveau;
    }

    /**
     * @param niveau the niveau to set
     */
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    /**
     * @return the decsription
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param decsription the decsription to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
