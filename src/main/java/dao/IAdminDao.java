/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;

import jpa.Admin;

/**
 *
 * @author Loïc
 */
public interface IAdminDao extends IDAO {

  public Admin create(Admin admin); 
  public Admin edit(Admin admin); 
  public void destroy(Admin admin); 
  public Admin find(Long id); 
 
  public Admin find(String login, String password); 
  
  
  public List<Admin> findAll();

}
