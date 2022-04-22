package views.tables;

import entity.BaseEntity;
import entity.Currency;
import entity.Relations;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.CurrencyService;

import java.time.LocalDate;

import static models.Model.getModel;

public class CurrencyTable extends AbstractTable {

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>("Month"));
        list_columns.add(new TableColumn<>("Year"));
        list_columns.add(new TableColumn<>("Date "));
        ObservableList<String> columns = getModel().getColumns();
        columns.stream().forEach(x -> list_columns.add(new TableColumn<>(x)));
    }


    @Override
    protected void addDataToTable(TableView tableView) {
        ObservableList<Currency> currencyList = getModel().getCurrencyList();
        tableView.setItems(currencyList);
        tableView.getColumns().setAll(list_columns);
    }

    public void addColumn(TableView tableView) {
        String relations = getModel().getRelations().getRelation();
        TableColumn<BaseEntity, String> column = new TableColumn<>(relations);
        configColumns(column, relations);
        tableView.getColumns().add(column);
    }

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

}
