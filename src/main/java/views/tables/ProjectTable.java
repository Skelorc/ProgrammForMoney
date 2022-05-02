package views.tables;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static models.Model.getModel;
import static textConst.StringConst.*;

public class ProjectTable extends AbstractTable{

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>(NAME_COLUMN_FROM));
        list_columns.add(new TableColumn<>(NAME_COLUMN_TO));
        list_columns.add(new TableColumn<>(NAME_COLUMN_RELATIONS));
        list_columns.add(new TableColumn<>(NAME_COLUMN_CATEGORY));
        list_columns.add(new TableColumn<>(NAME_COLUMN_STATUS));
    }

    @Override
    protected void addDataToTable(TableView tableView) {
        tableView.setItems(getModel().getProjectForTable(tableView.getId()));
        tableView.getColumns().addAll(list_columns);
    }

}
