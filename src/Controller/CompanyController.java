package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyController  implements Initializable {

    @FXML
    private Label errorCompany;

    @FXML
    private Label successCompany;
    @FXML
    private ComboBox<City> hq = new ComboBox<>();

    @FXML
    private TextField companyName;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){


        setMembersList();


    }

    private void setMembersList() {

        ObservableList<City> cities = FXCollections.observableList(City.getCities());
        /*hq.getItems().setAll(data);
         */
        //hq.setItems(data);
        hq.itemsProperty().setValue(cities);
        hq.getSelectionModel().selectFirst();

        hq.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City city) {
                return city.getName();
            }

            @Override
            public City fromString(final String string) {
                return hq.getItems().stream().filter(product -> product.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void createNewCompany(ActionEvent actionEvent) {
        String newCompany = this.companyName.getText().toString();
        Integer hq = this.hq.getSelectionModel().getSelectedItem().getId();

        if (newCompany.equals("") || hq<0){
            errorCompany.setVisible(true);
        } else {
            errorCompany.setVisible(false);

            Boolean created = Company.add(newCompany, hq);
            if(created){
                errorCompany.setVisible(false);
                successCompany.setVisible(true);
                this.companyName.clear();

                System.out.println("e tako treba");
            } else{
                System.out.println("e tako ne treba");
                errorCompany.setVisible(true);
                successCompany.setVisible(false);
            }
        }
    }

}
