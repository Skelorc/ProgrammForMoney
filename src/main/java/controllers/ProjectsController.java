package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.ProjectsView;

import static messages.StaticMessage.createInfoDialog;
import static models.Model.getModel;


public class ProjectsController extends ProjectsView {

    @FXML
    public void addNewParams(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id_button = pressed_button.getId();
        if (id_button.equals(btn_add_from.getId())) {
            getModel().saveNewParams(cb_edit_from, tf_from);
            addDataToComboBoxesAndClearTextField(cb_from, cb_edit_from, tf_from);
        } else if (id_button.equals(btn_add_to.getId())) {
            getModel().saveNewParams(cb_edit_to, tf_to);
            addDataToComboBoxesAndClearTextField(cb_to, cb_edit_to, tf_to);
        } else if (id_button.equals(btn_add_cc.getId())) {
            getModel().saveNewParams(cb_edit_cc, tf_cc);
            addDataToComboBoxesAndClearTextField(cb_cc, cb_edit_cc, tf_cc);
        } else if (id_button.equals(btn_add_type.getId())) {
            getModel().saveNewParams(cb_edit_type, tf_type);
            addDataToComboBoxesAndClearTextField(cb_type, cb_edit_type, tf_type);
        } else if (id_button.equals(btn_add_budget.getId())) {
            getModel().saveNewParams(cb_edit_budget, tf_budget);
            addDataToComboBoxesAndClearTextField(cb_budget, cb_edit_budget, tf_budget);
        } else if (id_button.equals(btn_add_amount.getId())) {
            getModel().saveNewParams(cb_edit_amount, tf_amount);
            addDataToComboBoxesAndClearTextField(cb_amount, cb_edit_amount, tf_amount);
        } else if (id_button.equals(btn_add_relations.getId())) {
            getModel().saveNewParams(cb_edit_relations, tf_relations);
            addDataToComboBoxesAndClearTextField(cb_relations, cb_edit_relations, tf_relations);
        } else if (id_button.equals(btn_add_category.getId())) {
            getModel().saveNewParams(cb_edit_category, tf_category);
            addDataToComboBoxesAndClearTextField(cb_category, cb_edit_category, tf_category);
        } else if (id_button.equals(btn_add_status.getId())) {
            getModel().saveNewParams(cb_edit_status, tf_status);
            addDataToComboBoxesAndClearTextField(cb_status, cb_edit_status, tf_status);
        }
    }

    @FXML
    public void deleteParams(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id = pressed_button.getId();
         if (id.equals(btn_remove_from.getId())) {
            getModel().removeParams(cb_edit_from);
            removeParamsFromComboBoxes(cb_edit_from, cb_from);
        } else if (id.equals(btn_remove_to.getId())) {
            getModel().removeParams(cb_edit_to);
            removeParamsFromComboBoxes(cb_edit_to, cb_to);
        } else if (id.equals(btn_remove_cc.getId())) {
            getModel().removeParams(cb_edit_cc);
            removeParamsFromComboBoxes(cb_edit_cc, cb_cc);
        } else if (id.equals(btn_remove_type.getId())) {
            getModel().removeParams(cb_edit_type);
            removeParamsFromComboBoxes(cb_edit_type, cb_type);
        } else if (id.equals(btn_remove_budget.getId())) {
            getModel().removeParams(cb_edit_budget);
            removeParamsFromComboBoxes(cb_edit_budget, cb_budget);
        } else if (id.equals(btn_remove_amount.getId())) {
            getModel().removeParams(cb_edit_amount);
            removeParamsFromComboBoxes(cb_edit_amount, cb_amount);
        } else if (id.equals(btn_remove_relations.getId())) {
            getModel().removeParams(cb_edit_relations);
            removeParamsFromComboBoxes(cb_edit_relations, cb_relations);
        } else if (id.equals(btn_remove_category.getId())) {
            getModel().removeParams(cb_edit_category);
            removeParamsFromComboBoxes(cb_edit_category, cb_category);
        } else if (id.equals(btn_remove_status.getId())) {
            getModel().removeParams(cb_edit_status);
            removeParamsFromComboBoxes(cb_edit_status, cb_status);
        }
    }

    @FXML
    public void controlTable(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id = pressed_button.getId();
        if (id.equals(btn_create.getId())) {
            getModel().saveNewDataObject(
                    cb_from, cb_to,  cb_currency, cb_cc, cb_type,
                            dp_date, cb_budget, cb_amount, tf_description,
                            cb_relations, cb_category, cb_status
                    );
            //addDataToTable(getModel().getProjectModel().getProject());
            getModel().getProjectModel().setProject(null);
            createInfoDialog("Data add to DB!", btn_create);
        }
        else if(id.equals(btn_update.getId()))
        {
            getModel().updateProject(cb_to, cb_from,cb_currency, cb_cc, cb_type,
                    dp_date, cb_budget, cb_amount, tf_description,
                    cb_relations, cb_category, cb_status);
           // updateTable(getModel().getProjectModel().getProject());
            getModel().getProjectModel().setProject(null);
        }
        else if(id.equals(btn_delete.getId()))
        {
            deleteProjectFromTable();
            getModel().deleteProject();
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
