package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Main.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Main.showApp(
                getClass(),
                "../View/MainView.fxml", 1900, 1080);

    }
//getClass().().
    public static void showApp(Class windowClass, String viewName, int w, int h) throws IOException {
        Parent root = FXMLLoader.load(windowClass.getResource(viewName));

        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, w, h));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}