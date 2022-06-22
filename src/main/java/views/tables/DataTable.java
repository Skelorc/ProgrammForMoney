package views.tables;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

import static models.Model.getModel;
import static textConst.StringConst.*;

@Getter
public class DataTable extends AbstractTable {

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>(NAME_COLUMN_FROM));
        list_columns.add(new TableColumn<>(NAME_COLUMN_TO));
        list_columns.add(new TableColumn<>(NAME_COLUMN_CURRENCY));
        list_columns.add(new TableColumn<>(NAME_COLUMN_NCC));
        list_columns.add(new TableColumn<>(NAME_COLUMN_TYPE));
        list_columns.add(new TableColumn<>(NAME_COLUMN_YEAR));
        list_columns.add(new TableColumn<>(NAME_COLUMN_MONTH_));
        list_columns.add(new TableColumn<>(NAME_COLUMN_BUDGET));
        list_columns.add(new TableColumn<>(NAME_COLUMN_AMOUNT));
        list_columns.add(new TableColumn<>(NAME_COLUMN_DESCRIPTION));
        list_columns.add(new TableColumn<>(NAME_COLUMN_CATEGORY));
        list_columns.add(new TableColumn<>(NAME_COLUMN_STATUS));
    }
}
