package Model;

import Controller.AdminController;
import Database.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.desktop.SystemSleepEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class City {
    private Integer id;
    private String name;

    public City() {
    }

    public City(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Boolean add(String newCity) {
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("INSERT INTO cities VALUES (null,?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1,newCity);
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static ObservableList<City> getCities() {
        ObservableList<City> cities = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select * from cities");

            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                cities.add(new City(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
            return cities;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return cities;
        }
    }
}
