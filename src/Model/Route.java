package Model;

import Database.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Route {
    private Integer id;
    private Integer departureId;
    private String departureCity;
    private Integer arrivatId;
    private String arrivalCity;
    private Double distance;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer companyId;
    private String company;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Route(Integer id, Integer departureId, Integer arrivatId, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer companyId, Double distance) {
        this.id = id;
        this.departureId = departureId;
        this.arrivatId = arrivatId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.companyId = companyId;
        this.distance = distance;
    }

    public Route(Integer id, String departureCity, String arrivalCity, LocalDateTime departureTime, LocalDateTime arrivalTime, String company, Double distance) {
        this.id = id;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.company = company;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public Integer getArrivatId() {
        return arrivatId;
    }

    public void setArrivatId(Integer arrivatId) {
        this.arrivatId = arrivatId;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public static Boolean add(Integer departure, Integer arrival, Integer company, String departureDateTime, String arrivalDateTime,Double distance) {
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("INSERT INTO routes VALUES (null,?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setInt(1, departure);
            stmnt.setInt(2, arrival );
            stmnt.setDouble(3, distance);
            stmnt.setString(4, departureDateTime );
            stmnt.setString(5, arrivalDateTime );
            stmnt.setInt(6, company );

            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Route> getRoutes() {
        ObservableList<Route> routes = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select r.id, r.departure_time as departure_time, r.arrival_time as arrival_time, r.distance as distance, c1.name as departure, c2.name as arrival, tc.name as company from routes as r inner join cities as c1 on r.dearture_city_id = c1.id inner JOIN cities as c2 on r.arrival_city_id = c2.id INNER JOIN transport_companies as tc on r.company_id = tc.id");

            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                routes.add(new Route(
                        rs.getInt(1),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(2).toLocalDateTime(),
                        rs.getTimestamp(3).toLocalDateTime(),
                        rs.getString(7),
                        rs.getDouble(4)
                        ));
            }
            return routes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return routes;
        }
    }

    public static ObservableList<Route> getRoutesFiltered(Integer departure, Integer arrival) {
        ObservableList<Route> routes = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select r.id, r.departure_time as departure_time, r.arrival_time as arrival_time, r.distance as distance, c1.name as departure, c2.name as arrival, tc.name as company from routes as r inner join cities as c1 on r.dearture_city_id = c1.id inner JOIN cities as c2 on r.arrival_city_id = c2.id INNER JOIN transport_companies as tc on r.company_id = tc.id WHERE r.dearture_city_id = ? AND r.arrival_city_id = ?");
            stmnt.setInt(1, departure);
            stmnt.setInt(2, arrival );
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                routes.add(new Route(
                        rs.getInt(1),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(2).toLocalDateTime(),
                        rs.getTimestamp(3).toLocalDateTime(),
                        rs.getString(7),
                        rs.getDouble(4)
                ));
            }
            return routes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return routes;
        }
    }
}
