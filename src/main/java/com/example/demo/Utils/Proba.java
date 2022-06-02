package com.example.demo.Utils;

import java.io.Serializable;
import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class Proba implements Serializable {
    private int id;
    private String nume;
    private int min;
    private int max;
    private Vector<String> participanti;

    public void loadElems(){
        String url = "jdbc:sqlite:/C:\\sqlLite\\mpp.db";
        try{
            Connection connection = DriverManager.getConnection(url);
            String sql = "SELECT * FROM participantProba WHERE nume_proba = ? ;";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1,this.getNume());
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                this.participanti.add(rs.getString("nume_participant"));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public Proba(int _id,String _nume,int _min,int _max){
        this.id = _id;
        this.nume = _nume;
        this.min = _min;
        this.max = _max;
        this.participanti = new Vector<String>();
        this.loadElems();
    }

    public Proba(int _id,String _nume,int _min,int _max, Vector<String> _participanti){
        this.id = _id;
        this.nume = _nume;
        this.min = _min;
        this.max = _max;
        this.participanti = _participanti;
    }

    public int getId(){return this.id;}
    public String getNume(){return this.nume;}
    public int getMin(){return this.min;}
    public int getMax(){return this.max;}
    public Vector<String> getParticipanti(){return this.participanti;}

    public void setId(int new_elem){this.id = new_elem;}
    public void setNume(String new_elem){this.nume = new_elem;}
    public void setMax(int new_elem){this.max = new_elem;}
    public void setMin(int new_elem){this.min = new_elem;}
    public void setParticipanti(Vector<String> new_elem){this.participanti = new_elem;}

    public int getParticipant(String nume_participant){
        for(int i=0;i<this.participanti.size();i++){
            if(Objects.equals(this.participanti.get(i), nume_participant))
                return i;
        }
        return -1;
    }

    public void addParticipant(String nume_participant){
        if(this.getParticipant(nume_participant) == -1)
            this.participanti.add(nume_participant);
    }
    public void removeParticipant(String nume_participant){
        int index = this.getParticipant(nume_participant);
        if(index != -1)
            this.participanti.remove(index);
    }

}
