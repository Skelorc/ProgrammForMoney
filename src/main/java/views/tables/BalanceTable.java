package views.tables;

import javafx.scene.control.TableColumn;

import static textConst.StringConst.*;

public class BalanceTable extends AbstractTable{

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>(NAME_COLUMN_YEAR));
        list_columns.add(new TableColumn<>(NAME_COLUMN_MONTH_));
        list_columns.add(new TableColumn<>(NAME_COLUMN_FROM));
        list_columns.add(new TableColumn<>(NAME_COLUMN_CURRENCY));
        list_columns.add(new TableColumn<>(NAME_COLUMN_NCC));
        list_columns.add(new TableColumn<>(NAME_COLUMN_AMOUNT));
    }
}
