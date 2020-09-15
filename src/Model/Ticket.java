package Model;

import Database.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ticket {
    private Integer id;
    private Integer routeId;
    private String route_departure;
    private String route_arrival;
    private Integer priceId;
    private Double price;
    private Integer issuedById;
    private String issuedBy;


    public Ticket() {
    }

    public Ticket(Integer id, Integer routeId, Integer priceId, Integer issuedById) {
        this.id = id;
        this.routeId = routeId;
        this.priceId = priceId;
        this.issuedById = issuedById;
    }

    public Ticket(Integer id, String route_departure, String route_arrival, Double price, String issuedBy) {
        this.id = id;
        this.route_departure = route_departure;
        this.route_arrival = route_arrival;
        this.price = price;
        this.issuedBy = issuedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRoute_departure() {
        return route_departure;
    }

    public void setRoute_departure(String route_departure) {
        this.route_departure = route_departure;
    }

    public String getRoute_arrival() {
        return route_arrival;
    }

    public void setRoute_arrival(String route_arrival) {
        this.route_arrival = route_arrival;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIssuedById() {
        return issuedById;
    }

    public void setIssuedById(Integer issuedById) {
        this.issuedById = issuedById;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public static Boolean add(Integer route, Integer price, Integer user) {
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("INSERT INTO tickets VALUES (null,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setInt(1, route);
            stmnt.setInt(2, price );
            stmnt.setInt(3, user);

            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static Object getTickets() {
        ObservableList<Ticket> tickets = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select t.id as id, c1.name as route_departure, c2.name as route_arrival, \n" +
                    " r.distance*p.price as price, CONCAT(u.first_name, ' ',u.last_name) as issuedBy from tickets as t inner join routes as r on t.route_id = r.id \n" +
                    " INNER JOIN cities as c1 on r.dearture_city_id = c1.id INNER JOIN cities as c2 on r.arrival_city_id = c2.id \n" +
                    " INNER join prices_per_kilometers as p on t.price_id = p.id inner JOIN users as u on t.issued_by_id = u.id");

            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                tickets.add(new Ticket(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5)
                ));
            }
            return tickets;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return tickets;
        }
    }
}
