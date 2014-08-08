/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import jpa.Categorie;

/**
 *
 * @author Loïc
 */
public interface ICategorieDao extends IDAO {

    public Categorie create(Categorie categorie);

    public Categorie edit(Categorie categorie);

    public void destroy(Categorie categorie);

    public Categorie find(Long id);

    public List<Categorie> findAll();
}
