package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import views.CashView;

import static messages.StaticMessage.createErrorAlertDialog;
import static models.Model.getModel;


public class USDController extends CashView {

    @FXML
    void initialize() {
        initData();
    }

    @FXML
    public void createTable(ActionEvent actionEvent)
    {
        if(!tf_scale_date.getText().isEmpty() && !tf_report_date.getText().isEmpty() &&!cb_currency.getSelectionModel().getSelectedItem().isEmpty())
        {
            createTable();
        }
        else
        {
            createErrorAlertDialog("Не введены значения Scale Date, Report Date, or Currency");
            throw new NullPointerException("Не введены значения Scale Date, Report Date, or Currency");
        }
    }
}
