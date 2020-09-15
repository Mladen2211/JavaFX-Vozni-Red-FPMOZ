package Model;

import Database.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User(Integer id, String username, String firstName, String lastName, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static User login (String username, String password) throws Exception {
        String sql = "SELECT id FROM users WHERE username=? AND password=?";
        PreparedStatement qry = Connection.db.CONNECTION.prepareStatement(sql);

        qry.setString(1, username);
        qry.setString(2, password);

        ResultSet rs = qry.executeQuery();

        if (rs.next()){
            return User.get(rs.getInt(1)) ;
        } else {
            return null;
        }

    }

    public static User get (int ID) {
        try {
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("SELECT * FROM users WHERE id=?");
            stmnt.setInt(1, ID);
            ResultSet rs = stmnt.executeQuery();


            if (rs.next()){
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Neuspješno izvlačenje korisnika iz baze: " + e.getMessage());
            return null;
        }
    }

    public static Boolean add(String username, String name, String password, String surname) {

        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("INSERT INTO users VALUES (null,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1,username);
            stmnt.setString(2,name);
            stmnt.setString(3,surname);
            stmnt.setString(4,password);
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static Boolean delete(User selUser) {
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Delete from users where id = ?", Statement.RETURN_GENERATED_KEYS);
           stmnt.setInt(1, selUser.getId());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try{
            PreparedStatement stmnt = Connection.db.CONNECTION.prepareStatement("Select * from users");

            ResultSet rs = stmnt.executeQuery();
            while(rs.next()){
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return users;
        }
    }
}
