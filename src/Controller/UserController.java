package Controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserController {
    @FXML
    private TextField username;


    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField password;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button newUser;

    @FXML
    private Button newCity;

    @FXML
    private Label error;

    @FXML
    private Label success;
    public void createNew(){
        String username = this.username.getText().toString();
        String name = this.name.getText().toString();
        String surname = this.surname.getText().toString();
        String password = this.password.getText().toString();

        if (username.equals("") || password.equals("") || name.equals("") || surname.equals("")){
            error.setVisible(true);
        } else {
            error.setVisible(false);

            Boolean created = User.add(username, name, password, surname);
            if(created){
                error.setVisible(false);
                success.setVisible(true);
                this.username.clear();
                this.name.clear();
                this.surname.clear();
                this.password.clear();
                System.out.println("e tako treba");
            } else{
                System.out.println("e tako ne treba");
                error.setVisible(true);
                success.setVisible(false);
            }
        }
    }

}
