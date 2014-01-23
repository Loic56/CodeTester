/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import jpa.Candidat;

/**
 *
 * @author Loïc
 */
public interface ICandidatDao {

    public Candidat create(Candidat candidat);

    public Candidat edit(Candidat candidat);

    public void destroy(Candidat candidat);

    public Candidat find(Long id);
    
     public Candidat find(String nom, String prenom, Date date);

    public Candidat find(String login, String password);

    public List<Candidat> findAll();

}
