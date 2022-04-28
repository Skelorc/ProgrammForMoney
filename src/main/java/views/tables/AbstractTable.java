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
            case "From Location":
                column.setCellValueFactory(new PropertyValueFactory<>("from"));
                ObservableList<String> from = getModel().getParamsByWord("from");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(from));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setFrom(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "To Project":
                column.setCellValueFactory(new PropertyValueFactory<>("to"));
                ObservableList<String> to = getModel().getParamsByWord("to");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(to));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setTo(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Relations":
                column.setCellValueFactory(new PropertyValueFactory<>("relations"));
                ObservableList<String> relations = getModel().getParamsByWord("relations");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(relations));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setRelations(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Category":
                column.setCellValueFactory(new PropertyValueFactory<>("category"));
                ObservableList<String> category = getModel().getParamsByWord("category");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(category));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setCategory(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Status":
                column.setCellValueFactory(new PropertyValueFactory<>("status"));
                ObservableList<String> status = getModel().getParamsByWord("status");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(status));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setStatus(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Currency":
                column.setCellValueFactory(new PropertyValueFactory<>("currency"));
                ObservableList<String> types_currency = getModel().getAllTypes();
                column.setCellFactory(ComboBoxTableCell.forTableColumn(types_currency));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setCurrency(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "NCC":
                column.setCellValueFactory(new PropertyValueFactory<>("NCC"));
                ObservableList<String> ncc = getModel().getParamsByWord("ncc");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(ncc));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setNCC(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Type":
                column.setCellValueFactory(new PropertyValueFactory<>("type"));
                ObservableList<String> types = getModel().getParamsByWord("type");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(types));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setType(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Amount":
                column.setCellValueFactory(new PropertyValueFactory<>("amount"));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setAmount(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Date":
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
            case "Budget":
                column.setCellValueFactory(new PropertyValueFactory<>("budget"));
                ObservableList<String> budget = getModel().getParamsByWord("budget");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(budget));
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setBudget(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Description":
                column.setCellValueFactory(new PropertyValueFactory<>("description"));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(event -> {
                    Project project = (Project) event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setDescription(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Date ":
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
            case "Month":
                column.setCellValueFactory(new PropertyValueFactory<>("month"));
                break;
            case "Month ":
                column.setCellValueFactory(new PropertyValueFactory<>("month"));
                ArrayList<String> months_list = new ArrayList<>();
                for (int i = 1; i < 13; i++) {
                    if(i<=10)
                    months_list.add(String.valueOf(i));
                }
                ObservableList<String> months = FXCollections.observableArrayList(months_list);
                column.setCellFactory(ComboBoxTableCell.forTableColumn(months));
                column.setOnEditCommit(param -> {
                    String newValue = param.getNewValue();
                    int row_index = param.getTablePosition().getRow();
                    Project project = (Project) param.getTableView().getItems().get(row_index);
                    LocalDate localDate = LocalDate.now();
                    String date = localDate.getYear()+"-"+newValue+"-"+localDate.getDayOfMonth();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-dd", Locale.US);
                    localDate = LocalDate.parse(date,formatter);
                    project.setDate(localDate);
                    getModel().updateProject(project);
                });
                break;
            case "Year":
                column.setCellValueFactory(new PropertyValueFactory<>("year"));
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(param -> {
                    String newYear = param.getNewValue();
                    Project project = (Project) param.getTableView().getSelectionModel().getSelectedItem();
                    int month = project.getDate().getMonth().getValue();
                    int dayOfMonth = project.getDate().getDayOfMonth();
                    String date_str = newYear+"-"+month+"-"+dayOfMonth;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-dd", Locale.US);
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


    protected abstract void addDataToTable(TableView tableView);
    protected abstract void addColumnsToList();
}
