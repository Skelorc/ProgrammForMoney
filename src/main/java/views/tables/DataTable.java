package views.tables;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

import static models.Model.getModel;

@Getter
public class DataTable extends AbstractTable {

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>("From Location"));
        list_columns.add(new TableColumn<>("To Project"));
        list_columns.add(new TableColumn<>("Currency"));
        list_columns.add(new TableColumn<>("NCC"));
        list_columns.add(new TableColumn<>("Type"));
        list_columns.add(new TableColumn<>("Year"));
        list_columns.add(new TableColumn<>("Month "));
        list_columns.add(new TableColumn<>("Budget"));
        list_columns.add(new TableColumn<>("Amount"));
        list_columns.add(new TableColumn<>("Description"));
        list_columns.add(new TableColumn<>("Category"));
        list_columns.add(new TableColumn<>("Status"));
    }

    protected void addDataToTable(TableView tableView) {
        tableView.setItems(getModel().getProjectForTable(tableView.getId()));
        tableView.getColumns().addAll(list_columns);
    }
}
