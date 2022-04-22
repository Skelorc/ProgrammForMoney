package views;

import entity.Currency;
import entity.Project;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import views.tables.CurrencyTable;
import views.tables.Table;

import static models.Model.getModel;

@Getter
public class CurrencyView extends AbstractView {


    @FXML
    private TableView<Currency> tableView;
    @FXML
    protected TextField tf_type_currency, tf_relations;
    @FXML
    protected Button btn_add_type, btn_remove_type, btn_add_row, btn_remove_row,
            btn_add_relations, btn_clear_fields, btn_remove_column;
    @FXML
    protected ComboBox<String> cb_first_value, cb_second_value, cb_type;
    @FXML
    protected DatePicker dp_date;

    @Override
    protected void addValuesToComboBox() {
        ObservableList<String> types = getModel().getCurrencyTypes();
        cb_first_value.setItems(types);
        cb_second_value.setItems(types);
        cb_type.setItems(types);
    }

    @Override
    protected void createTable() {
        table = new CurrencyTable();
        table.createTableView(tableView);
    }

    @Override
    public void initData() {
        super.initData();
        addListenersToComboBox();
    }

    private void addListenersToComboBox() {
        cb_first_value.valueProperty().addListener((observable, oldValue, newValue) -> {
            tf_relations.setText(newValue + "/" + cb_second_value.getSelectionModel().getSelectedItem());
        });
        cb_second_value.valueProperty().addListener((observable, oldValue, newValue) -> {
            tf_relations.setText(cb_first_value.getSelectionModel().getSelectedItem() + "/" + newValue);
        });
    }

    protected void addColumnToTable() {
        CurrencyTable currencyTable = (CurrencyTable) table;
        currencyTable.addColumn(tableView);
    }

    @Override
    public void clearFields() {
        cb_first_value.getSelectionModel().selectFirst();
        cb_second_value.getSelectionModel().selectFirst();
        cb_type.getSelectionModel().selectFirst();
        tf_relations.setText("/");
    }

    @Override
    protected void addComboBoxToList() {
    }

    protected void addNewRow() {
        CurrencyTable currencyTable = (CurrencyTable) table;
        currencyTable.addRow(tableView);
    }

    protected void removeColumn() {
        CurrencyTable currencyTable = (CurrencyTable) table;
        currencyTable.deleteColumn(tableView);
    }


}
