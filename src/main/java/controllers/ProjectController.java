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
            getModel().saveNewDataObject(cb_to, cb_from, cb_relations, cb_category, cb_status);
        }
        if(id_button.equals(btn_remove.getId()))
        {
            int index = getTableView().getSelectionModel().getSelectedIndex();
            getModel().deleteProject(index);
            createInfoDialog("Delete row!", btn_remove);
        }
    }
}
