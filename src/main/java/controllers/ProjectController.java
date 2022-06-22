package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Export;
import utils.Import;
import views.ProjectView;

import static messages.StaticMessage.showProgressBar;
import static models.Model.getModel;
import static textConst.StringConst.NAME_TABLE_PROJECT;

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
            getModel().saveNewProject(cb_from,cb_to,null,null,null,null,
                    cb_relations, cb_category, cb_status, null,null,null, getTableView_project().getId());
        }
        if(id_button.equals(btn_show_all.getId()))
        {
            showAll();
        }
        if(id_button.equals(btn_find.getId()))
        {
            getModel().getDataByFilters(
                    ccb_from,
                    ccb_to,
                    null,
                    null,
                    null,
                    ccb_relations,
                    null,
                    null,
                    ccb_status,
                    ccb_category,
                    null,
                    null,
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

    @FXML
    private void exportFile(ActionEvent actionEvent)
    {
        Export export = new Export(NAME_TABLE_PROJECT,getTableView_project());
        export.createDocumentAndAddHeaders();
        export.addProjectData();
        showProgressBar();
    }

    @FXML
    private void importFile(ActionEvent actionEvent)
    {
        Import import_Excel = new Import();
        import_Excel.findFile();
        import_Excel.readFile(getTableView_project());
    }
}
