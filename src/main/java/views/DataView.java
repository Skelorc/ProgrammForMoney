package views;

import entity.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import org.controlsfx.control.CheckComboBox;
import views.tables.DataTable;

import java.util.*;

import static models.Model.getModel;


@Getter
public class DataView extends AbstractView {

    private List<ComboBox<String>> boxes;
    private List<CheckComboBox<String>> boxes_filters;

    @FXML
    protected TextField tf_from, tf_to, tf_ncc, tf_type,
            tf_budget, tf_amount, tf_description, tf_relations, tf_category, tf_status,
            tf_filter_amount, tf_filter_description;
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_currency, cb_ncc, cb_type, cb_budget,
            cb_edit_from, cb_edit_to, cb_edit_ncc, cb_edit_type, cb_edit_budget, cb_edit_relations, cb_edit_status, cb_edit_category;

    @FXML
    protected Button btn_add_from, btn_add_to, btn_add_ncc, btn_add_type, btn_add_budget, btn_add_category, btn_add_status, btn_add_relations,
            btn_remove_from, btn_remove_to, btn_remove_ncc, btn_remove_type, btn_remove_budget, btn_remove_relations, btn_remove_category, btn_remove_status,
            btn_add,btn_show_all,btn_clear_fields, btn_find, btn_delete,
            btn_export,btn_import;

    @FXML
    protected CheckComboBox<String> ccb_from, ccb_to, ccb_currency, ccb_ncc, ccb_type, ccb_budget;

    @FXML
    protected DatePicker dp_date;
    @FXML
    protected TableView<Project> tableView_data;


    @Override
    protected void createTable() {
        table = new DataTable();
        table.createTableView(tableView_data);
    }

    @Override
    protected void addValuesToComboBox() {
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        ObservableList<String> currencyTypes = getModel().getCurrencyTypes();
        cb_currency.setItems(currencyTypes);
        ccb_currency.getItems().addAll(currencyTypes);
        for (String key : allParams.keySet()) {
            for (ComboBox<String> box : boxes) {
                if (box.getId().contains(key)) {
                    box.setItems(allParams.get(key));
                }
            }
            for (CheckComboBox<String> box : boxes_filters)
            {
                if(box.getId().contains(key))
                {
                    box.getItems().addAll(allParams.get(key));
                }
            }
        }
    }

    @Override
    public void clearFields() {
        for (ComboBox<String> box : boxes) {
            box.getSelectionModel().clearSelection();
        }
        for (CheckComboBox<String> box : boxes_filters)
        {
            box.getCheckModel().clearChecks();
        }
        dp_date.getEditor().clear();
        clearTextField(tf_description);
        clearTextField(tf_filter_description);
        clearTextField(tf_amount);
        clearTextField(tf_filter_amount);
    }

    protected void showFilteredProjectsList()
    {
        tableView_data.setItems(getModel().getFilteredProjects());
    }

    protected void showAll()
    {
        tableView_data.setItems(getModel().getProjectForTable(tableView_data.getId()));
    }

    @Override
    protected void addComboBoxToList() {
        boxes = new ArrayList<>();
        boxes.add(cb_edit_from);
        boxes.add(cb_edit_to);
        boxes.add(cb_edit_ncc);
        boxes.add(cb_edit_type);
        boxes.add(cb_edit_budget);
        boxes.add(cb_edit_status);
        boxes.add(cb_edit_category);
        boxes.add(cb_edit_relations);
        boxes.add(cb_to);
        boxes.add(cb_from);
        boxes.add(cb_currency);
        boxes.add(cb_ncc);
        boxes.add(cb_type);
        boxes.add(cb_budget);

        boxes_filters = new ArrayList<>();
        boxes_filters.add(ccb_from);
        boxes_filters.add(ccb_to);
        boxes_filters.add(ccb_type);
        boxes_filters.add(ccb_budget);
        boxes_filters.add(ccb_currency);
        boxes_filters.add(ccb_ncc);
    }


}
