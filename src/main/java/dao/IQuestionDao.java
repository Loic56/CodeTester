/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import jpa.Question;
import jpa.Rubrique;


/**
 *
 * @author Loïc
 */
public interface IQuestionDao {

    public Question create(Question question);

    public Question edit(Question question);

    public void destroy(Question question);

    public Question find(Long id);

    public List<Question> find(Rubrique rubrique);

    public List<Question> findAll();

}
