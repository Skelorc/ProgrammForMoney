package views;

import entity.DataObject;
import entity.Params;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import models.Model;
import models.ParamsModel;
import views.tables.TableData;

import java.util.*;
import java.util.stream.Collectors;


@Getter
public class DataView implements View {

    private List<ComboBox<String>> list_combobox;
    private List<Button> list_buttons;

    protected TableData tableData;
    private Model model;

    @FXML
    protected TextField tf_from, tf_to, tf_currency, tf_cc, tf_type,
            tf_budget, tf_amount, tf_description, tf_relations, tf_category, tf_status;
    @FXML
    protected ComboBox<String> cb_from, cb_to, cb_currency, cb_cc, cb_type, cb_budget, cb_amount, cb_relations,cb_category,cb_status,
            cb_edit_from, cb_edit_to, cb_edit_currency, cb_edit_cc, cb_edit_type, cb_edit_budget, cb_edit_amount, cb_edit_relations,cb_edit_status,cb_edit_category;

    @FXML
    protected Button btn_add_from, btn_add_to, btn_add_currency, btn_add_cc, btn_add_type, btn_add_budget, btn_add_amount,btn_add_relations,btn_add_category,btn_add_status,
            btn_remove_from, btn_remove_to, btn_remove_currency, btn_remove_cc, btn_remove_type, btn_remove_budget, btn_remove_amount,btn_remove_relations,btn_remove_category,btn_remove_status,
            btn_create, btn_clear_fields, btn_find, btn_delete;

    @FXML
    protected DatePicker dp_date;
    @FXML
    protected TableView<DataObject> table_data;

    @Override
    public void initData() {
        model = new Model();
        createTable();
        addComboBoxToList();
        addValuesToComboBox();
        addButtonsToList();
    }

    public void addDataToTable(DataObject dataObject) {
        tableData.addDataObjectToList(dataObject);
    }

    private void createTable() {
        tableData = new TableData(model);
        tableData.createTableView(table_data);
    }

    protected void addDataToComboBoxesAndClearTextField(ComboBox<String> combobox_table, ComboBox<String> comboBox_edit, TextField textField) {
        Params params = model.getParamsByWord(comboBox_edit.getId());
        Set<String> values = params.getValues();
        ObservableList<String> list_params = FXCollections.observableArrayList(values);
        combobox_table.getItems().clear();
        combobox_table.setItems(list_params);
        comboBox_edit.getItems().clear();
        comboBox_edit.setItems(list_params);
        clearTextField(textField);
    }

    private void addValuesToComboBox()
    {
        List<Params> allValues = model.getAllParams();
        for (Params params : allValues) {
            String name = params.getName();
            for (int i = 0; i < list_combobox.size(); i++) {
                ComboBox<String> comboBox = list_combobox.get(i);
                if(comboBox.getId().contains(name))
                {
                    comboBox.getItems().addAll(FXCollections.observableArrayList(params.getValues()));
                }
            }
        }
    }

    public void removeParamsFromComboBoxes(ComboBox<String> box_edit, ComboBox<String> box_create)
    {
        String value = box_edit.getSelectionModel().getSelectedItem();
        box_edit.getItems().remove(value);
        box_create.getItems().remove(value);
    }

    private void addComboBoxToList()
    {
        list_combobox = new ArrayList<>();
        list_combobox.add(cb_edit_from);
        list_combobox.add(cb_edit_to);
        list_combobox.add(cb_edit_currency);
        list_combobox.add(cb_edit_cc);
        list_combobox.add(cb_edit_type);
        list_combobox.add(cb_edit_budget);
        list_combobox.add(cb_edit_amount);
        list_combobox.add(cb_edit_category);
        list_combobox.add(cb_edit_relations);
        list_combobox.add(cb_edit_status);
        list_combobox.add(cb_to);
        list_combobox.add(cb_from);
        list_combobox.add(cb_currency);
        list_combobox.add(cb_cc);
        list_combobox.add(cb_type);
        list_combobox.add(cb_budget);
        list_combobox.add(cb_amount);
        list_combobox.add(cb_category);
        list_combobox.add(cb_relations);
        list_combobox.add(cb_status);
    }

    private void addButtonsToList()
    {
        list_buttons = new ArrayList<>();
        list_buttons.add(btn_add_from);
        list_buttons.add(btn_add_to);
        list_buttons.add(btn_add_currency);
        list_buttons.add(btn_add_cc);
        list_buttons.add(btn_add_type);
        list_buttons.add(btn_add_budget);
        list_buttons.add(btn_add_amount);
        list_buttons.add(btn_add_relations);
        list_buttons.add(btn_add_category);
        list_buttons.add(btn_add_status);
        list_buttons.add(btn_remove_from);
        list_buttons.add(btn_remove_to);
        list_buttons.add(btn_remove_currency);
        list_buttons.add(btn_remove_cc);
        list_buttons.add(btn_remove_budget);
        list_buttons.add(btn_remove_amount);
        list_buttons.add(btn_remove_relations);
        list_buttons.add(btn_remove_category);
        list_buttons.add(btn_remove_status);
    }

    protected void clearTextField(TextField textfield)
    {
        textfield.clear();
    }
}
