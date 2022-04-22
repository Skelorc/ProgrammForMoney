package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.AverageView;

import static messages.StaticMessage.createInfoDialog;
import static models.Model.getModel;

public class AverageController extends AverageView {

    @FXML
    void initialize() {
        initData();
    }

    @FXML
    private void averageControls(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        String id_button = btn.getId();
        if (id_button.equals(btn_add.getId())) {
            getModel().saveNewDataObject(cb_to, cb_from, cb_currency, cb_ncc, cb_type, dp_date, tf_amount, getTableView_average().getId());
        }
        if(id_button.equals(btn_show_all.getId()))
        {
            showAll();
        }
        if(id_button.equals(btn_find.getId()))
        {
            getModel().getDataByFilters(
                    cb_from.getSelectionModel().getSelectedItem(),
                    cb_to.getSelectionModel().getSelectedItem(),
                    cb_currency.getSelectionModel().getSelectedItem(),
                    cb_ncc.getSelectionModel().getSelectedItem(),
                    cb_type.getSelectionModel().getSelectedItem(),
                  null,
                    dp_date.getValue(),
                    null,
                    null,
                    null,
                    tf_amount.getText(),
                    "",
                    getTableView_average().getId()
            );
            showFilteredProjectsList();
            clearFields();
        }
        if(id_button.equals(btn_clear_fields.getId()))
        {
            clearFields();
        }
        if(id_button.equals(btn_remove.getId()))
        {
            int index = getTableView_average().getSelectionModel().getSelectedIndex();
            getModel().deleteProject(index,getTableView_average().getId());
        }
    }
}
