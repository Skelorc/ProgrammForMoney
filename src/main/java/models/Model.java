package models;

import entity.Currency;
import entity.Params;
import entity.Project;
import entity.Relations;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

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


    public void saveNewDataObject(ComboBox<String> cb_to, ComboBox<String> cb_from, ComboBox<String> cb_currency, ComboBox<String> cb_cc,
                                  ComboBox<String> cb_type, DatePicker datePicker, ComboBox<String> cb_budget, ComboBox<String> cb_amount,
                                  TextField description,
                                  ComboBox<String> cb_relations, ComboBox<String> cb_category, ComboBox<String> cb_status) {
        try {
            String to = cb_to.getSelectionModel().getSelectedItem();
            String from = cb_from.getSelectionModel().getSelectedItem();
            String currency = cb_currency.getSelectionModel().getSelectedItem();
            String cc = cb_cc.getSelectionModel().getSelectedItem();
            String type = cb_type.getSelectionModel().getSelectedItem();
            LocalDate value = datePicker.getValue();
            String budget = cb_budget.getSelectionModel().getSelectedItem();
            String amount = cb_amount.getSelectionModel().getSelectedItem();
            String desc = description.getText();
            projectModel.createNewProject(to, from, currency, cc, type, value, budget, amount, desc);
            if (cb_relations.getSelectionModel().getSelectedItem() != null) {
                projectModel.getProject().setRelations(cb_relations.getSelectionModel().getSelectedItem());
            }
            if (cb_category.getSelectionModel().getSelectedItem() != null) {
                projectModel.getProject().setCategory(cb_category.getSelectionModel().getSelectedItem());
            }
            if (cb_status.getSelectionModel().getSelectedItem() != null) {
                projectModel.getProject().setStatus(cb_status.getSelectionModel().getSelectedItem());
            }
            projectModel.createNewProject();
        } catch (NullPointerException e) {
            createErrorAlertDialog("Error create new Object! You must select all obligatory params!");
        }

    }

    public List<Project> getAllProjects() {
        return projectModel.getAllData();
    }

    public void setProjectForEdit(Project project) {
        projectModel.setProject(project);
    }


    public void updateProject(ComboBox<String> cb_to, ComboBox<String> cb_from, ComboBox<String> cb_currency, ComboBox<String> cb_cc,
                              ComboBox<String> cb_type, DatePicker dp_date, ComboBox<String> cb_budget, ComboBox<String> cb_amount, TextField tf_description,
                              ComboBox<String> cb_relations, ComboBox<String> cb_category, ComboBox<String> cb_status) {
        Project project = projectModel.getProject();
        if (project != null) {
            project.setTo_project(cb_to.getSelectionModel().getSelectedItem());
            project.setFrom_location(cb_from.getSelectionModel().getSelectedItem());
            project.setCurrency(cb_currency.getSelectionModel().getSelectedItem());
            project.setNCC(cb_cc.getSelectionModel().getSelectedItem());
            project.setType(cb_type.getSelectionModel().getSelectedItem());
            project.setDate(dp_date.getValue());
            project.setBudget(cb_budget.getSelectionModel().getSelectedItem());
            project.setAmount(cb_amount.getSelectionModel().getSelectedItem());
            project.setDescription(tf_description.getText());
            project.setRelations(cb_relations.getSelectionModel().getSelectedItem());
            project.setCategory(cb_category.getSelectionModel().getSelectedItem());
            project.setStatus(cb_status.getSelectionModel().getSelectedItem());
            projectModel.updateProject(project);
        } else {
            createErrorAlertDialog("You must choice row in the table for update!");
            throw new NullPointerException();
        }
    }

    public void deleteProject() {
        projectModel.delete();
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

    public List<Params> getAllParams() {
        return paramsModel.getAllParams();
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

    public ObservableList<Currency> getCurrencyList() {
        return currencyModel.getList_currency();
    }

    public Currency getCurrency() {
        return currencyModel.getCurrency();
    }

    public void createCurrency(String type) {


    }

    public void setCurrencyForEdit(Currency currencyForEdit) {
        currencyModel.setCurrency(currencyForEdit);
    }


    private String getNameFromComboBox(ComboBox<String> comboBox) {
        String id = comboBox.getId();
        int start_name = id.lastIndexOf("-") + 1;
        return id.substring(start_name);
    }

    public ObservableList<String> getCurrencyTypes() {
        return currencyModel.getList_types();
    }

    public ObservableList<String> getColumns()
    {
        return currencyModel.getList_name_columns();
    }


    public void addRelations(String first_value, String second_value) throws Exception {
        if (first_value != null && second_value != null && !first_value.equals(second_value)) {
                currencyModel.addNewRelations(first_value, second_value);
        } else {
            throw new NullPointerException("Error");
        }

    }

    public Relations getRelations()
    {
       return currencyModel.getRelations();
    }

    public void deleteCurrency(Currency currency) {
        currencyModel.delete(currency);
    }

    public void createTypeCurrency(String type)
    {
        if (!type.isEmpty()) {
            currencyModel.createTypeCurrency(type.toUpperCase());
        } else {
            createErrorAlertDialog("Please, for create new type write type currency!");
            throw new NullPointerException("Error");
        }
    }

    public void removeTypeCurrency(String selectedItem) {
        if(selectedItem != null)
        {
            currencyModel.removeTypeCurrency(selectedItem);
        }
    }

    public void addNewCurrencyToList(Currency currency) {
        currencyModel.addCurrencyToList(currency);
    }

    public void updateCurrency(Currency currency) {
        currencyModel.updateCurrency(currency);
    }

    public void removeRelations(String first_value, String second_value) {
        if (first_value != null && second_value != null && !first_value.equals(second_value)) {
            currencyModel.deleteRelations(first_value+"/"+second_value);
        } else {
            throw new NullPointerException("Error");
        }
    }
}
