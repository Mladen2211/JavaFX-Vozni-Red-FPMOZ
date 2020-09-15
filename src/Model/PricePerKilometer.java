package Model;

import Database.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PricePerKilometer {
    private Integer id;
    private String type;
    private Double price;


    public PricePerKilometer(Integer id, String type, Double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public PricePerKilometer() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public static Boolean add(String price, String type) {
        try{

            Double priceParse = Double.parseDouble(price);
            System.out.println(priceParse);
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("INSERT INTO prices_per_kilometers VALUES (null,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setDouble(2,priceParse);
            stmnt.setString(1, type);
            System.out.println(stmnt);
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public static ObservableList<PricePerKilometer> getPrices() {
        ObservableList<PricePerKilometer> prices = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select * from prices_per_kilometers");

            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                prices.add(new PricePerKilometer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3)
                ));
            }
            return prices;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return prices;
        }
    }

    public static Boolean update(Integer selPriceId,String selPriceType,String selPriceValue) {
        try{

            Double priceParse = Double.parseDouble(selPriceValue);

            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("UPDATE prices_per_kilometers SET type = ?, price = ?\n" +
                    "Where id = ?", Statement.RETURN_GENERATED_KEYS);
            stmnt.setDouble(2, priceParse);
            stmnt.setString(1, selPriceType);
            stmnt.setInt(3, selPriceId);
            System.out.println(stmnt+" ???");
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            return true;
        } catch (SQLException throwables) {
            System.out.println("nećemo tako");
            throwables.printStackTrace();
            return false;
        }
    }
}
