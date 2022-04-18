package views.tables;

import entity.Currency;
import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Iterator;
import java.util.List;

import static models.Model.getModel;

public class ProjectTable extends AbstractTable{



    @Override
    protected void addListenerToTable(TableView<Currency> tableView) {

    }

    @Override
    protected void addColumnsToList() {
        list_columns.add(new TableColumn<>("To Project"));
        list_columns.add(new TableColumn<>("From Location"));
        list_columns.add(new TableColumn<>("Relations"));
        list_columns.add(new TableColumn<>("Category"));
        list_columns.add(new TableColumn<>("Status"));
    }

    @Override
    protected void createColumns() {
        Iterator<TableColumn> iterator = list_columns.iterator();
        while(iterator.hasNext())
        {
            TableColumn<Project, String> column = iterator.next();
            if(column.getText() != null)
            {
                configColumn(column, column.getText());
            }
        }
    }

    private void configColumn(TableColumn<Project, String> column, String value) {
        switch (value)
        {
            case "To Project":
                column.setCellValueFactory(new PropertyValueFactory<>("to"));
                ObservableList<String> to = getModel().getParamsByWord("to");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(to));
                column.setOnEditCommit(event -> {
                    Project project = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setTo(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "From Location":
                column.setCellValueFactory(new PropertyValueFactory<>("from"));
                ObservableList<String> from = getModel().getParamsByWord("from");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(from));
                column.setOnEditCommit(event -> {
                    Project project = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setFrom(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Relations":
                column.setCellValueFactory(new PropertyValueFactory<>("relations"));
                ObservableList<String> relations = getModel().getColumns();
                column.setCellFactory(ComboBoxTableCell.forTableColumn(relations));
                column.setOnEditCommit(event -> {
                    Project project = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setRelations(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Category":
                column.setCellValueFactory(new PropertyValueFactory<>("category"));
                ObservableList<String> category = getModel().getParamsByWord("category");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(category));
                column.setOnEditCommit(event -> {
                    Project project = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setCategory(newValue);
                    getModel().updateProject(project);
                });
                break;
            case "Status":
                column.setCellValueFactory(new PropertyValueFactory<>("status"));
                ObservableList<String> status = getModel().getParamsByWord("status");
                column.setCellFactory(ComboBoxTableCell.forTableColumn(status));
                column.setOnEditCommit(event -> {
                    Project project = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    project.setStatus(newValue);
                    getModel().updateProject(project);
                });
                break;
        }
    }

    @Override
    protected void addDataToTable(TableView tableView) {
        tableView.setEditable(true);
        tableView.setItems(getModel().getAllProjects());
        tableView.getColumns().setAll(list_columns);
    }

    @Override
    public void addColumn(TableView tableView) {

    }

    @Override
    public void addRow(TableView tableView) {

    }

    @Override
    public void deleteColumn(TableView tableView) {

    }
}
