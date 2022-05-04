//Anthony Franklin afranklin18@cnm.edu
//FranklinP5
//Database Program
//04/21/2022

package com.cis2235.franklin.franklinp5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    DBManager dbm;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDisplay;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnID;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnPopulate;

    @FXML
    private TextField txbDirector;

    @FXML
    private TextField txbGross;

    @FXML
    private TextField txbID;

    @FXML
    private TextArea txbMovies;

    @FXML
    private TextField txbTitle;

    @FXML
    private TextField txbYear;


    @FXML
    void onDisplay(ActionEvent event) {
        if(dbm != null)
        {
            int tempSize = 0;
            try{
                tempSize = dbm.getLastID();
            }catch (Exception ex){txbMovies.setText("[ERROR] You must populate a table before using it");}
            if(tempSize == 0) txbMovies.setText("[ERROR] You must populate a table before using it");
            else {
                int id = 0;
                try {
                    id = Integer.parseInt(txbID.getText());
                } catch (NumberFormatException ex) {
                }
                String results = "";
                if (id == 0 || id > dbm.getLastID()) {
                    for (int i = 1; i <= dbm.getLastID(); i++) {
                        if (i > 1) {
                            results += "\n" + i + " ";
                        } else results += i + " ";
                        for (String temp : dbm.getRecordById(i)) results += temp + "\t\t";
                    }
                } else
                {
                    results += id + " ";
                    for (String temp : dbm.getRecordById(id)) results += temp + "\t\t";
                }
                txbMovies.setText(results);
            }
        }
        else txbMovies.setText("[ERROR] You must create and populate a table before using it");
    }

    @FXML
    void createTable(ActionEvent event) {
        dbm = new DBManager();
        dbm.createTable();
    }

    @FXML
    void onDelete(ActionEvent event) {
        if(dbm != null)
        {
            if(!(dbm.getLastID() > 0)) txbMovies.setText("[ERROR] You must populate a table before using it");
            else {
                int id = 0;
                try {
                    id = Integer.parseInt(txbID.getText());
                } catch (NumberFormatException ex) {
                }
                if (id > 0 && id <= dbm.getLastID()) {
                    dbm.deleteRecord(id);
                    txbID.setText("");
                    onDisplay(event);
                } else txbMovies.setText("[ERROR] Enter a valid record ID");
            }
        }
        else txbMovies.setText("[ERROR] You must create and populate a table before using it");
    }

    @FXML
    void onEdit(ActionEvent event) {
        if(dbm != null)
        {
            if(!(dbm.getLastID() > 0)) txbMovies.setText("[ERROR] You must populate a table before using it");
            else {
                int id = 0;
                try {
                    id = Integer.parseInt(txbID.getText());
                } catch (NumberFormatException ex) {
                    txbMovies.setText("[ERROR] You must enter a number");
                }
                String results = "";
                if (id > 0 && id <= dbm.getLastID()) {
                    String title = "";
                    String director = "";
                    int year = 0;
                    Double gross = 0.00;
                    try {
                        title = txbTitle.getText();
                        director = txbDirector.getText();
                        year = Integer.parseInt(txbYear.getText());
                        gross = Double.parseDouble(txbGross.getText());
                    } catch (NumberFormatException ex) {
                        txbMovies.setText("[ERROR] All fields must be correctly populated");
                    }
                    if (title != "" && director != "" && year > 0 && gross > 0.00) {
                        dbm.editRecord(id, title, director, year, gross);
                        onDisplay(event);
                        txbID.setText("");
                        txbTitle.setText("");
                        txbDirector.setText("");
                        txbYear.setText("");
                        txbGross.setText("");
                    } else txbMovies.setText("[ERROR] All fields must be correctly populated before submitting edit");
                } else txbMovies.setText("[ERROR] Enter a valid record ID");
            }
        }else txbMovies.setText("[ERROR] You must create and populate a table before using it");
    }

    @FXML
    void onEnterID(ActionEvent event) {
        if(dbm != null)
        {
            if(!(dbm.getLastID() > 0)) txbMovies.setText("[ERROR] You must populate a table before using it");
            else {
                int id = 0;
                try {
                    id = Integer.parseInt(txbID.getText());
                } catch (NumberFormatException ex) {
                    txbMovies.setText("[ERROR] You must enter a number");
                }
                String results = "";
                if (id > 0 && id <= dbm.getLastID()) {
                    var temp = dbm.getRecordById(id);
                    ;
                    txbTitle.setText(temp[0]);
                    txbDirector.setText(temp[1]);
                    txbYear.setText(temp[2]);
                    txbGross.setText(temp[3]);
                } else txbMovies.setText("[ERROR] Enter a valid record ID");
            }

        }else txbMovies.setText("[ERROR] You must create and populate a table before using it");
    }

    @FXML
    void onInsert(ActionEvent event) {
        if(dbm != null)
        {
            if(!(dbm.getLastID() > 0)) txbMovies.setText("[ERROR] You must populate a table before using it");
            else {
                String title = "";
                String director = "";
                int id = 0;
                int year = 0;
                Double gross = 0.00;
                try {
                    id = Integer.parseInt(txbID.getText());
                    title = txbTitle.getText();
                    director = txbDirector.getText();
                    year = Integer.parseInt(txbYear.getText());
                    gross = Double.parseDouble(txbGross.getText());
                } catch (NumberFormatException ex) {
                    txbMovies.setText("[ERROR] All fields must be correctly populated!");
                }
                if (title != "" && director != "" && year > 0 && gross > 0.00) {
                    if (id > dbm.getLastID()) {
                        dbm.insert(id, title, director, year, gross);
                        onDisplay(event);
                        txbID.setText("");
                        txbTitle.setText("");
                        txbDirector.setText("");
                        txbYear.setText("");
                        txbGross.setText("");
                    } else
                        txbMovies.setText("[ERROR] This ID already exists or you are trying to create a record without an ID");
                }
            }
        }else txbMovies.setText("[ERROR] You must create and populate a table before using it");
    }

    @FXML
    void populateDatabase(ActionEvent event) {
        dbm.populateDatabase();
        onDisplay(event);
    }

}