package Controller;

import App.Main;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

//import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

public class AdminController implements Initializable{
    @FXML
    private Button updatePrice;
    @FXML
    private TextField selPriceType;
    @FXML
    private TextField selPriceValue;
    @FXML
    private Label selPriceId;




    @FXML
    private Label selectedUser;
    @FXML
    private Button deleteUserBtn;


    @FXML
    TableView<Ticket> tableView;
    @FXML TableColumn<Ticket, Integer> tableId;
    @FXML TableColumn<Ticket, String> tableDeparture;
    @FXML TableColumn<Ticket, String> tableArrival;
    @FXML TableColumn<Ticket, Double> tablePrice;
    @FXML TableColumn<Ticket, String> tableTicketIssuer;

    @FXML TableView<User> userTableView;
    @FXML TableColumn<User, Integer> userTableId;
    @FXML TableColumn<User, String> userTableName;
    @FXML TableColumn<User, String> userTablelastName;
    @FXML TableColumn<User, Double> userTableUserNAme;

    @FXML TableView<City> cityTableView;
    @FXML TableColumn<City, Integer> cityTableId;
    @FXML TableColumn<City, String> cityTableName;

    @FXML TableView<PricePerKilometer> priceTableView;
    @FXML TableColumn<PricePerKilometer, Integer> priceTableId;
    @FXML TableColumn<PricePerKilometer, String> priceTableType;
    @FXML TableColumn<PricePerKilometer, Double> priceTablePrice;

    @FXML TableView<Company> companyTableView;
    @FXML TableColumn<Company, Integer> companyTableId;
    @FXML TableColumn<Company, String> companyTableCity;
    @FXML TableColumn<Company, String> companyTableName;


    @FXML TableView<Route> routeTableView;
    @FXML TableColumn<Route, Integer> routeTableId;
    @FXML TableColumn<Route, String> routeTableDeparture;
    @FXML TableColumn<Route, String> routeTableArrival;
    @FXML TableColumn<Route, Double> routeTableDistance;
    @FXML TableColumn<Route, LocalDateTime> routeTableTicketDepartureTime;
    @FXML TableColumn<Route, LocalDateTime> routeTableTicketArrivalTime;

    @FXML
    public void logout(ActionEvent ev) throws IOException {
        Main.showApp(
                getClass(),
                "../View/MainView.fxml",
                1920, 1080
        );
    }
    private User selUser = null;
    private PricePerKilometer selPrice = null;
    public void setTickets() {
        ObservableList<Ticket> tickets = (ObservableList<Ticket>) Ticket.getTickets();
        System.out.println(tickets);
        this.tableView.setItems(tickets);
    }


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tableDeparture.setCellValueFactory(new PropertyValueFactory<>("route_departure"));
        this.tableArrival.setCellValueFactory(new PropertyValueFactory<>("route_arrival"));
        this.tablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.tableTicketIssuer.setCellValueFactory(new PropertyValueFactory<>("issuedBy"));

        this.userTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.userTableName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.userTablelastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.userTableUserNAme.setCellValueFactory(new PropertyValueFactory<>("username"));

