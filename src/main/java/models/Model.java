package models;

import entity.Currency;
import entity.Project;
import entity.Relations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

import static messages.StaticMessage.createErrorAlertDialog;

@Getter
public class Model {

    private static Model instance = new Model();

    private Model() {
    }

    public static Model getModel() {
        if (instance == null)
            return instance = new Model();
        else
            return instance;
    }

    private final ProjectModel projectModel = new ProjectModel();
    private final ParamsModel paramsModel = new ParamsModel();
    private final CurrencyModel currencyModel = new CurrencyModel();


    /*Tab Data*/
    public void saveNewDataObject(ComboBox<String> cb_to, ComboBox<String> cb_from, ComboBox<String> cb_currency, ComboBox<String> cb_cc,
                                  ComboBox<String> cb_type, DatePicker datePicker, ComboBox<String> cb_budget, TextField tf_amount,
                                  TextField description) {
        try {
            String to = cb_to.getSelectionModel().getSelectedItem();
            String from = cb_from.getSelectionModel().getSelectedItem();
            String currency = cb_currency.getSelectionModel().getSelectedItem();
            String cc = cb_cc.getSelectionModel().getSelectedItem();
            String type = cb_type.getSelectionModel().getSelectedItem();
            LocalDate value = datePicker.getValue();
            String budget = cb_budget.getSelectionModel().getSelectedItem();
            String amount = tf_amount.getText();
            String desc = description.getText();
            projectModel.createNewProject(to, from, currency, cc, type, value, budget, amount, desc);
        } catch (NullPointerException e) {
            createErrorAlertDialog("Error create new Object! You must select all obligatory params!");
        }

    }

    public void saveNewDataObject(ComboBox<String> cb_to, ComboBox<String> cb_from,
                                  ComboBox<String> cb_relations, ComboBox<String> cb_category,
                                  ComboBox<String> cb_status) {
        try
        {
            String to = cb_to.getSelectionModel().getSelectedItem();
            String from = cb_from.getSelectionModel().getSelectedItem();
            String relations = cb_relations.getSelectionModel().getSelectedItem();
            String category = cb_category.getSelectionModel().getSelectedItem();
            String status = cb_status.getSelectionModel().getSelectedItem();
            projectModel.createNewProject(to, from, relations,category,status);
        }
        catch (NullPointerException e) {
            createErrorAlertDialog("Error create new Object! You must select all obligatory params!");
        }
    }


    public ObservableList<Project> getAllProjects() {
        FXCollections.observableArrayList(projectModel.getAllData());
        return projectModel.getAllData();
    }

    public void setProjectForEdit(Project project) {
        projectModel.setProject(project);
    }


    public void updateProject(Project project) {
       projectModel.updateProject(project);
    }

    public void deleteProject(int index) {
        projectModel.delete(index);
    }


    public void saveNewParams(ComboBox<String> box, TextField textField) {
        if (!textField.getText().isEmpty()) {
            String name = getNameFromComboBox(box);
            paramsModel.saveParams(name, textField.getText());
        } else {
            createErrorAlertDialog("Empty fields - " + textField.getId());
            throw new NullPointerException("Empty Fields!");
        }
    }

    public ObservableList<String> getParamsByWord(String word) {
        return paramsModel.getParamsFromWord(word);
    }

    public Map<String, ObservableList<String>> getAllParams() {
        return paramsModel.getMap_params();
    }

    public ObservableList<String> getParamsFromCombobox() {
        return paramsModel.getList_for_box();
    }

    public void removeParams(ComboBox<String> comboBox) {
        String name_params = getNameFromComboBox(comboBox);
        String value = comboBox.getSelectionModel().getSelectedItem();
        try {
            paramsModel.deleteParams(name_params, value);
        } catch (NullPointerException e) {
            createErrorAlertDialog("Error! Please, set value in " + comboBox.getPromptText());
        }
    }

    private String getNameFromComboBox(ComboBox<String> comboBox) {
        String id = comboBox.getId();
        int start_name = id.lastIndexOf("_") + 1;
        return id.substring(start_name);
    }

    /*Tab Currency*/

    public ObservableList<Currency> getCurrencyList() {
        return currencyModel.getList_currency();
    }

    public Currency getCurrency() {
        return currencyModel.getCurrency();
    }

    public void setCurrencyForEdit(Currency currencyForEdit) {
        currencyModel.setCurrency(currencyForEdit);
    }

    public ObservableList<String> getCurrencyTypes() {
        return currencyModel.getList_types();
    }

    public ObservableList<String> getColumns() {
        return currencyModel.getList_name_columns();
    }


    public void addRelations(String first_value, String second_value) throws Exception {
        if (first_value != null && second_value != null && !first_value.equals(second_value)) {
            currencyModel.addNewRelations(first_value, second_value);
        } else {
            throw new NullPointerException("Error");
        }

    }

    public Relations getRelations() {
        return currencyModel.getRelations();
    }

    public void deleteCurrency(int index) {
        currencyModel.delete(index);
    }

    public void createTypeCurrency(String type) {
        if (!type.isEmpty()) {
            currencyModel.createTypeCurrency(type.toUpperCase());
        } else {
            createErrorAlertDialog("Please, for create new type write type currency!");
            throw new NullPointerException("Error");
        }
    }

    public void removeTypeCurrency(String selectedItem) {
        if (selectedItem != null) {
            currencyModel.removeTypeCurrency(selectedItem);
        }
    }

    public void addNewCurrencyToList(Currency currency) {
        currencyModel.addCurrencyToList(currency);
    }

    public void updateCurrencyDate(Currency currency, LocalDate localDate) {
        currencyModel.updateCurrencyDate(currency, localDate);
    }

    public void removeRelations(String first_value, String second_value) {
        if (first_value != null && second_value != null && !first_value.equals(second_value)) {
            currencyModel.deleteRelations(first_value + "/" + second_value);
        } else {
            createErrorAlertDialog("Please, for delete choice name column!");
            throw new NullPointerException("Error");
        }
    }

    public void updateCurrencyRelations(Currency currency, Relations relations, String newValue) {
        currencyModel.updateCurrencyRelations(currency, relations, newValue);
    }


}
