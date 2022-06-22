package views;

import dto.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static models.Model.getModel;
import static textConst.StringConst.*;

@Getter
public class CashView implements View {

    @FXML
    private TreeTableView treeTableView;
    @FXML
    protected ComboBox<String> cb_currency;
    @FXML
    private Button btn_create_report;
    @FXML
    protected TextField tf_scale_date, tf_report_date;
    private LocalDate scale_date, report_date;
    private String currency;

    protected void createTable() {
        treeTableView.getColumns().clear();
        treeTableView.setId("treeTable");
        checkData();
        addDataToColumns();

    }

    private void addDataToColumns() {
        getModel().createMapProjectsForCash(report_date, scale_date, currency);
        TreeItem<Transaction> root = new TreeItem<>();
        List<Transaction> nodesListForCash = getModel().getNodesListForCash();
        List<Transaction> valueListForCash = getModel().getResultListForCash();
        createColumns(nodesListForCash, valueListForCash, root);
        valueListForCash.addAll(nodesListForCash);
        addColumnsToTable(valueListForCash);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }

    private void createColumns(List<Transaction> list_nodes, List<Transaction> list_values, TreeItem<Transaction> root) {
        List<TreeItem<Transaction>> treeItems = new ArrayList<>();
        for (Transaction node : list_nodes) {
            TreeItem<Transaction> treeItem = new TreeItem<>();
            treeItem.setValue(node);
            treeItems.add(treeItem);
            List<Transaction> collect = list_values.stream().filter(x -> x.getRelations().equals(node.getFrom())).collect(Collectors.toList());
            for (int i = 0; i < collect.size(); i++) {
                TreeItem<Transaction> item = new TreeItem<>();
                item.setValue(collect.get(i));
                treeItem.getChildren().add(item);
            }
        }
        root.getChildren().addAll(treeItems);
    }


    private void addColumnsToTable(List<Transaction> list) {
        TreeTableColumn<Transaction, String> from = new TreeTableColumn<>(NAME_COLUMN_FROM);
        TreeTableColumn<Transaction, String> to = new TreeTableColumn<>(NAME_COLUMN_TO);
        TreeTableColumn<Transaction, String> ncc = new TreeTableColumn<>(NAME_COLUMN_NCC);

        from.setCellValueFactory(new TreeItemPropertyValueFactory<>(FROM));
        to.setCellValueFactory(new TreeItemPropertyValueFactory<>(TO));
        ncc.setCellValueFactory(new TreeItemPropertyValueFactory<>(NAME_COLUMN_NCC));

        treeTableView.getColumns().addAll(from, to, ncc);

        int j = 0;
        for (int i = 0; i < 24; i++) {
            TreeTableColumn<Transaction, String> date_column;
            /*if (i < 24) {*/
                date_column = new TreeTableColumn<>(scale_date.plusMonths(i).toString());
                date_column.setCellValueFactory(param -> {
                    String text = param.getTreeTableColumn().getText();
                    LocalDate date_cell = LocalDate.parse(text);
                    for (Transaction transaction : list) {
                        Integer value = transaction.getValueByDate(report_date, date_cell);
                        if (value != null) {
                            Transaction row_item = param.getValue().getValue();
                            if (row_item.equals(transaction)) {
                                return new SimpleStringProperty(String.valueOf(value));
                            }
                        }
                    }
                    return new SimpleStringProperty("");
                });
           /* }*/ /*else {
                date_column = new TreeTableColumn<>(scale_date.plusYears(j + 2).toString());
                date_column.setCellValueFactory(param -> {
                            String text = param.getTreeTableColumn().getText();
                            LocalDate date_by_cell = LocalDate.parse(text);
                            Transaction row_item = param.getValue().getValue();
                            for (Transaction transaction : list) {
                                if (transaction.equals(row_item)) {
                                    Integer value = transaction.getValueByDate(report_date, date_by_cell);
                                    if (value != null)
                                        return new SimpleStringProperty(String.valueOf(value));
                                }
                            }
                            return new SimpleStringProperty("");
                        }
                );
                j++;
            }*/
            treeTableView.getColumns().add(date_column);
        }

    }

    protected void checkData() {
        report_date = getModel().createDateFromString(tf_report_date);
        scale_date = getModel().createDateFromString(tf_scale_date);
        currency = cb_currency.getSelectionModel().getSelectedItem();
    }


    private void addDataToComboBox() {
        cb_currency.setItems(getModel().getCurrencyTypes());
    }

    @Override
    public void initData() {
        addDataToComboBox();
    }

    @Override
    public void clearFields() {

    }

}