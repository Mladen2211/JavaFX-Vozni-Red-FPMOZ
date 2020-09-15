package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketController implements Initializable {
    @FXML
    private ComboBox<Route> routeSelect = new ComboBox<>();
    @FXML
    private ComboBox<Model.PricePerKilometer> priceSelect = new ComboBox<>();
    @FXML
    private ComboBox<Model.User> userSelect = new ComboBox<>();
    @FXML
    private Label errorTicket;

    @FXML
    private Label successTicket;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){


        setMembersList();


    }

    private void setMembersList(){

        ObservableList<Model.PricePerKilometer> prices = FXCollections.observableList(PricePerKilometer.getPrices());
        ObservableList<Model.Route> routes = FXCollections.observableList(Route.getRoutes());
        ObservableList<Model.User> users = FXCollections.observableList(User.getUsers());

        routeSelect.itemsProperty().setValue(routes);
        routeSelect.getSelectionModel().selectFirst();

        routeSelect.setConverter(new StringConverter<Route>() {
            @Override
            public String toString(Route route) {
                return route.getDepartureCity()+"-"+route.getArrivalCity();
            }
            @Override
            public Route fromString(final String string) {
                return routeSelect.getItems().stream().filter(product -> product.getDepartureCity().equals(string)).findFirst().orElse(null);
            }
        });
        priceSelect.itemsProperty().setValue(prices);
        priceSelect.getSelectionModel().selectFirst();

        priceSelect.setConverter(new StringConverter<PricePerKilometer>() {
            @Override
            public String toString(PricePerKilometer price) {
                return price.getType();
            }
            @Override
            public PricePerKilometer fromString(final String string) {
                return priceSelect.getItems().stream().filter(product -> product.getType().equals(string)).findFirst().orElse(null);
            }
        });
        userSelect.itemsProperty().setValue(users);
        userSelect.getSelectionModel().selectFirst();

        userSelect.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getFirstName()+" "+user.getLastName();
            }
            @Override
            public User fromString(final String string) {
                return userSelect.getItems().stream().filter(product -> product.getFirstName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void createTicket(ActionEvent actionEvent) {
        Integer route = this.routeSelect.getSelectionModel().getSelectedItem().getId();
        Integer price = this.priceSelect.getSelectionModel().getSelectedItem().getId();
        Integer user = this.userSelect.getSelectionModel().getSelectedItem().getId();

        if (route<0 || price<0 || user<0){
            errorTicket.setVisible(true);
        } else {
            errorTicket.setVisible(false);

            Boolean created = Ticket.add(route, price, user);
            if (created) {
                errorTicket.setVisible(false);
                successTicket.setVisible(true);
                System.out.println("e tako treba");
            } else {
                System.out.println("e tako ne treba");
                errorTicket.setVisible(true);
                successTicket.setVisible(false);
            }
        }
    }

}
