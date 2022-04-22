package views.tables;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static models.Model.getModel;

public class AverageTable extends AbstractTable{

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>("From Location"));
        list_columns.add(new TableColumn<>("To Project"));
        list_columns.add(new TableColumn<>("Currency"));
        list_columns.add(new TableColumn<>("NCC"));
        list_columns.add(new TableColumn<>("Type"));
        list_columns.add(new TableColumn<>("Month "));
        list_columns.add(new TableColumn<>("Amount"));
    }

    @Override
    protected void addDataToTable(TableView tableView) {
        tableView.setItems(getModel().getProjectForTable(tableView.getId()));
        tableView.getColumns().setAll(list_columns);
    }


}
