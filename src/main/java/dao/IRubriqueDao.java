/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import jpa.Rubrique;
import jpa.Test;

/**
 *
 * @author Loïc
 */
public interface IRubriqueDao extends IDAO {

    public Rubrique create(Rubrique rubrique);

    public Rubrique edit(Rubrique rubrique);

    public void destroy(Rubrique rubrique);

    public Rubrique find(Long id);

    public List<Rubrique> findAll();

    public List<Rubrique> find(Test test);

}
