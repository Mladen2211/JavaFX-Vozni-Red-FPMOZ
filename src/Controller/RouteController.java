package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RouteController implements Initializable {


    @FXML
    private ComboBox<City> departure = new ComboBox<>();
    @FXML
    private ComboBox<Model.City> arrival = new ComboBox<>();
    @FXML
    private ComboBox<Model.Company> company = new ComboBox<>();
    @FXML
    private DatePicker dDate;
    @FXML
    private DatePicker aDate;
    @FXML
    private TextField aHour;
    @FXML
    private TextField aMinute;
    @FXML
    private TextField dHour;
    @FXML
    private TextField dMinute;
    @FXML
    private TextField distance;
    @FXML
    private Label errorRoute;

    @FXML
    private Label successRoute;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){


        setMembersList();

    }

    private void setMembersList() {

        ObservableList<City> cities = FXCollections.observableList(City.getCities());
        ObservableList<Model.Company> companies = FXCollections.observableList(Company.getCompanies());
        departure.itemsProperty().setValue(cities);
        departure.getSelectionModel().selectFirst();

        departure.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City city) {
                return city.getName();
            }

            @Override
            public City fromString(final String string) {
                return departure.getItems().stream().filter(product -> product.getName().equals(string)).findFirst().orElse(null);
            }
        });
        arrival.itemsProperty().setValue(cities);
        arrival.getSelectionModel().selectFirst();

        arrival.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City city) {
                return city.getName();
            }

            @Override
            public City fromString(final String string) {
                return arrival.getItems().stream().filter(product -> product.getName().equals(string)).findFirst().orElse(null);
            }
        });

        company.itemsProperty().setValue(companies);
        company.getSelectionModel().selectFirst();

        company.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company company) {
                return company.getName();
            }

            @Override
            public Company fromString(final String string) {
                return company.getItems().stream().filter(product -> product.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void createNewRoute(ActionEvent actionEvent) {
        System.out.println("?");
        Integer departure = this.departure.getSelectionModel().getSelectedItem().getId();
        Integer arrival = this.arrival.getSelectionModel().getSelectedItem().getId();
        Integer company = this.company.getSelectionModel().getSelectedItem().getId();
        String dhours = this.dHour.getText().toString()+":"+this.dMinute.getText().toString();
        String ahours = this.aHour.getText().toString()+":"+this.aMinute.getText().toString();

        if(dhours.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$") && ahours.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$") && this.distance.getText().toString().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$") ){
            System.out.println("?");
            String timeColonPattern = "YYYY-MM-dd hh:mm:ss";
            DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
            LocalTime departureTime = LocalTime.of(Integer.parseInt(this.dHour.getText()), Integer.parseInt(this.dMinute.getText()));
            LocalTime arrivalTime = LocalTime.of(Integer.parseInt(this.aHour.getText()), Integer.parseInt(this.aMinute.getText()));
            ZonedDateTime departureDateTime = ZonedDateTime.of(this.aDate.getValue(), departureTime, ZoneId.of("Europe/Berlin"));
            ZonedDateTime arrivalDateTime = ZonedDateTime.of(this.aDate.getValue(), arrivalTime, ZoneId.of("Europe/Berlin"));
            if (company<0 || departure<0 || arrival<0){
                errorRoute.setVisible(true);
            } else {
                errorRoute.setVisible(false);

                Boolean created = Route.add(departure, arrival, company, timeColonFormatter.format(departureDateTime), timeColonFormatter.format(arrivalDateTime), Double.parseDouble(this.distance.getText()));
                if(created){
                    errorRoute.setVisible(false);
                    successRoute.setVisible(true);
                    this.dHour.clear();
                    this.aHour.clear();
                    this.dMinute.clear();
                    this.aMinute.clear();
                    this.distance.clear();

                    System.out.println("e tako treba");
                } else{
                    System.out.println("e tako ne treba");
                    errorRoute.setVisible(true);
                    successRoute.setVisible(false);
                }
            }
        } else {
            errorRoute.setText("Provjerite unsesna polja, polja za vrijeme moraju biti u formatu xx:xx");
        }
    }

}
