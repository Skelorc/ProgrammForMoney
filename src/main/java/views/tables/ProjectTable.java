package views.tables;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static models.Model.getModel;

public class ProjectTable extends AbstractTable{

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>("From Location"));
        list_columns.add(new TableColumn<>("To Project"));
        list_columns.add(new TableColumn<>("Relations"));
        list_columns.add(new TableColumn<>("Category"));
        list_columns.add(new TableColumn<>("Status"));
    }

    @Override
    protected void addDataToTable(TableView tableView) {
        tableView.setItems(getModel().getProjectForTable(tableView.getId()));
        tableView.getColumns().addAll(list_columns);
    }

}
