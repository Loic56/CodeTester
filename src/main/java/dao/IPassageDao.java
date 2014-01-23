/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import jpa.Candidat;
import jpa.Passage;

/**
 *
 * @author Loïc
 */
public interface IPassageDao {

    public Passage create(Passage passage);

    public Passage update(Passage passage);

    public void destroy(Passage passage);

    public Passage find(Long id);
    
    public Passage find(Candidat candidat);

    public List<Passage> find(String format);

    public List<Passage> find(String format, String theme);

    public List<Passage> findAll();
}
