package Controller;

import Model.User;

import App.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class LoginController {
    public static User loggedInUser;

    @FXML
    Button logInBtn;

    @FXML
    TextField usernameTxt;

    @FXML
    TextField passwordTxt;

    @FXML
    Label error;


    @FXML
    public void logIn(ActionEvent ev){
        System.out.println(this.usernameTxt);
        String username = this.usernameTxt.getText().toString();
        String password = this.passwordTxt.getText().toString();
        if (username.equals("") || password.equals("")){
            error.setVisible(true);
        } else {
            error.setVisible(false);
            try {
                LoginController.loggedInUser = User.login(username, password);

                if (LoginController.loggedInUser != null) {
                    Main.showApp(
                            getClass(),
                            "../View/AdminView.fxml",
                            1920, 1080
                    );
                } else {
                    error.setText("Neuspjela prijava");
                    error.setVisible(true);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
