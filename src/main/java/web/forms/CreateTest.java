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
import tools.Document;

/**
 *
 * @author Loïc
 */
@ManagedBean(name = "create")
@SessionScoped
public class CreateTest implements Serializable {

    // page1
    private String type;
    private String categorie;
    private String theme;
    private String duree;
    private String niveau;
    private String description;

    //page2
    private String nomRubrique;

    // 
    private TreeNode root;
    private Document selectedDocument;

    public CreateTest() {

        root = new DefaultTreeNode("root", null);

        TreeNode documents = new DefaultTreeNode(new Document("Documents", "-", "Folder"), getRoot());
        TreeNode pictures = new DefaultTreeNode(new Document("Pictures", "-", "Folder"), getRoot());
        TreeNode music = new DefaultTreeNode(new Document("Music", "-", "Folder"), getRoot());
        TreeNode work = new DefaultTreeNode(new Document("Work", "-", "Folder"), documents);
        TreeNode primefaces = new DefaultTreeNode(new Document("PrimeFaces", "-", "Folder"), documents);

        //Documents  
        TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
        TreeNode resume = new DefaultTreeNode("document", new Document("Resume.doc", "10 KB", "Word Document"), work);
        TreeNode refdoc = new DefaultTreeNode("document", new Document("RefDoc.pages", "40 KB", "Pages Document"), primefaces);

        //Pictures  
        TreeNode barca = new DefaultTreeNode("picture", new Document("barcelona.jpg", "30 KB", "JPEG Image"), pictures);
        TreeNode primelogo = new DefaultTreeNode("picture", new Document("logo.jpg", "45 KB", "JPEG Image"), pictures);
        TreeNode optimus = new DefaultTreeNode("picture", new Document("optimusprime.png", "96 KB", "PNG Image"), pictures);

        //Music  
        TreeNode turkish = new DefaultTreeNode(new Document("Turkish", "-", "Folder"), music);

        TreeNode cemKaraca = new DefaultTreeNode(new Document("Cem Karaca", "-", "Folder"), turkish);
        TreeNode erkinKoray = new DefaultTreeNode(new Document("Erkin Koray", "-", "Folder"), turkish);
        TreeNode mogollar = new DefaultTreeNode(new Document("Mogollar", "-", "Folder"), turkish);

        TreeNode nemalacak = new DefaultTreeNode("mp3", new Document("Nem Alacak Felek Benim", "1500 KB", "Audio File"), cemKaraca);
        TreeNode resimdeki = new DefaultTreeNode("mp3", new Document("Resimdeki Gozyaslari", "2400 KB", "Audio File"), cemKaraca);

        TreeNode copculer = new DefaultTreeNode("mp3", new Document("Copculer", "2351 KB", "Audio File"), erkinKoray);
        TreeNode oylebirgecer = new DefaultTreeNode("mp3", new Document("Oyle bir Gecer", "1794 KB", "Audio File"), erkinKoray);

        TreeNode toprakana = new DefaultTreeNode("mp3", new Document("Toprak Ana", "1536 KB", "Audio File"), mogollar);
        TreeNode bisiyapmali = new DefaultTreeNode("mp3", new Document("Bisi Yapmali", "2730 KB", "Audio File"), mogollar);
    }

    public String suivant() {
        System.out.println("suivant");
        return "createTest_2?faces-redirect=true";
    }

    public String creerRubrique() {
        System.out.println("creerRubrique");
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

    /**
     * @return the nomRubrique
     */
    public String getNomRubrique() {
        return nomRubrique;
    }

    /**
     * @param nomRubrique the nomRubrique to set
     */
    public void setNomRubrique(String nomRubrique) {
        this.nomRubrique = nomRubrique;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedDocument
     */
    public Document getSelectedDocument() {
        return selectedDocument;
    }

    /**
     * @param selectedDocument the selectedDocument to set
     */
    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}
