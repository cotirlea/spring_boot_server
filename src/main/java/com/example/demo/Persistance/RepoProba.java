package com.example.demo.Persistance;



import com.example.demo.Utils.Proba;

import java.io.Serializable;
import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class RepoProba implements Repo<Proba>, Serializable {
    private Vector<Proba> elems;

    public void setConnection(){
        String url = "jdbc:sqlite:/C:\\sqlLite\\mpp.db";
        try{
            Connection connection = DriverManager.getConnection(url);
            String sql = "SELECT * FROM proba;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                int min = resultSet.getInt("min");
                int max = resultSet.getInt("max");

                Proba p = new Proba(id,nume,min, max);
                this.elems.add(p);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public RepoProba(){
        this.elems = new Vector<Proba>();
        setConnection();
    }

    @Override
    public void add(Proba elem) {
        this.elems.add(elem);
    }

    @Override
    public int get(String name) {
        for(int i=0;i<this.elems.size();i++){
            if(Objects.equals(this.elems.get(i).getNume(), name))
                return i;
        }
        return -1;
    }

    @Override
    public void remove(String name) {
        int index = this.get(name);
        if(index != -1)
            this.elems.remove(index);
    }

    @Override
    public Vector<Proba> getData() {
        return this.elems;
    }


    public void addSQL(String nume_proba,String nume_participant){
        String url = "jdbc:sqlite:/C:\\sqlLite\\mpp.db";
        try{
            Connection connection = DriverManager.getConnection(url);
            String sql = "INSERT INTO participantProba (nume_participant, nume_proba) VALUES (?, ?);";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1,nume_participant);
            s.setString(2,nume_proba);
            s.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeSQL(String nume_proba,String nume_participant){
        String url = "jdbc:sqlite:/C:\\sqlLite\\mpp.db";
        try{
            Connection connection = DriverManager.getConnection(url);
            String sql = "DELETE FROM participantProba WHERE nume_participant = ? AND nume_proba = ?;";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1,nume_participant);
            s.setString(2,nume_proba);
            s.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addParticipant(String nume_proba,String nume_participant){
        int index = this.get(nume_proba);
        if(index != -1) {
            this.addSQL(nume_proba,nume_participant);
            this.elems.get(index).addParticipant(nume_participant);
        }
    }

    public void removeParticipant(String nume_proba,String nume_participant){
        int index = this.get(nume_proba);
        if(index != -1) {
            this.removeSQL(nume_proba,nume_participant);
            this.elems.get(index).removeParticipant(nume_participant);
        }
    }

    public int findPart(String nume, int index){
        return this.elems.get(index).getParticipant(nume);
    }
}
