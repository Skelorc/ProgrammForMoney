package views.tables;

import entity.DataObject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Getter;
import models.Model;

import java.util.ArrayList;
import java.util.Iterator;

@Getter
public class TableData {

    private ArrayList<TableColumn<DataObject, String>> list_columns;
    private ArrayList<DataObject> all_data;
    private ObservableList<DataObject> observable_all_data;
    private Model model;

    public void createTableView(TableView<DataObject> tableView) {
        addColumnsToList();
        createColumns();
        addDataToTable(tableView,model);
    }

    public TableData(Model model) {
        this.model = model;
    }

    private void addColumnsToList() {
        list_columns = new ArrayList<>();
        list_columns.add(new TableColumn<>("from_location"));
        list_columns.add(new TableColumn<>("to_project"));
        list_columns.add(new TableColumn<>("Currency"));
        list_columns.add(new TableColumn<>("NCC"));
        list_columns.add(new TableColumn<>("Type"));
        list_columns.add(new TableColumn<>("Date"));
        list_columns.add(new TableColumn<>("Budget"));
        list_columns.add(new TableColumn<>("Amount"));
        list_columns.add(new TableColumn<>("Description"));
        list_columns.add(new TableColumn<>("Relations"));
        list_columns.add(new TableColumn<>("Category"));
        list_columns.add(new TableColumn<>("Status"));
    }

    private void createColumns() {
        Iterator<TableColumn<DataObject, String>> iterator = list_columns.iterator();
        while (iterator.hasNext()) {
            TableColumn<DataObject, String> column = iterator.next();
            configColumn(column, column.getText());
        }
    }

    private void configColumn(TableColumn<DataObject, String> column, String value) {
        if (value.equals("Date"))
            column.setCellValueFactory(userStringCellDataFeatures -> new SimpleObjectProperty<>(userStringCellDataFeatures.getValue().getDate().toString()));
        else
            column.setCellValueFactory(new PropertyValueFactory<>(value));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addDataToTable(TableView<DataObject> tableView, Model model) {
        all_data = (ArrayList<DataObject>) model.getAllDataObjects();
        observable_all_data = FXCollections.observableArrayList(all_data);
        clearDataInTable(tableView);
        tableView.setItems(observable_all_data);
        tableView.getColumns().addAll(list_columns);
    }

    private void clearDataInTable(TableView<DataObject> table) {
        table.getItems().clear();
        table.getColumns().clear();
    }

    public void addDataObjectToList(DataObject dataObject) {
        observable_all_data.add(dataObject);
    }
}
