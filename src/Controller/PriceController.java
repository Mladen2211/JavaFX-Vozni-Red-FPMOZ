package Controller;

import Model.PricePerKilometer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PriceController {

    @FXML
    private Label errorPrice;

    @FXML
    private Label successPrice;


    @FXML
    private TextField price;

    @FXML
    private TextField type;

    @FXML
    private Label notANumber;

    public void newprice(ActionEvent ev){
        System.out.println();
        if(this.price.getText().toString().matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")){
            notANumber.setVisible(false);
            if(this.type.getText().toString().equals("")){
                errorPrice.setVisible(true);
            } else {
                errorPrice.setVisible(false);

                Boolean created = PricePerKilometer.add(this.price.getText().toString(), this.type.getText().toString());
                if(created){
                    errorPrice.setVisible(false);
                    successPrice.setVisible(true);
                    this.price.clear();
                    this.type.clear();

                    System.out.println("e tako treba");
                } else{
                    System.out.println("e tako ne treba");
                    errorPrice.setVisible(true);
                    successPrice.setVisible(false);
                }
            }
        } else {
            notANumber.setVisible(true);
        }
    }

}
