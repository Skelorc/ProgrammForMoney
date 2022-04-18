package views;

import entity.Params;
import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import views.tables.DataTable;

import java.util.*;

import static models.Model.getModel;


@Getter
public class DataView extends AbstractView {

    private List<ComboBox<String>> boxes;

    @FXML
    protected TextField tf_from, tf_to, tf_cc, tf_type,
            tf_budget, tf_amount, tf_description, tf_category, tf_status;
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_currency, cb_cc, cb_type, cb_budget,
            cb_edit_from, cb_edit_to, cb_edit_cc, cb_edit_type, cb_edit_budget, cb_edit_status, cb_edit_category;

    @FXML
    protected Button btn_add_from, btn_add_to, btn_add_cc, btn_add_type, btn_add_budget, btn_add_category, btn_add_status,
            btn_remove_from, btn_remove_to, btn_remove_cc, btn_remove_type, btn_remove_budget, btn_remove_category, btn_remove_status,
            btn_create, btn_update, btn_clear_fields, btn_find, btn_delete;

    @FXML
    private CheckBox check_to, check_from, check_cc, check_type, check_date, check_budget, check_amount, check_description;

    @FXML
    protected DatePicker dp_date;
    @FXML
    protected TableView<Project> tableView;


    @Override
    protected void createTable() {
        table = new DataTable();
        table.createTableView(tableView);
    }

    @Override
    protected void addValuesToComboBox() {
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        cb_currency.setItems(getModel().getCurrencyTypes());
        for (String key : allParams.keySet()) {
            for (ComboBox<String> box : boxes) {
                if (box.getId().contains(key)) {
                    box.setItems(allParams.get(key));
                }
            }
        }
    }

    public void removeParamsFromComboBoxes(ComboBox<String> box_edit) {
        String value = box_edit.getSelectionModel().getSelectedItem();
        box_edit.getItems().remove(value);
    }

    public void deleteProjectFromTable() {
        Project project = getModel().getProjectModel().getProject();
        tableView.getItems().remove(project);
    }

    @Override
    public void clearFields() {
        for (ComboBox<String> box : boxes) {
            box.getSelectionModel().selectFirst();
        }
        dp_date.getEditor().clear();
        clearTextField(tf_description);
    }

    protected void find() {
        List<String> params = new ArrayList<>();
        if (check_to.isSelected()) {
            params.add(cb_to.getSelectionModel().getSelectedItem());
        }
        if (check_from.isSelected()) {
            params.add(cb_from.getSelectionModel().getSelectedItem());
        }
        if (check_cc.isSelected()) {
            params.add(cb_cc.getSelectionModel().getSelectedItem());
        }
        if (check_type.isSelected()) {
            params.add(cb_type.getSelectionModel().getSelectedItem());
        }
        if (check_date.isSelected()) {
            params.add(dp_date.getValue().toString());
        }
        if (check_budget.isSelected()) {
            params.add(cb_budget.getSelectionModel().getSelectedItem());
        }
        if (check_description.isSelected()) {
            params.add(tf_description.getText());
        }
        ObservableList<Project> objects = tableView.getItems();
        for (int i = 0; i < objects.size(); i++) {
            Project project = objects.get(i);
            for (int j = 0; j < params.size(); j++) {

            }
        }
    }

    @Override
    protected void addComboBoxToList() {
        boxes = new ArrayList<>();
        boxes.add(cb_edit_from);
        boxes.add(cb_edit_to);
        boxes.add(cb_edit_cc);
        boxes.add(cb_edit_type);
        boxes.add(cb_edit_budget);
        boxes.add(cb_edit_status);
        boxes.add(cb_edit_category);
        boxes.add(cb_to);
        boxes.add(cb_from);
        boxes.add(cb_currency);
        boxes.add(cb_cc);
        boxes.add(cb_type);
        boxes.add(cb_budget);
    }


}
