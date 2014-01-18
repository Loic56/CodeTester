/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import jpa.Jointure;

/**
 *
 * @author Loïc
 */
public interface IJointureDao {

    public Jointure create(Jointure lointure);

    public List<Jointure> findAll();
    
    public void destroy(Jointure jointure);
}
