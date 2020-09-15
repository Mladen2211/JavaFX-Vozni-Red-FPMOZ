package Database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    private String host = "localhost";
    private String db_name = "vozni_red";
    private String user = "root";
    private String password = "";


    public final static Connection db = new Connection();

    public java.sql.Connection CONNECTION;

    public java.sql.Connection getCONNECTION() {
        return CONNECTION;
    }

    public Connection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.CONNECTION = DriverManager.getConnection(
                    "jdbc:mysql://"+this.host+":3306/"+this.db_name,
                    this.user,
                    this.password
            );
        } catch (ClassNotFoundException ex){
            System.out.println("Unexpected error " + ex.getMessage());
        } catch (SQLException se){
            System.out.println("Unable to connect to database with error:"+ se.getMessage());
        }
    }
}
