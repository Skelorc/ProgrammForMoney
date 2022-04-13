package views;

import entity.Params;
import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import views.tables.ProjectsTable;

import java.util.*;

import static models.Model.getModel;


@Getter
public class ProjectsView extends AbstractView{

    private Map<String, List<ComboBox<String>>> boxes;

    @FXML
    protected TextField tf_from, tf_to, tf_currency, tf_cc, tf_type,
            tf_budget, tf_amount, tf_description, tf_relations, tf_category, tf_status;
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_currency, cb_cc, cb_type, cb_budget, cb_amount, cb_relations, cb_category, cb_status,
            cb_edit_from, cb_edit_to, cb_edit_cc, cb_edit_type, cb_edit_budget, cb_edit_amount, cb_edit_relations, cb_edit_status, cb_edit_category;

    @FXML
    protected Button btn_add_from, btn_add_to, btn_add_cc, btn_add_type, btn_add_budget, btn_add_amount, btn_add_relations, btn_add_category, btn_add_status,
            btn_remove_from, btn_remove_to, btn_remove_cc, btn_remove_type, btn_remove_budget, btn_remove_amount, btn_remove_relations, btn_remove_category, btn_remove_status,
            btn_create, btn_update, btn_clear_fields, btn_find, btn_delete;

    @FXML
    private CheckBox check_to, check_from, check_cc, check_type, check_date, check_budget, check_amount, check_description;

    @FXML
    protected DatePicker dp_date;
    @FXML
    protected TableView<Project> tableView;


    @Override
    protected void createTable() {
        table = new ProjectsTable();
        table.createTableView(tableView);
    }

    protected void addDataToComboBoxesAndClearTextField(ComboBox<String> combobox_table, ComboBox<String> comboBox_edit, TextField textField) {
        ObservableList<String> params = getModel().getParamsByWord(comboBox_edit.getId());
        combobox_table.getItems().clear();
        combobox_table.setItems(params);
        comboBox_edit.getItems().clear();
        comboBox_edit.setItems(params);
        clearTextField(textField);
    }

    @Override
    protected void addValuesToComboBox() {
        List<Params> allValues = getModel().getAllParams();
        for (Params params : allValues) {
            String name_from_db = params.getName();
            int start_name = name_from_db.lastIndexOf("_")+1;
            String real_name = name_from_db.substring(start_name);
            Collection<List<ComboBox<String>>> values = boxes.values();
            for (List<ComboBox<String>> next : values) {
                for (ComboBox<String> box : next) {
                    if (box.getId().contains(real_name)) {
                        box.getItems().addAll(FXCollections.observableArrayList(params.getValues()));
                    }
                }
            }
        }
    }

    public void removeParamsFromComboBoxes(ComboBox<String> box_edit, ComboBox<String> box_create) {
        String value = box_edit.getSelectionModel().getSelectedItem();
        box_edit.getItems().remove(value);
        box_create.getItems().remove(value);
    }

   /* @Override
    protected void addListenerToTable() {
        tableView.setOnMouseClicked(event -> {
            Project selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                cb_to.getSelectionModel().select(selectedItem.getTo_project());
                cb_from.getSelectionModel().select(selectedItem.getFrom_location());
                cb_currency.getSelectionModel().select(selectedItem.getCurrency());
                cb_cc.getSelectionModel().select(selectedItem.getNCC());
                cb_type.getSelectionModel().select(selectedItem.getType());
                dp_date.setValue(selectedItem.getDate());
                cb_budget.getSelectionModel().select(selectedItem.getBudget());
                cb_amount.getSelectionModel().select(selectedItem.getAmount());
                tf_description.setText(selectedItem.getDescription());
                cb_relations.getSelectionModel().select(selectedItem.getRelations());
                cb_category.getSelectionModel().select(selectedItem.getCategory());
                cb_status.getSelectionModel().select(selectedItem.getStatus());
                getModel().setProjectForEdit(selectedItem);
            }
        });
    }*/


    public void deleteProjectFromTable()
    {
        Project project = getModel().getProjectModel().getProject();
        tableView.getItems().remove(project);
    }

    @Override
    public void clearFields() {
        Collection<List<ComboBox<String>>> values = boxes.values();
        for (List<ComboBox<String>> next : values) {
            for (ComboBox<String> box : next) {
               box.getSelectionModel().selectFirst();
            }
        }
        dp_date.getEditor().clear();
        clearTextField(tf_description);
    }

    protected void find()
    {
        List<String> params = new ArrayList<>();
        if(check_to.isSelected())
        {
            params.add(cb_to.getSelectionModel().getSelectedItem());
        }
        if(check_from.isSelected())
        {
            params.add(cb_from.getSelectionModel().getSelectedItem());
        }
        if(check_cc.isSelected())
        {
            params.add(cb_cc.getSelectionModel().getSelectedItem());
        }
        if(check_type.isSelected())
        {
            params.add(cb_type.getSelectionModel().getSelectedItem());
        }
        if(check_date.isSelected())
        {
            params.add(dp_date.getValue().toString());
        }
        if(check_budget.isSelected())
        {
            params.add(cb_budget.getSelectionModel().getSelectedItem());
        }
        if(check_amount.isSelected())
        {
            params.add(cb_amount.getSelectionModel().getSelectedItem());
        }
        if(check_description.isSelected())
        {
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
        List<ComboBox<String>> list_combobox = new ArrayList<>();
        boxes = new TreeMap<>();
        list_combobox.add(cb_edit_from);
        list_combobox.add(cb_edit_to);
        list_combobox.add(cb_edit_cc);
        list_combobox.add(cb_edit_type);
        list_combobox.add(cb_edit_budget);
        list_combobox.add(cb_edit_amount);
        list_combobox.add(cb_edit_category);
        list_combobox.add(cb_edit_relations);
        list_combobox.add(cb_edit_status);
        boxes.put("edit",list_combobox);
        list_combobox = new ArrayList<>();
        list_combobox.add(cb_to);
        list_combobox.add(cb_from);
        list_combobox.add(cb_cc);
        list_combobox.add(cb_type);
        list_combobox.add(cb_budget);
        list_combobox.add(cb_amount);
        list_combobox.add(cb_category);
        list_combobox.add(cb_relations);
        list_combobox.add(cb_status);
        boxes.put("create",list_combobox);
    }



}
