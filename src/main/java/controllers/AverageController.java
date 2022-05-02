package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Export;
import utils.Import;
import views.AverageView;

import static messages.StaticMessage.showProgressBar;
import static models.Model.getModel;
import static textConst.StringConst.NAME_TABLE_AVERAGE;

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
                    ccb_from,
                    ccb_to,
                    ccb_currency,
                    ccb_ncc,
                    ccb_type,
                  null,
                    null,
                    null,
                    null,
                    null,
                    tf_filter_amount,
                    null,
                    getTableView_average().getId()
            );
            showFilteredProjectsList();
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

    @FXML
    private void exportFile(ActionEvent actionEvent)
    {
        Export export = new Export(NAME_TABLE_AVERAGE, getTableView_average());
        export.createDocumentAndAddHeaders();
        export.addProjectData();
        showProgressBar();

    }

    @FXML
    private void importFile(ActionEvent actionEvent)
    {
        Import import_Excel = new Import();
        import_Excel.findFile();
        import_Excel.readFile(getTableView_average());
    }
}
