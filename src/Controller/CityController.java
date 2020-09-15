package Controller;

import Model.City;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CityController {

    @FXML
    private Label errorCity;

    @FXML
    private Label successCity;

    @FXML
    private TextField cityName;

    public void createNewCity(){
        String newCity = this.cityName.getText().toString();


        if (newCity.equals("")){
            errorCity.setVisible(true);
        } else {
            errorCity.setVisible(false);

            Boolean created = City.add(newCity);
            if(created){
                errorCity.setVisible(false);
                successCity.setVisible(true);
                this.cityName.clear();

                System.out.println("e tako treba");
            } else{
                System.out.println("e tako ne treba");
                errorCity.setVisible(true);
                successCity.setVisible(false);
            }
        }
    }

}
