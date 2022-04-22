package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.CurrencyView;

import static messages.StaticMessage.createErrorAlertDialog;
import static messages.StaticMessage.createInfoDialog;
import static models.Model.getModel;

public class CurrencyController extends CurrencyView {

    @FXML
    private void currencyControl(ActionEvent actionEvent)
    {
        Button btn = (Button)actionEvent.getSource();
        String id_button = btn.getId();
        if(id_button.equals(btn_add_type.getId())) {
            getModel().createTypeCurrency(tf_type_currency.getText());
            clearTextField(tf_type_currency);
        }
        if(id_button.equals(btn_remove_type.getId()))
        {
            getModel().removeTypeCurrency(cb_type.getSelectionModel().getSelectedItem());
        }
        if(id_button.equals(btn_add_row.getId()))
        {
            addNewRow();
            clearFields();
        }
        if(id_button.equals(btn_add_relations.getId()))
        {
            try {
                getModel().addRelations(cb_first_value.getSelectionModel().getSelectedItem(),
                        cb_second_value.getSelectionModel().getSelectedItem());
                addColumnToTable();
            } catch (Exception e)
            {
                createErrorAlertDialog("Please, for create relations, you must choice value from combobox, choice date, and enter value!");
                e.printStackTrace();
            }
            clearFields();
        }
        if(id_button.equals(btn_clear_fields.getId()))
        {
            clearFields();
            getModel().setCurrencyForEdit(null);
        }
        if(id_button.equals(btn_remove_row.getId()))
        {
            int index = getTableView().getSelectionModel().getSelectedIndex();
            getModel().deleteCurrency(index);
        }
        if(id_button.equals(btn_remove_column.getId()))
        {
            getModel().removeRelations(cb_first_value.getSelectionModel().getSelectedItem(),
                    cb_second_value.getSelectionModel().getSelectedItem());
            removeColumn();
        }
    }

    @FXML
    void initialize() {
        initData();
    }

}
