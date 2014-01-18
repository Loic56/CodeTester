/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.util.List;
import jpa.Passage;
import jpa.Proposition;
import jpa.Question;
import jpa.Reponse;
import jpa.Test;


/**
 *
 * @author Loïc
 */
public interface IReponseDao {

    public Reponse create(Reponse reponse);

    public Reponse edit(Reponse reponse);

    public void destroy(Reponse reponse);

    public Reponse find(Long id);
    
    public Reponse find(Question question);

    public List<Reponse> findAll();

    public List<Reponse> find(Passage passage);
    
    public List<Reponse> find(Passage passage, Test test);
    
//    public void makeTransient(Reponse entity) ;

    public void detach(Object object);
    
}
