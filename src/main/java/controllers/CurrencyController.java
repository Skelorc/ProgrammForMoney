package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Export;
import utils.Import;
import views.CurrencyView;

import static messages.StaticMessage.*;
import static models.Model.getModel;
import static textConst.StringConst.NAME_TABLE_CURRENCY;

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
            int index = getTableView_currency().getSelectionModel().getSelectedIndex();
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
    private void exportFile(ActionEvent actionEvent)
    {
        Export export = new Export(NAME_TABLE_CURRENCY,getTableView_currency());
        export.createDocumentAndAddHeaders();
        export.addCurrencyData();
        showProgressBar();

    }

    @FXML
    private void importFile(ActionEvent actionEvent)
    {
        Import import_Excel = new Import();
        import_Excel.findFile();
        import_Excel.readFile(getTableView_currency());
    }

    @FXML
    void initialize() {
        initData();
    }

}
