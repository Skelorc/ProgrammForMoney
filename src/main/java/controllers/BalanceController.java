package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.BalanceView;

import static models.Model.getModel;


public class BalanceController extends BalanceView {

    @FXML
    private void controlTable(ActionEvent actionEvent)
    {
        Button pressed_button = (Button) actionEvent.getSource();
        String id = pressed_button.getId();
        if(id.equals(btn_add.getId()))
        {
            getModel().saveNewProject(
                    cb_from, null, cb_currency, cb_ncc,null,
                    tf_date,null,null,null,
                    null,tf_amount,null,tableView_balance.getId()
            );
        }
        else if(id.equals(btn_delete.getId()))
        {
            int index = tableView_balance.getSelectionModel().getSelectedIndex();
            getModel().deleteProject(index, tableView_balance.getId());
        }
        if(id.equals(btn_show_all.getId()))
        {
            showAll();
        }
        if(id.equals(btn_find.getId()))
        {
            getModel().getDataByFilters(
                    ccb_from,
                   null,
                    ccb_currency,
                    ccb_ncc,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    tf_filter_amount,
                    null,
                    tableView_balance.getId()
            );
            showFilteredProjectsList();
        }
        if(id.equals(btn_clear_fields.getId()))
        {
            clearFields();
        }
    }

    @FXML
    void initialize() {
        initData();
    }
}
