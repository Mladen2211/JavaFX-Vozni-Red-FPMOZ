package Model;

import Database.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Company {
    private Integer id;
    private String name;
    private Integer hqId;
    private String headquarter;

    public Company(Integer id, String name, Integer hqId) {
        this.id = id;
        this.name = name;
        this.hqId = hqId;
    }

    public Company(Integer id, String name, String headquarter) {
        this.id = id;
        this.name = name;
        this.headquarter = headquarter;
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

    public Integer getHqId() {
        return hqId;
    }

    public void setHqId(Integer hqId) {
        this.hqId = hqId;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }


    public static Boolean add(String newCompany, Integer hq) {
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("INSERT INTO transport_companies VALUES (null,?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, newCompany);
            stmnt.setInt(2, hq );
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static ObservableList<Company> getCompanies() {
        ObservableList<Company> companies = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select c.id, c.name as name, ci.name as headquarter from transport_companies as c inner join cities as ci on c.city_id = ci.id");

            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                companies.add(new Company(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
            return companies;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return companies;
        }
    }
}
