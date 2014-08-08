/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import jpa.Proposition;
import jpa.Question;
import jpa.Test;

/**
 *
 * @author Loïc
 */
public interface IPropositionDao extends IDAO {

    public Proposition create(Proposition proposition);

    public Proposition edit(Proposition proposition);

    public void destroy(Proposition proposition);

    public Proposition find(Long id);
    
    public List<Proposition> find_(Question quest);
    
    public List<Proposition> find(Question question);

    public List<Proposition> findAll();

    public List<Proposition> find(Test test);
}
