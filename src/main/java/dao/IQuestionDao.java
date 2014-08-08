/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import jpa.Question;
import jpa.Rubrique;
import jpa.Test;

/**
 *
 * @author Loïc
 */
public interface IQuestionDao extends IDAO {

    public Question create(Question question);

    public Question edit(Question question);

    public void destroy(Question question);

    public Question find(Long id);

    public List<Question> find(Rubrique rubrique);

    public List<Question> findAll();

    public List<Question> findByTest(Test test);

}
