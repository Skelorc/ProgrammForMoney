package views;

import entity.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import org.controlsfx.control.CheckComboBox;
import views.tables.AverageTable;

import java.util.Map;

import static models.Model.getModel;
import static textConst.StringConst.*;

@Getter
public class AverageView extends AbstractView{

    @FXML
    protected Button btn_add,btn_show_all,btn_clear_fields, btn_find, btn_remove,
    btn_export, btn_import;
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_currency, cb_ncc, cb_type;
    @FXML
    protected CheckComboBox<String> ccb_from, ccb_to, ccb_currency, ccb_ncc, ccb_type;
    @FXML
    protected DatePicker dp_date;
    @FXML
    protected TextField tf_amount,tf_filter_amount;
    @FXML
    private TableView<Project> tableView_average;


    @Override
    protected void addValuesToComboBox() {
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        ObservableList<String> allTypes = getModel().getAllTypes();
        cb_from.setItems(allParams.get(FROM));
        ccb_from.getItems().addAll(allParams.get(FROM));
        cb_to.setItems(allParams.get(TO));
        ccb_to.getItems().addAll(allParams.get(TO));
        cb_currency.setItems(allTypes);
        ccb_currency.getItems().addAll(allTypes);
        cb_ncc.setItems(allParams.get(NCC));
        ccb_ncc.getItems().addAll(allParams.get(NCC));
        cb_type.setItems(allParams.get(TYPE));
        ccb_type.getItems().addAll(allParams.get(TYPE));
    }

    @Override
    protected void addComboBoxToList() {

    }

    @Override
    protected void createTable() {
        table = new AverageTable();
        table.createTableView(tableView_average);
    }

    protected void showFilteredProjectsList()
    {
        tableView_average.setItems(getModel().getFilteredProjects());
    }

    protected void showAll()
    {
        tableView_average.setItems(getModel().getProjectForTable(tableView_average.getId()));
    }

    @Override
    public void clearFields() {
        cb_to.getSelectionModel().clearSelection();
        ccb_to.getCheckModel().clearChecks();
        cb_from.getSelectionModel().clearSelection();
        ccb_from.getCheckModel().clearChecks();
        cb_currency.getSelectionModel().clearSelection();
        ccb_currency.getCheckModel().clearChecks();
        cb_ncc.getSelectionModel().clearSelection();
        ccb_ncc.getCheckModel().clearChecks();
        cb_type.getSelectionModel().clearSelection();
        ccb_type.getCheckModel().clearChecks();
        dp_date.getEditor().clear();
        clearTextField(tf_amount);
        clearTextField(tf_filter_amount);
    }


}