        this.cityTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.cityTableName.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.priceTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.priceTableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.priceTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        this.companyTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.companyTableCity.setCellValueFactory(new PropertyValueFactory<>("headquarter"));
        this.companyTableName.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.routeTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.routeTableDeparture.setCellValueFactory(new PropertyValueFactory<>("departureCity"));
        this.routeTableArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalCity"));
        this.routeTableDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        this.routeTableTicketDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        this.routeTableTicketArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        setMembersList();
        setTickets();

    }

    private void setMembersList(){

        ObservableList<Model.City> cities = FXCollections.observableList(City.getCities());
        ObservableList<Model.Company> companies = FXCollections.observableList(Company.getCompanies());
        ObservableList<Model.PricePerKilometer> prices = FXCollections.observableList(PricePerKilometer.getPrices());
        ObservableList<Model.Route> routes = FXCollections.observableList(Route.getRoutes());
        ObservableList<Model.User> users = FXCollections.observableList(User.getUsers());
        this.cityTableView.setItems(City.getCities());
        this.companyTableView.setItems(Company.getCompanies());
        this.priceTableView.setItems(PricePerKilometer.getPrices());
        this.routeTableView.setItems(Route.getRoutes());
        this.userTableView.setItems(User.getUsers());


    }


    public void openNewUserDialog(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation();

            DialogPane addStudentPane = fxmlLoader.load(getClass().getResource("../View/AddUser.fxml"));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addStudentPane);

            Optional<ButtonType> clickedButton = dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewCity(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation();

            DialogPane addCityPane = fxmlLoader.load(getClass().getResource("../View/AddCity.fxml"));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addCityPane);

            Optional<ButtonType> clickedButton = dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewprice(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation();

            DialogPane addCityPane = fxmlLoader.load(getClass().getResource("../View/AdNewPricePerKilometer.fxml"));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addCityPane);

            Optional<ButtonType> clickedButton = dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewCompany(ActionEvent actionEvent) {
        try{
            //hq.setItems(newData);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/AddCompany.fxml"));

            DialogPane addCityPane =(DialogPane) fxmlLoader.load();
            fxmlLoader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addCityPane);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openNewRoute(ActionEvent actionEvent) {
        try{
            //hq.setItems(newData);

            FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation();

            DialogPane addCityPane = fxmlLoader.load(getClass().getResource("../View/Route.fxml"));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addCityPane);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void openTicket(ActionEvent actionEvent) {
        try{
            //hq.setItems(newData);

            FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation();

            DialogPane addCityPane = fxmlLoader.load(Main.class.getResource("../View/PrintNewTicket.fxml"));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addCityPane);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void getUser(MouseEvent ev){
        this.selUser = this.userTableView.getSelectionModel().getSelectedItem();
        this.selectedUser.setText(this.selUser.getUsername());
        this.selectedUser.setVisible(true);
        this.deleteUserBtn.setVisible(true);
    }

    public void deleteUser(ActionEvent actionEvent) {
        if(this.selUser != null){
            Boolean deleted = User.delete(this.selUser);
            if (deleted) {
                this.selectedUser.setText("Korsnik uspiješno obrisan");
                this.deleteUserBtn.setVisible(false);

                userTableView.getSelectionModel().clearSelection();
                userTableView.getItems().clear();
                userTableView.getItems().addAll(selUser);
            } else {
                this.selectedUser.setText("Desila se greška pri brisanju korisnika");
                this.deleteUserBtn.setVisible(false);
            }
        }
    }

    public void selectPrice(MouseEvent mouseEvent) {
        this.selPrice = this.priceTableView.getSelectionModel().getSelectedItem();
        if(this.selPrice != null) {
            this.selPriceId.setVisible(true);
            this.selPriceType.setVisible(true);
            this.selPriceValue.setVisible(true);
            this.selPriceId.setText(this.selPrice.getId().toString());
            this.selPriceType.setText(String.valueOf(this.selPrice.getType()));
            this.selPriceValue.setText(String.valueOf(this.selPrice.getPrice()));
            this.updatePrice.setVisible(true);
        }
    }

    public void updatePrice(ActionEvent actionEvent) {
        if(this.selPriceValue.getText().toString().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")){
            Boolean updated = PricePerKilometer.update(Integer.parseInt(selPriceId.getText().toString()), selPriceType.getText().toString(), selPriceValue.getText().toString());
            if (updated) {
                this.selPriceId.setText("Cijena uspiješno promjenjena");
                this.updatePrice.setVisible(false);
                this.selPriceType.setVisible(false);
                this.selPriceValue.setVisible(false);
                this.setMembersList();
                this.setTickets();
            } else {
                this.selectedUser.setText("Desila se greška pri brisanju korisnika");
                this.deleteUserBtn.setVisible(false);
            }
        }
    }

}
