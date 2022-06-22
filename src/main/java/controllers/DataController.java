package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Export;
import utils.Import;
import views.DataView;

import static messages.StaticMessage.showProgressBar;
import static models.Model.getModel;
import static textConst.StringConst.NAME_TABLE_DATA;


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
        } else if (id_button.equals(btn_add_ncc.getId())) {
            getModel().saveNewParams(cb_edit_ncc, tf_ncc);
            clearTextField(tf_ncc);
        } else if (id_button.equals(btn_add_type.getId())) {
            getModel().saveNewParams(cb_edit_type, tf_type);
            clearTextField(tf_type);
        } else if (id_button.equals(btn_add_budget.getId())) {
            getModel().saveNewParams(cb_edit_budget, tf_budget);
            clearTextField(tf_budget);
        } else if (id_button.equals(btn_add_relations.getId())) {
            getModel().saveNewParams(cb_edit_relations, tf_relations);
            clearTextField(tf_relations);
        }
        else if (id_button.equals(btn_add_category.getId())) {
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
            cb_edit_from.getSelectionModel().clearSelection();
        } else if (id.equals(btn_remove_to.getId())) {
            getModel().removeParams(cb_edit_to);
            cb_edit_to.getSelectionModel().clearSelection();
        } else if (id.equals(btn_remove_ncc.getId())) {
            getModel().removeParams(cb_edit_ncc);
            cb_edit_ncc.getSelectionModel().clearSelection();
        } else if (id.equals(btn_remove_type.getId())) {
            getModel().removeParams(cb_edit_type);
            cb_edit_type.getSelectionModel().clearSelection();
        } else if (id.equals(btn_remove_budget.getId())) {
            getModel().removeParams(cb_edit_budget);
            cb_edit_budget.getSelectionModel().clearSelection();
        } else if (id.equals(btn_remove_relations.getId())) {
            getModel().removeParams(cb_edit_relations);
            cb_edit_relations.getSelectionModel().clearSelection();
        }else if (id.equals(btn_remove_category.getId())) {
            getModel().removeParams(cb_edit_category);
            cb_edit_category.getSelectionModel().clearSelection();
        } else if (id.equals(btn_remove_status.getId())) {
            getModel().removeParams(cb_edit_status);
            cb_edit_status.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void controlTable(ActionEvent actionEvent) {
        Button pressed_button = (Button) actionEvent.getSource();
        String id = pressed_button.getId();
        if(id.equals(btn_add.getId()))
        {
            getModel().saveNewProject(
                    cb_from, cb_to, cb_currency, cb_ncc,cb_type,
                    tf_date,null,null,null,
                    cb_budget,tf_amount,tf_description,tableView_data.getId()
            );
        }
        else if(id.equals(btn_delete.getId()))
        {
            int index = tableView_data.getSelectionModel().getSelectedIndex();
            getModel().deleteProject(index, getTableView_data().getId());
        }
        else if(id.equals(btn_find.getId()))
        {
            getModel().getDataByFilters(
                    ccb_from,
                    ccb_to,
                    ccb_currency,
                    ccb_ncc,
                    ccb_type,
                    null,
                    null,
                    ccb_budget,
                    null,
                    null,
                    tf_filter_amount,
                    tf_filter_description,
                    getTableView_data().getId()
            );
            showFilteredProjectsList();
        }
        else if(id.equals(btn_clear_fields.getId()))
        {
            clearFields();
            getModel().getProjectModel().setProject(null);
        }
        else if(id.equals(btn_show_all.getId()))
         {
             showAll();
         }
    }

    @FXML
    private void exportFile(ActionEvent actionEvent)
    {
        Export export = new Export(NAME_TABLE_DATA,getTableView_data());
        export.createDocumentAndAddHeaders();
        export.addProjectData();
        showProgressBar();

    }

    @FXML
    private void importFile(ActionEvent actionEvent)
    {
        Import import_Excel = new Import();
        import_Excel.findFile();
        import_Excel.readFile(getTableView_data());

    }

    @FXML
    void initialize() {
        initData();
    }

}
