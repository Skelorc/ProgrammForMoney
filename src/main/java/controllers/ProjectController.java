package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.ProjectView;

import javafx.event.ActionEvent;

import static messages.StaticMessage.createInfoDialog;
import static models.Model.getModel;

public class ProjectController extends ProjectView {

    @FXML
    void initialize() {
        initData();
    }

    @FXML
    private void projectControls(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        String id_button = btn.getId();
        if (id_button.equals(btn_add.getId())) {
            getModel().saveNewDataObject(cb_to, cb_from, cb_relations, cb_category, cb_status,getTableView_project().getId());
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
                    null,
                    null,
                    null,
                    cb_relations.getSelectionModel().getSelectedItem(),
                    null,
                    null,
                    cb_status.getSelectionModel().getSelectedItem(),
                    cb_category.getSelectionModel().getSelectedItem(),
                    "",
                    "",
                    getTableView_project().getId()
            );
            showFilteredProjectsList();
        }
        if(id_button.equals(btn_clear_fields.getId()))
        {
            clearFields();
        }
        if(id_button.equals(btn_remove.getId()))
        {
            int index = getTableView_project().getSelectionModel().getSelectedIndex();
            getModel().deleteProject(index, getTableView_project().getId());
        }
    }
}
