package views.tables;

import javafx.scene.control.TableView;

public interface Table {
    void createTableView(TableView tableView_data);
    void addColumn(TableView tableView);
    void addRow(TableView tableView);
    void deleteColumn(TableView tableView);

}
