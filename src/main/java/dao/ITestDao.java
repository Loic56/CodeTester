/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;

import jpa.Test;

/**
 *
 * @author Loïc
 */
public interface ITestDao extends IDAO {
    
    public Test create(Test test);

    public Test edit(Test test);

    public void destroy(Test test);

    public Test find(Long id);

    public List<Test> find(String format);
    
    public List<Test> find(String format, String theme);

    public List<Test> findAll();
    
    public Test update(Test test);
}
