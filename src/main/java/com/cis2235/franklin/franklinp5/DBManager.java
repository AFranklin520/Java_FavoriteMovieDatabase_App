/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cis2235.franklin.franklinp5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

/**
 *
 * @author inelson1
 */
public class DBManager {
    
    private Connection connection = null;
    private Statement statement = null;
    private final String TABLE_NAME = "FranklinFavMovies";
    private Result result = null;
    
    //Constructor established the connection to the database
    public DBManager(){
       
        try{
            // Establish a connection
            //Set the database name here.  Default is sample
            connection = DriverManager.getConnection("jdbc:sqlite:FranklinP5.db");

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);

        }
        
    }
    
    public void insert(int recordID, String jtitle, String jdirector, int jyear, Double jgross){
        try {
            //Insert a new record into the database
            String insertQuery =
                    "INSERT INTO "+TABLE_NAME +
                    " VALUES (?, ?, ? , ?, ?)";
            
           PreparedStatement  insertUpdate = null;
           insertUpdate = connection.prepareStatement(insertQuery);
           
           insertUpdate.setInt(1, recordID);
           insertUpdate.setString(2, jtitle);
           insertUpdate.setString(3, jdirector);
           insertUpdate.setInt(4, jyear);
           insertUpdate.setDouble(5, jgross);
           insertUpdate.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public int getLastID() {
        int ID = 0;
        ResultSet results;
        try {
            statement = connection.createStatement();

            String newQuery = "SELECT MAX(ID)"
                    + "FROM " + TABLE_NAME;
            results = statement.executeQuery(newQuery);

            if (results.next()) {
                ID = Integer.parseInt(results.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ID;
    }
    
    public String[] getRecordById(int recordID){
        
            String jtitle = "";
            String jdirector= "";
            String strjyear = "";
            String strjgross = "";
         
            try {
             PreparedStatement getRecordStmt = null;
               
     
                //Display the contents of the record
                
                String getRecordQuery = "SELECT * "
                        +"FROM "+TABLE_NAME
                        +" WHERE ID = ?";//+ recordID;
                getRecordStmt = connection.prepareStatement(getRecordQuery);
                getRecordStmt.setInt(1, recordID);    
                                                
                ResultSet result = getRecordStmt.executeQuery();
                
                if(result.next())
                {
                    jtitle = result.getString(2);
                    jdirector = result.getString(3);
                    strjyear = result.getString(4);
                    strjgross = result.getString(5);
                }               


        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
         String[] getRow = {jtitle, jdirector, strjyear, strjgross };
            return getRow;
    }
    
    public void editRecord(int recordID, String jtitle, String jdirector, int jyear, Double jgross){
        try {
            PreparedStatement  editStatement = null;
            //update the record
            String editQuery = "UPDATE " + TABLE_NAME
                    + " SET TITLE = ?, DIRECTOR = ?, YEAR = ?, GROSS = ? WHERE ID = ?";
             editStatement = connection.prepareStatement(editQuery);
             
             editStatement.setString(1, jtitle);
             editStatement.setString(2, jdirector);
             editStatement.setInt(3, jyear);
             editStatement.setDouble(4, jgross);
             editStatement.setInt(5, recordID);
             editStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteRecord(int  recordID) {
        
        try {
           PreparedStatement deleteStmt = null; 
            //Delete the record.
            String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
            deleteStmt = connection.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, recordID);
            deleteStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Classes for creating the table and putting values in it.
    
          //Create a table
    public void createTable(){

          //  statement.executeUpdate
            String sql = "CREATE TABLE IF NOT EXISTS FranklinFavMovies ( \n"
                      + "   id integer PRIMARY KEY, \n"
                      + "   title text NOT NULL, \n"
                      + "   director text NOT NULL, \n"
                      + "   year integer NOT NULL, \n"
                      + "   gross double Not Null\n"
                      + ");";
            //Using SQL:
//                    + "(ID NUMBER(3) NOT NULL PRIMARY KEY, "
//                        + "NAME Varchar2(30) NOT NULL, "
//                        + "BREED Varchar2(30) NOT NULL , "
//                        + "AGE NUMBER(2) NOT NULL , "
//                        + "COLOR Varchar2(30) NOT NULL )");
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void populateDatabase(){

        insert(1, "The Usual Suspects", "Bryan Singer", 1995, 34449356.00);
        insert(2, "Ted", "Seth MacFarlane", 2012, 549400000.00);
        insert (3, "Goodfellas", "Martin Scorsese", 1990, 47100000.00);
        insert (4, "Casino", "Martin Scorsese", 1995, 116100000.00);
        insert (5, "Pulp Fiction", "Quentin Tarantino", 1994, 213900000.00);

    }
        
        public void dropTable(){

        try {
            statement = connection.createStatement();
            
            //Be sure to change the name of the table
            String drop = "Drop Table " +TABLE_NAME+ " " ;
            statement.execute(drop);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }    
          
     }
    
}
