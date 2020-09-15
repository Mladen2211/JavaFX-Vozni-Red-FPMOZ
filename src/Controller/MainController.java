package Controller;

import App.Main;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML
    private Label mainError;
    @FXML
    private ToggleButton showHideBtn;
    @FXML
    private Button adminPanelButton;
    @FXML
    private ComboBox<Model.City> departure = new ComboBox<>();
    @FXML
    private ComboBox<Model.City> arrival = new ComboBox<>();


    @FXML private TableView<Route> routeTable;
    @FXML private TableColumn<Route, Integer> routeId;
    @FXML private TableColumn<Route, String> routeDeparture;
    @FXML private TableColumn<Route, String> routeArrival;
    @FXML private TableColumn<Route, Double> routeDistance;
    @FXML private TableColumn<Route, LocalDateTime> routeDepartureTime;
    @FXML private TableColumn<Route, LocalDateTime> routeArrivalTime;
    @FXML private TableColumn<Route, String> routeCompany;

    @FXML private TableView<PricePerKilometer> priceTable;
    @FXML private TableColumn<PricePerKilometer, Integer> priceId;
    @FXML private TableColumn<PricePerKilometer, String> priceType;
    @FXML private TableColumn<PricePerKilometer, Double> pricePrice;


    @FXML
    public void adminPanelButton(ActionEvent ev) throws IOException {
        Main.showApp(
                getClass(),
                "../View/loginView.fxml",
                1920, 1080
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        this.routeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.routeDeparture.setCellValueFactory(new PropertyValueFactory<>("departureCity"));
        this.routeArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalCity"));
        this.routeDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        this.routeDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        this.routeArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        this.routeCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        this.priceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.priceType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.pricePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        setMembersList();
    }

    private void setMembersList(){
        ObservableList<City> cities = FXCollections.observableList(City.getCities());
        ObservableList<Model.PricePerKilometer> prices = FXCollections.observableList(PricePerKilometer.getPrices());
        this.priceTable.setItems(prices);
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
    }

    public void ShowHidePrices(ActionEvent actionEvent) {
        if(this.priceTable.isVisible()){
            this.priceTable.setVisible(false);
            this.showHideBtn.setText("Prikaži cijene");
        } else {
            this.priceTable.setVisible(true);
            this.showHideBtn.setText("Sakrij cijene");
        }
    }

    public void searchRoutes(ActionEvent actionEvent) {
        Integer departureInt = this.departure.getSelectionModel().getSelectedItem().getId();
        Integer arrivalInt = this.arrival.getSelectionModel().getSelectedItem().getId();
        if (departure == arrival) {
            mainError.setVisible(true);
            mainError.setText("Ne možete tražiti rutu s dva ista grada!");
        } else {
            ObservableList<Model.Route> routes = Route.getRoutesFiltered(departureInt, arrivalInt);
            this.routeTable.setItems(routes);
        }
    }
}
