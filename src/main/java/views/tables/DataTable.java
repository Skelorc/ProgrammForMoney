package views.tables;

import entity.Currency;
import entity.Project;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class DataTable extends AbstractTable {

    private ArrayList<Project> all_data;

    @Override
    protected void addListenerToTable(TableView<Currency> tableView) {

    }

    protected void addColumnsToList() {
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

    @Override
    protected void createColumns() {
       /* Iterator<TableColumn<Project, String>> iterator = list_columns.iterator();
        while (iterator.hasNext()) {
            TableColumn<Project, String> column = iterator.next();
            configColumn(column, column.getText());
        }*/
    }

    protected void addDataToTable(TableView tableView) {
      /*  all_data = (ArrayList<Project>) model.getAllProjects();
        observable_all_data = FXCollections.observableArrayList(all_data);
        clearDataInTable(tableView);
        tableView.setItems(observable_all_data);
        tableView.getColumns().addAll(list_columns);*/
    }

    private void configColumn(TableColumn<Project, String> column, String value) {
       /* if (value.equals("Date"))
            column.setCellValueFactory(userStringCellDataFeatures -> new SimpleObjectProperty<>(userStringCellDataFeatures.getValue().getDate().toString()));
        else
            column.setCellValueFactory(new PropertyValueFactory<>(value));
        column.setCellFactory(TextFieldTableCell.forTableColumn());*/
    }



    public void updateTable(Project project)
    {
       /* for (int i = 0; i < observable_all_data.size(); i++) {
            if(observable_all_data.get(i).getId() == project.getId())
            {
                observable_all_data.set(i, project);
                System.out.println(i);
            }
        }*/
    }

    public void addDataToObservableList(Project project) {
       /* observable_all_data.add(project);*/
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
