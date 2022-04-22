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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;

import static messages.StaticMessage.createErrorAlertDialog;

@Getter
public class Model {
    private static final Logger logger = LoggerFactory.getLogger(Model.class);
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
                                  TextField description, String name_table) {
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
            projectModel.createNewProject(to, from, currency, cc, type, value, budget, amount, desc, name_table);
        } catch (NullPointerException e) {
            createErrorAlertDialog("Ошибка при создании новой записи! Вы должны выбрать все параметры!");
        }

    }

    public void saveNewDataObject(ComboBox<String> cb_to, ComboBox<String> cb_from,
                                  ComboBox<String> cb_relations, ComboBox<String> cb_category,
                                  ComboBox<String> cb_status, String name_table) {
        try
        {
            String to = cb_to.getSelectionModel().getSelectedItem();
            String from = cb_from.getSelectionModel().getSelectedItem();
            String relations = cb_relations.getSelectionModel().getSelectedItem();
            String category = cb_category.getSelectionModel().getSelectedItem();
            String status = cb_status.getSelectionModel().getSelectedItem();
            projectModel.createNewProject(to, from, relations,category,status,name_table);
        }
        catch (NullPointerException e) {
            createErrorAlertDialog("Ошибка при создании новой записи! Вы должны выбрать все параметры!");
        }
    }
    public void saveNewDataObject(ComboBox<String> cb_to, ComboBox<String> cb_from, ComboBox<String> cb_currency,
                                  ComboBox<String> cb_ncc, ComboBox<String> cb_type,
                                  DatePicker dp_date, TextField tf_amount, String name_table) {
        try
        {
            String to = cb_to.getSelectionModel().getSelectedItem();
            String from = cb_from.getSelectionModel().getSelectedItem();
            String currency = cb_currency.getSelectionModel().getSelectedItem();
            String ncc = cb_ncc.getSelectionModel().getSelectedItem();
            String type = cb_type.getSelectionModel().getSelectedItem();
            LocalDate date = dp_date.getValue();
            String amount = tf_amount.getText();
            projectModel.createNewProject(to, from, currency,ncc,type, date, amount, name_table);
        }
        catch (NullPointerException e) {
            createErrorAlertDialog("Ошибка при создании новой записи! Вы должны выбрать все параметры!");
        }
    }

    public ObservableList<Project> getProjectForTable(String name_table) {
        return projectModel.getProjectForTable(name_table);
    }

    public ObservableList<Project> getFilteredProjects()
    {
        return projectModel.getFiltered_list_projects();
    }

    public void setProjectForEdit(Project project) {
        projectModel.setProject(project);
    }


    public void updateProject(Project project) {
       projectModel.updateProject(project);
    }

    public void deleteProject(int index, String name_table) {
        try {
            projectModel.delete(index, name_table);
        }
        catch (Exception e)
        {
            logger.error("Error when delete row {}",index,e);
            createErrorAlertDialog("Ошибка! Вы пытаетесь удалить несуществующую строку!");
        }
    }


    public void saveNewParams(ComboBox<String> box, TextField textField) {
        if (!textField.getText().isEmpty()) {
            String name = getNameFromComboBox(box);
            paramsModel.saveParams(name, textField.getText());
        } else {
            createErrorAlertDialog("При добавлении параметра, введите текст в " + textField.getId());
            throw new NullPointerException("Пустое поле");
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
            createErrorAlertDialog("Ошибка! Пожалуйста, выберите значение из " + comboBox.getPromptText());
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

    public ObservableList<String> getAllTypes()
    {
        return currencyModel.getList_types();
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
        try {
            currencyModel.delete(index);
        }
        catch (Exception e)
        {
            logger.error("Error when delete row {}",index,e);
            createErrorAlertDialog("Ошибка! Вы пытаетесь удалить несуществующую строку!");
        }

    }

    public void createTypeCurrency(String type) {
        if (!type.isEmpty()) {
            currencyModel.createTypeCurrency(type.toUpperCase());
        } else {
            createErrorAlertDialog("Ошибка! При создании нового типа валюты, укажите название этого типа!");
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
            createErrorAlertDialog("Ошибка! Для удаления столбца, выберите его ззначение из выпадающего списка!");
            throw new NullPointerException("Error");
        }
    }

    public void updateCurrencyRelations(Currency currency, Relations relations, String newValue) {
        currencyModel.updateCurrencyRelations(currency, relations, newValue);
    }


    public void getDataByFilters(String from, String to, String currency, String ncc, String type, String relations,
                                 LocalDate date, String budget, String status, String category, String amount, String description, String name_table) {
        projectModel.getDataByFilters(from,to,currency,ncc,type,relations,date,budget, status, category,amount,description,name_table);
    }
}
