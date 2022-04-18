package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.DataView;

import static messages.StaticMessage.createInfoDialog;
import static models.Model.getModel;


public class DataController extends DataView {

    @FXML
    public void addNewParams(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id_button = pressed_button.getId();
        if (id_button.equals(btn_add_from.getId())) {
            getModel().saveNewParams(cb_edit_from, tf_from);
            clearTextField(tf_from);
        } else if (id_button.equals(btn_add_to.getId())) {
            getModel().saveNewParams(cb_edit_to, tf_to);
            clearTextField(tf_to);
        } else if (id_button.equals(btn_add_cc.getId())) {
            getModel().saveNewParams(cb_edit_cc, tf_cc);
            clearTextField(tf_cc);
        } else if (id_button.equals(btn_add_type.getId())) {
            getModel().saveNewParams(cb_edit_type, tf_type);
            clearTextField(tf_type);
        } else if (id_button.equals(btn_add_budget.getId())) {
            getModel().saveNewParams(cb_edit_budget, tf_budget);
            clearTextField(tf_budget);
        } else if (id_button.equals(btn_add_category.getId())) {
            getModel().saveNewParams(cb_edit_category, tf_category);
            clearTextField(tf_category);
        } else if (id_button.equals(btn_add_status.getId())) {
            getModel().saveNewParams(cb_edit_status, tf_status);
            clearTextField(tf_status);
        }
    }

    @FXML
    public void deleteParams(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id = pressed_button.getId();
         if (id.equals(btn_remove_from.getId())) {
            getModel().removeParams(cb_edit_from);
            removeParamsFromComboBoxes(cb_edit_from);
            removeParamsFromComboBoxes(cb_from);
        } else if (id.equals(btn_remove_to.getId())) {
            getModel().removeParams(cb_edit_to);
            removeParamsFromComboBoxes(cb_edit_to);
            removeParamsFromComboBoxes(cb_to);
        } else if (id.equals(btn_remove_cc.getId())) {
            getModel().removeParams(cb_edit_cc);
            removeParamsFromComboBoxes(cb_edit_cc);
            removeParamsFromComboBoxes(cb_cc);
        } else if (id.equals(btn_remove_type.getId())) {
            getModel().removeParams(cb_edit_type);
            removeParamsFromComboBoxes(cb_edit_type);
            removeParamsFromComboBoxes(cb_type);
        } else if (id.equals(btn_remove_budget.getId())) {
            getModel().removeParams(cb_edit_budget);
            removeParamsFromComboBoxes(cb_edit_budget);
            removeParamsFromComboBoxes(cb_budget);
        } else if (id.equals(btn_remove_category.getId())) {
            getModel().removeParams(cb_edit_category);
            removeParamsFromComboBoxes(cb_edit_category);
        } else if (id.equals(btn_remove_status.getId())) {
            getModel().removeParams(cb_edit_status);
            removeParamsFromComboBoxes(cb_edit_status);
        }
    }

    @FXML
    public void controlTable(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id = pressed_button.getId();
        if (id.equals(btn_create.getId())) {
            getModel().saveNewDataObject(
                    cb_from, cb_to,  cb_currency, cb_cc, cb_type,
                            dp_date, cb_budget, tf_amount, tf_description
                    );
            //addDataToTable(getModel().getProjectModel().getProject());
            getModel().getProjectModel().setProject(null);
            createInfoDialog("Data add to DB!", btn_create);
        }
        else if(id.equals(btn_update.getId()))
        {
           // updateTable(getModel().getProjectModel().getProject());
            getModel().getProjectModel().setProject(null);
        }
        else if(id.equals(btn_delete.getId()))
        {
           /* deleteProjectFromTable();
            getModel().deleteProject(index);*/
            createInfoDialog("Delete!", btn_delete);
        }
        else if(id.equals(btn_find.getId()))
        {

        }
        else if(id.equals(btn_clear_fields.getId()))
        {
            clearFields();
            getModel().getProjectModel().setProject(null);
        }
    }

    @FXML
    void initialize() {
        initData();
    }

}
