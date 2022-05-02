package views.tables;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static models.Model.getModel;
import static textConst.StringConst.*;

public class AverageTable extends AbstractTable{

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>(NAME_COLUMN_FROM));
        list_columns.add(new TableColumn<>(NAME_COLUMN_TO));
        list_columns.add(new TableColumn<>(NAME_COLUMN_CURRENCY));
        list_columns.add(new TableColumn<>(NAME_COLUMN_NCC));
        list_columns.add(new TableColumn<>(NAME_COLUMN_TYPE));
        list_columns.add(new TableColumn<>(NAME_COLUMN_MONTH_));
        list_columns.add(new TableColumn<>(NAME_COLUMN_AMOUNT));
    }

    @Override
    protected void addDataToTable(TableView tableView) {
        tableView.setItems(getModel().getProjectForTable(tableView.getId()));
        tableView.getColumns().setAll(list_columns);
    }


}
