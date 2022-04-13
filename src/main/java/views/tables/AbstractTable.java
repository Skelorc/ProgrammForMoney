package views.tables;

import entity.Currency;
import entity.Project;
import javafx.scene.control.TableView;

abstract class AbstractTable implements Table {

    public final void createTableView(TableView tableView) {
        addColumnsToList();
        createColumns();
        addListenerToTable(tableView);
        addDataToTable(tableView);
    }

    protected abstract void addListenerToTable(TableView<Currency> tableView);
    protected abstract void addColumnsToList();
    protected abstract void createColumns();
    protected abstract void addDataToTable(TableView tableView);

    protected void clearDataInTable(TableView<Project> table) {
        table.getItems().clear();
        table.getColumns().clear();
    }

}
