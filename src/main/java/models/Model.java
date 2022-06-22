package models;

import dto.Transaction;
import entity.Currency;
import entity.Project;
import entity.Relations;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.controlsfx.control.CheckComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static messages.StaticMessage.createErrorAlertDialog;
import static textConst.StringConst.*;

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
    private final TransactionModel transactionModel = new TransactionModel();


    /*Tab Data*/
    public void saveNewProject(ComboBox<String> cb_to, ComboBox<String> cb_from, ComboBox<String> cb_currency, ComboBox<String> cb_ncc,
                               ComboBox<String> cb_type, TextField tf_date,
                               ComboBox<String> cb_relations, ComboBox<String> cb_category,
                               ComboBox<String> cb_status, ComboBox<String> cb_budget, TextField tf_amount,
                               TextField tf_description, String name_table) {
        String to = checkComboBoxValue(cb_to);
        String from = checkComboBoxValue(cb_from);
        String currency = checkComboBoxValue(cb_currency);
        String ncc = checkComboBoxValue(cb_ncc);
        String type = checkComboBoxValue(cb_type);
        LocalDate date = createDateFromString(tf_date);
        String relations = checkComboBoxValue(cb_relations);
        String category = checkComboBoxValue(cb_category);
        String status = checkComboBoxValue(cb_status);
        String budget = checkComboBoxValue(cb_budget);
        String amount = checkTextField(tf_amount);
        String desc = checkTextField(tf_description);
        try {
            projectModel.createNewProject(to, from, currency, ncc, type, date, relations, category, status, budget, amount, desc, name_table);
        }
        catch (NullPointerException exception)
        {
            String message = exception.getMessage();
            String null_field = message.substring(0,message.indexOf(" "));
            createErrorAlertDialog("Ошибка заполнения данных! Проверьте поле "+ null_field +"!");
        }
    }

    public ObservableList<Project> getProjectForTable(String name_table) {
        return projectModel.getListForTable(name_table);
    }

    public ObservableList<Project> getFilteredProjects() {
        return projectModel.getFiltered_list_projects();
    }

    public void updateProject(Project project) {
        projectModel.updateProject(project);
    }

    public void deleteProject(int index, String name_table) {
        try {
            projectModel.delete(index, name_table);
        } catch (Exception e) {
            logger.error("Error when delete row {}", index, e);
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

    public void setCurrencyForEdit(Currency currencyForEdit) {
        currencyModel.setCurrency(currencyForEdit);
    }

    public ObservableList<String> getCurrencyTypes() {
        return currencyModel.getList_types();
    }

    public ObservableList<String> getColumnsByCurrency() {
        return currencyModel.getList_name_columns();
    }

    public ObservableList<String> getAllTypes() {
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
        } catch (Exception e) {
            logger.error("Error when delete row {}", index, e);
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


    public void getDataByFilters(CheckComboBox<String> ccb_from, CheckComboBox<String> ccb_to,
                                 CheckComboBox<String> ccb_currency, CheckComboBox<String> ccb_ncc,
                                 CheckComboBox<String> ccb_type, CheckComboBox<String> ccb_relations,
                                 DatePicker date, CheckComboBox<String> ccb_budget, CheckComboBox<String> ccb_status,
                                 CheckComboBox<String> ccb_category, TextField amount, TextField description, String name_table) {
        HashMap<String, List<String>> param_map = new HashMap<>();
        if (ccb_from != null) {
            param_map.put(ccb_from.getTitle(), ccb_from.getCheckModel().getCheckedItems());
        }
        if (ccb_to != null) {
            param_map.put(ccb_to.getTitle(), ccb_to.getCheckModel().getCheckedItems());
        }
        if (ccb_currency != null) {
            param_map.put(ccb_currency.getTitle(), ccb_currency.getCheckModel().getCheckedItems());
        }
        if (ccb_ncc != null) {
            param_map.put(ccb_ncc.getTitle(), ccb_ncc.getCheckModel().getCheckedItems());
        }
        if (ccb_type != null) {
            param_map.put(ccb_type.getTitle(), ccb_type.getCheckModel().getCheckedItems());
        }
        if (ccb_relations != null) {
            param_map.put(ccb_relations.getTitle(), ccb_relations.getCheckModel().getCheckedItems());
        }
        if (date != null && date.getValue() != null) {
            List<String> list = new ArrayList<>();
            list.add(date.getValue().toString());
            param_map.put(DATE, list);
        }
        if (ccb_budget != null) {
            param_map.put(ccb_budget.getTitle(), ccb_budget.getCheckModel().getCheckedItems());
        }
        if (ccb_status != null) {
            param_map.put(ccb_status.getTitle(), ccb_status.getCheckModel().getCheckedItems());
        }
        if (ccb_category != null) {
            param_map.put(ccb_category.getTitle(), ccb_category.getCheckModel().getCheckedItems());
        }
        if (amount != null && !amount.getText().isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add(amount.getText());
            param_map.put(AMOUNT, list);
        }
        if (description != null && !description.getText().isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add(description.getText());
            param_map.put(DESCRIPTION, list);
        }
        projectModel.getDataByFilters(param_map, name_table);
    }

    public void addProjectsFromExcel(List<Project> list) {
        projectModel.addListProjects(list);
    }

    public void addCurrencyFromExcel(List<Currency> list) {
        currencyModel.addListCurrencyFromExcel(list);
    }

    public void checkParams(String params, String value) {
        boolean b = paramsModel.checkParams(params, value);
        if (!b) {
            createErrorAlertDialog("Ошибка импорта данных! В программе не существуют параметр " + params + " или значение " + value + "." +
                    " Для устранения ошибки, внесите данные параметры в программу, и попробуйте импортировать снова!");
            throw new IllegalArgumentException("Ошибка добавления информации с файла! Не существуют параметры!");
        }
    }

    /*Cash*/



    public void createMapProjectsForCash(LocalDate report_date, LocalDate scale_Date,String currency) {
        transactionModel.createMap(report_date,scale_Date,currency);
    }

    public List<Transaction> getResultListForCash() {
        return transactionModel.getResult_list();
    }

    public List<Transaction> getNodesListForCash()
    {
        return transactionModel.getNodes_list();
    }


    /*Utils*/
    private String checkComboBoxValue(ComboBox<String> box) {
        if (box != null) {
            return box.getSelectionModel().getSelectedItem();
        } else
            return "";
    }

    private String checkTextField(TextField textField) {
        if (textField != null) {
            return textField.getText();
        } else
            return "";
    }


    public LocalDate createDateFromString(TextField date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d", Locale.US);
        if (date != null) {
            String dateText = date.getText();
            if (dateText.contains("."))
                dateText = dateText.replaceAll("\\.", "-");
            if (dateText.contains(","))
                dateText = dateText.replaceAll(",", "-");
            if (dateText.contains("/"))
                dateText = dateText.replaceAll("/", "-");
            String[] split = dateText.split("-");
            if (split[0].length() == 2 && split[1].length() == 4) {
                String date_result = split[1] + "-" + split[0] + "-01";
                LocalDate localDate = LocalDate.parse(date_result, formatter);
                return localDate;
            } else {
                createErrorAlertDialog("Ошибка при указании даты! Введите дату в следующем формате: месяц-год. (01.2022).");
                throw new IllegalArgumentException("Ошибка при указании даты! Введите дату в следующем формате: месяц.год (Например: 01.2022).");
            }
        }
        else
        {
            return LocalDate.parse(LocalDate.now().getYear()+"-"+LocalDate.now().getMonthValue()+"-01", formatter);
        }


    }
}
