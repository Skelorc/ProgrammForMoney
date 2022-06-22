package views;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import views.tables.BalanceTable;

import java.util.Map;

import static models.Model.getModel;
import static textConst.StringConst.FROM;
import static textConst.StringConst.NCC;

public class BalanceView extends AbstractView {

    @FXML
    protected TextField tf_date, tf_amount, tf_filter_amount;

    @FXML
    protected ComboBox<String> cb_from,cb_currency,cb_ncc;
    @FXML
    protected CheckComboBox<String> ccb_currency,ccb_from,ccb_ncc;
    @FXML
    protected Button btn_add, btn_delete, btn_show_all, btn_find, btn_clear_fields;
    @FXML
    protected TableView tableView_balance;


    @Override
    protected void addValuesToComboBox() {
        ObservableList<String> currencyTypes = getModel().getCurrencyTypes();
        Map<String, ObservableList<String>> allParams = getModel().getAllParams();
        cb_from.setItems(allParams.get(FROM));
        ccb_from.getItems().addAll(allParams.get(FROM));
        cb_ncc.setItems(allParams.get(NCC));
        ccb_ncc.getItems().addAll(allParams.get(NCC));
        cb_currency.setItems(currencyTypes);
        ccb_currency.getItems().addAll(currencyTypes);
    }

    @Override
    protected void addComboBoxToList() {

    }

    @Override
    protected void createTable() {
        table = new BalanceTable();
        table.createTableView(tableView_balance);
    }

    protected void showFilteredProjectsList()
    {
        tableView_balance.setItems(getModel().getFilteredProjects());
    }

    protected void showAll()
    {
        tableView_balance.setItems(getModel().getProjectForTable(tableView_balance.getId()));
    }

    @Override
    public void clearFields() {
        cb_from.getSelectionModel().clearSelection();
        ccb_from.getCheckModel().clearChecks();
        cb_currency.getSelectionModel().clearSelection();
        ccb_currency.getCheckModel().clearChecks();
        cb_ncc.getSelectionModel().clearSelection();
        ccb_ncc.getCheckModel().clearChecks();
        clearTextField(tf_date);
        clearTextField(tf_amount);
        clearTextField(tf_filter_amount);
    }


}
