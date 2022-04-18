package views.tables;

import entity.Currency;
import entity.Relations;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import service.CurrencyService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static messages.StaticMessage.createErrorAlertDialog;
import static models.Model.getModel;

public class CurrencyTable extends AbstractTable {

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>("Month"));
        list_columns.add(new TableColumn<>("Year"));
        list_columns.add(new TableColumn<>("Date"));
        ObservableList<String> columns = getModel().getColumns();
        columns.stream().forEach(x -> list_columns.add(new TableColumn<>(x)));
    }

    @Override
    protected void createColumns() {
        Iterator<TableColumn> iterator = list_columns.iterator();
        while (iterator.hasNext()) {
            TableColumn<Currency, String> column = iterator.next();
            if (column.getText() != null) {
                configColumn(column, column.getText());
            }
        }
    }

    @Override
    protected void addDataToTable(TableView tableView) {
        clearDataInTable(tableView);
        ObservableList<Currency> currencyList = getModel().getCurrencyList();
        tableView.setItems(currencyList);
        tableView.getColumns().setAll(list_columns);
    }


    private void configColumn(TableColumn<Currency, String> column, String value) {
        switch (value) {
            case "Date":
                column.setCellValueFactory(param -> {
                    LocalDate date = param.getValue().getDate();
                    if (date == null)
                        return new SimpleObjectProperty<>("");
                    return new SimpleObjectProperty<>(date.toString());
                });
                column.setOnEditCommit(param -> {
                    String newValue = param.getNewValue();
                    int row_index = param.getTablePosition().getRow();
                    Currency currency = param.getTableView().getItems().get(row_index);
                    LocalDate localDate;
                    try {
                        localDate = LocalDate.parse(newValue);
                        getModel().updateCurrencyDate(currency,localDate);
                        param.getTableView().refresh();
                    } catch (DateTimeParseException e) {
                        createErrorAlertDialog("Please, write new date next format : YEAR-MONTH-DAY ");
                    }
                });

                break;
            case "Month":
                column.setCellValueFactory(new PropertyValueFactory<>("month"));
                break;
            case "Year":
                column.setCellValueFactory(new PropertyValueFactory<>("year"));
                break;
            default:
                column.setCellValueFactory(param -> {
                    Set<Relations> relations_list = param.getValue().getRelations_list();
                    Relations relations = relations_list.stream().filter(x -> x.getRelation().equals(column.getText())).findAny().orElse(null);
                    return new SimpleObjectProperty<>(relations.getValue());
                });
                column.setOnEditCommit(event -> {
                    Currency currency = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    Set<Relations> relations_list = currency.getRelations_list();
                    Relations relations = relations_list.stream().filter(x -> x.getRelation().equals(event.getTablePosition().getTableColumn().getText())).findAny().orElse(null);
                            String newValue = event.getNewValue();
                            getModel().updateCurrencyRelations(currency,relations,newValue);
                }
                );
                break;
        }
        column.setCellFactory(TextFieldTableCell.forTableColumn());
    }


    @Override
    public void addColumn(TableView tableView) {
        String relations = getModel().getRelations().getRelation();
        TableColumn<Currency, String> column = new TableColumn<>(relations);
        configColumn(column, relations);
        tableView.getColumns().add(column);
    }

    @Override
    public void addRow(TableView tableView) {
        Currency currency = new Currency();
        currency.setDate(LocalDate.now());
        for (int i = 3; i < tableView.getColumns().size(); i++) {
            TableColumn<Currency, String> column = (TableColumn<Currency, String>) tableView.getColumns().get(i);
            String relation = column.getText();
            Relations relations = new Relations();
            relations.setRelation(relation);
            relations.setCurrency(currency);
            currency.getRelations_list().add(relations);
        }
        CurrencyService service = new CurrencyService();
        service.saveOrUpdate(currency);
        getModel().addNewCurrencyToList(currency);
        tableView.getSelectionModel().select(0);
        tableView.scrollTo(0);
    }

    @Override
    public void deleteColumn(TableView tableView) {
        TableColumn column_for_delete = new TableColumn<>();
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            TableColumn tableColumn = (TableColumn) tableView.getColumns().get(i);
            if (tableColumn.getText().equals(getModel().getRelations().getRelation())) {
                column_for_delete = tableColumn;
            }
        }
        tableView.getColumns().remove(column_for_delete);
    }

    @Override
    protected void addListenerToTable(TableView tableView) {
        tableView.setEditable(true);
    }


}
