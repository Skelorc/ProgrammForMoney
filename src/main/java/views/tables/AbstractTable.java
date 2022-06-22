package views.tables;

import entity.BaseEntity;
import entity.Currency;
import entity.Project;
import entity.Relations;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import static messages.StaticMessage.createErrorAlertDialog;
import static models.Model.getModel;
import static textConst.StringConst.*;

abstract class AbstractTable implements Table {

    protected ObservableList<TableColumn> list_columns = FXCollections.observableList(new ArrayList<>());

    public final void createTableView(TableView tableView) {
        tableView.setEditable(true);
        addColumnsToList();
        createColumns();
        addDataToTable(tableView);
    }
    protected void createColumns()
    {
        Iterator<TableColumn> iterator = list_columns.iterator();
        while (iterator.hasNext()) {
            TableColumn<BaseEntity, String> column = iterator.next();
            configColumns(column, column.getText());
        }
    }

    protected void configColumns(TableColumn<BaseEntity, String> column, String value)
    {

        switch (value)
        {
            case NAME_COLUMN_FROM:
                column.setCellValueFactory(new PropertyValueFactory<>(FROM));
                ObservableList<String> from = getModel().getParamsByWord(FROM);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(from));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setFrom(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_TO:
                column.setCellValueFactory(new PropertyValueFactory<>(TO));
                ObservableList<String> to = getModel().getParamsByWord(TO);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(to));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setTo(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_RELATIONS:
                column.setCellValueFactory(new PropertyValueFactory<>(RELATIONS));
                ObservableList<String> relations = getModel().getParamsByWord(RELATIONS);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(relations));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setRelations(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_CATEGORY:
                column.setCellValueFactory(new PropertyValueFactory<>(CATEGORY));
                ObservableList<String> category = getModel().getParamsByWord(CATEGORY);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(category));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setCategory(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_STATUS:
                column.setCellValueFactory(new PropertyValueFactory<>(STATUS));
                ObservableList<String> status = getModel().getParamsByWord(STATUS);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(status));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setStatus(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_CURRENCY:
                column.setCellValueFactory(new PropertyValueFactory<>(CURRENCY));
                ObservableList<String> types_currency = getModel().getAllTypes();
                column.setCellFactory(ComboBoxTableCell.forTableColumn(types_currency));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setCurrency(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_NCC:
                column.setCellValueFactory(new PropertyValueFactory<>(NAME_COLUMN_NCC));
                ObservableList<String> ncc = getModel().getParamsByWord(NCC);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(ncc));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setNCC(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_TYPE:
                column.setCellValueFactory(new PropertyValueFactory<>(TYPE));
                ObservableList<String> types = getModel().getParamsByWord(TYPE);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(types));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setType(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_AMOUNT:
                column.setCellValueFactory(param -> {
                    Project project = (Project) param.getValue();
                    int amount = project.getAmount();
                    return new SimpleObjectProperty<>(String.valueOf(amount));
                });
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setAmount(Integer.parseInt(newValue));
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_DATE:
                column.setCellValueFactory(param -> {
                    Project project = (Project)param.getValue();
                    LocalDate date = project.getDate();
                    if (date == null)
                        return new SimpleObjectProperty<>("");
                    return new SimpleObjectProperty<>(date.toString());
                });
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(param -> {
                    try {
                        String newValue = param.getNewValue();
                        int row_index = param.getTablePosition().getRow();
                        Project project = (Project) param.getTableView().getItems().get(row_index);
                        LocalDate localDate;
                        localDate = LocalDate.parse(newValue);
                        project.setDate(localDate);
                        getModel().updateProject(project);
                    } catch (DateTimeParseException e) {
                        createErrorAlertDialog("Please, write new date next format : YEAR-MONTH-DAY ");
                        throw new IllegalArgumentException("Error");
                    }
                });
                break;
            case NAME_COLUMN_BUDGET:
                column.setCellValueFactory(new PropertyValueFactory<>(BUDGET));
                ObservableList<String> budget = getModel().getParamsByWord(BUDGET);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(budget));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setBudget(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_DESCRIPTION:
                column.setCellValueFactory(new PropertyValueFactory<>(DESCRIPTION));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setDescription(newValue);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_DATE_:
                column.setCellValueFactory(param -> {
                    Currency currency = (Currency)param.getValue();
                    LocalDate date = currency.getDate();
                    if (date == null)
                        return new SimpleObjectProperty<>("");
                    return new SimpleObjectProperty<>(date.toString());
                });
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(param -> {
                    String newValue = param.getNewValue();
                    int row_index = param.getTablePosition().getRow();
                    Currency currency = (Currency) param.getTableView().getItems().get(row_index);
                    LocalDate localDate;
                    try {
                        localDate = LocalDate.parse(newValue);
                        getModel().updateCurrencyDate(currency,localDate);
                        param.getTableView().refresh();
                    } catch (DateTimeParseException e) {
                        createErrorAlertDialog("Пожалуйста, введите дату в следующем формате : ГОД-МЕСЯЦ-ДЕНЬ ");
                    }
                });
                break;
            case NAME_COLUMN_MONTH:
                column.setCellValueFactory(new PropertyValueFactory<>(MONTH));
                break;
            case NAME_COLUMN_MONTH_:
                column.setCellValueFactory(new PropertyValueFactory<>(MONTH));
                ArrayList<String> months_list = new ArrayList<>();
                for (int i = 1; i < 13; i++) {
                    months_list.add(String.valueOf(i));
                }
                ObservableList<String> months = FXCollections.observableArrayList(months_list);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(months));
                column.setOnEditCommit(param -> {
                    String newValue = param.getNewValue();
                    int row_index = param.getTablePosition().getRow();
                    Project project = (Project) param.getTableView().getItems().get(row_index);
                    LocalDate localDate = LocalDate.now();
                    String date = localDate.getYear()+"-"+newValue+"-01";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d", Locale.US);
                    localDate = LocalDate.parse(date,formatter);
                    project.setDate(localDate);
                    getModel().updateProject(project);
                });
                break;
            case NAME_COLUMN_YEAR:
                column.setCellValueFactory(new PropertyValueFactory<>(YEAR));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(param -> {
                    String newYear = param.getNewValue();
                    Project project = (Project) param.getTableView().getSelectionModel().getSelectedItem();
                    int month = project.getDate().getMonth().getValue();
                    String date_str = newYear+"-"+month+"-01";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d", Locale.US);
                    LocalDate date = LocalDate.parse(date_str,formatter);
                    project.setDate(date);
                    getModel().updateProject(project);
                });
                break;
            default:
                column.setCellValueFactory(param -> {
                    Currency currency = (Currency)param.getValue();
                    Set<Relations> relations_list = currency.getRelations_list();
                    Relations relations_currency = relations_list.stream().filter(x -> x.getRelation().equals(column.getText())).findAny().orElse(null);
                    return new SimpleObjectProperty<>(relations_currency.getValue_relation());
                });
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(event -> {
                            Currency currency = (Currency) event.getTableView().getItems().get(event.getTablePosition().getRow());
                            Set<Relations> relations_list = currency.getRelations_list();
                            Relations relations_currency = relations_list.stream().filter(x -> x.getRelation().equals(event.getTablePosition().getTableColumn().getText())).findAny().orElse(null);
                            String newValue = event.getNewValue();
                            getModel().updateCurrencyRelations(currency,relations_currency,newValue);
                        }
                );

                break;
        }
    }

    protected void clearDataInTable(TableView<Project> table) {
        table.getItems().clear();
        table.getColumns().clear();
    }


    protected void addDataToTable(TableView tableView)
    {
        tableView.setItems(getModel().getProjectForTable(tableView.getId()));
        tableView.getColumns().setAll(list_columns);
    }
    protected abstract void addColumnsToList();
}
