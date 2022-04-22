package views;

import entity.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import views.tables.AverageTable;

import java.util.Map;

import static models.Model.getModel;

@Getter
public class AverageView extends AbstractView{

    @FXML
    protected Button btn_add,btn_show_all,btn_clear_fields, btn_find, btn_remove;
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_currency, cb_ncc, cb_type;
    @FXML
    protected DatePicker dp_date;
    @FXML
    protected TextField tf_amount;
    @FXML
    private TableView<Project> tableView_average;


    @Override
    protected void addValuesToComboBox() {
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        ObservableList<String> allTypes = getModel().getAllTypes();
        cb_to.setItems(allParams.get("to"));
        cb_from.setItems(allParams.get("from"));
        cb_currency.setItems(allTypes);
        cb_ncc.setItems(allParams.get("ncc"));
        cb_type.setItems(allParams.get("type"));
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
        cb_from.getSelectionModel().clearSelection();
        cb_currency.getSelectionModel().clearSelection();
        cb_ncc.getSelectionModel().clearSelection();
        cb_type.getSelectionModel().clearSelection();
        dp_date.getEditor().clear();
        clearTextField(tf_amount);
    }
}
