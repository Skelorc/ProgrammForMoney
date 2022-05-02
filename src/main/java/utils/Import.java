package utils;

import entity.Currency;
import entity.Project;
import entity.Relations;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static messages.StaticMessage.*;
import static models.Model.getModel;
import static textConst.StringConst.*;

public class Import {

    private static final Logger logger = LoggerFactory.getLogger(Import.class);
    private Workbook workbook;
    private Sheet sheet;
    private File file;
    private Project project;
    private Currency cur;
    private String name_table;
    private List<Project> list_projects;
    private List<Currency> list_currency;
    private ObservableList<TableColumn> columns;
    private boolean id_column = false;

    public void findFile() {
        try {
            file = MyFileChooser.createFileChooser();
            if (file != null) {
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(inputStream);
                }
            } else {
                createErrorAlertDialog("Вы не выбрали файл для импорта!");
                closeProgressBar();
                throw new NullPointerException("Файл не выбран!");
            }

        } catch (FileNotFoundException e) {
            createErrorAlertDialog("Файл не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            createErrorAlertDialog("Файл не загружен!");
            e.printStackTrace();
        }
    }

    public void readFile(TableView table) {
        showProgressBar();
        sheet = workbook.getSheetAt(0);
        Iterator<Row> it_row = sheet.rowIterator();
        getNameTableAndCreateLists(table);
        Platform.runLater(() -> {
            while (it_row.hasNext()) {
                Row row = it_row.next();
                if (row.getRowNum() == 0)
                    checkDataInfile(row, table);
                else {
                    addDataFromFile(row);
                }
            }
            closeProgressBar("Данные успешно добавлены!");
            if (list_projects != null)
                getModel().addProjectsFromExcel(table.getId(), list_projects);
            else
                getModel().addCurrencyFromExcel(list_currency);
        });
    }

    private void getNameTableAndCreateLists(TableView table) {
        name_table = table.getId();
        if (name_table.equals(ID_TABLE_CURRENCY))
            list_currency = new ArrayList<>();
        else
            list_projects = new ArrayList<>();
    }

    private void checkDataInfile(Row row, TableView table) {
        Iterator<Cell> cellIterator = row.cellIterator();
        columns = table.getColumns();
        Iterator<TableColumn> iterator_columns = columns.iterator();
        while (cellIterator.hasNext()) {
            String value_from_excel = cellIterator.next().getStringCellValue().trim();
            if (value_from_excel.equals("ID")) {
                id_column = true;
                continue;
            }
            String name_column = "";
            try {
                name_column = iterator_columns.next().getText().trim();
            } catch (Exception e) {
                createErrorAlertDialog("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы.");
                logger.error("Ошибка добавления данных!" + value_from_excel);
                throw new NullPointerException("Ошибка добавления данных!");
            }
            if (!value_from_excel.equals(name_column)) {
                createErrorAlertDialog("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в значении " + value_from_excel);
                logger.error("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в значении " + value_from_excel);
                throw new IllegalArgumentException("Ошибка добавления данных!");
            }
        }

    }

    private void addDataFromFile(Row row) {
        int num_cell = 0;
        switch (name_table) {
            case ID_TABLE_DATA:
                try {
                    project = new Project();
                    if (id_column) {
                        String id = getStringValueFromCell(row.getCell(num_cell++));
                        if (!id.isEmpty()) {
                            project.setId(Long.parseLong(id));
                        }
                    }
                    String from = getStringValueFromCell(row.getCell(num_cell++));
                    String to = getStringValueFromCell(row.getCell(num_cell++));
                    String currency = getStringValueFromCell(row.getCell(num_cell++));
                    String ncc = getStringValueFromCell(row.getCell(num_cell++));
                    String type = getStringValueFromCell(row.getCell(num_cell++));
                    String year = getStringValueFromCell(row.getCell(num_cell++));
                    String month = getStringValueFromCell(row.getCell(num_cell++));
                    String budget = getStringValueFromCell(row.getCell(num_cell++));
                    String amount = getStringValueFromCell(row.getCell(num_cell++));
                    String description = getStringValueFromCell(row.getCell(num_cell++));


                    checkParams(from, to, currency, ncc, type, budget, "", "", "");

                    project.setFrom(from);
                    project.setTo(to);
                    project.setCurrency(currency);
                    project.setNCC(ncc);
                    project.setType(type);
                    createDateFromCells(project, year, month);
                    project.setBudget(budget);
                    project.setAmount(amount);
                    project.setDescription(description);
                    project.setName_table(ID_TABLE_DATA);
                } catch (Exception e) {
                    createErrorAlertDialog("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    logger.error("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    e.printStackTrace();
                    throw e;
                }
                list_projects.add(project);
                break;
            case ID_TABLE_AVERAGE:
                try {
                    project = new Project();
                    if (id_column) {
                        String id = getStringValueFromCell(row.getCell(num_cell++));
                        if (!id.isEmpty()) {
                            project.setId(Long.parseLong(id));
                        }
                    }
                    String from = getStringValueFromCell(row.getCell(num_cell++));
                    String to = getStringValueFromCell(row.getCell(num_cell++));
                    String currency = getStringValueFromCell(row.getCell(num_cell++));
                    String ncc = getStringValueFromCell(row.getCell(num_cell++));
                    String type = getStringValueFromCell(row.getCell(num_cell++));
                    String month = getStringValueFromCell(row.getCell(num_cell++));
                    String amount = getStringValueFromCell(row.getCell(num_cell++));

                    checkParams(from, to, currency, ncc, type, "", "", "", "");

                    project.setFrom(from);
                    project.setTo(to);
                    project.setCurrency(currency);
                    project.setNCC(ncc);
                    project.setType(type);
                    createDateFromCells(project, "", month);
                    project.setAmount(amount);
                    project.setName_table(ID_TABLE_AVERAGE);
                } catch (Exception e) {
                    createErrorAlertDialog("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    logger.error("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    e.printStackTrace();
                    throw e;
                }
                list_projects.add(project);
                break;
            case ID_TABLE_PROJECT:
                try {
                    project = new Project();
                    if (id_column) {
                        String id = getStringValueFromCell(row.getCell(num_cell++));
                        if (!id.isEmpty()) {
                            project.setId(Long.parseLong(id));
                        }
                    }
                    String from = getStringValueFromCell(row.getCell(num_cell++));
                    String to = getStringValueFromCell(row.getCell(num_cell++));
                    String relations = getStringValueFromCell(row.getCell(num_cell++));
                    String category = getStringValueFromCell(row.getCell(num_cell++));
                    String status = getStringValueFromCell(row.getCell(num_cell++));

                    checkParams(from, to, "", "", "", "", relations, category, status);
                    project.setFrom(from);
                    project.setTo(to);
                    project.setRelations(relations);
                    project.setCategory(category);
                    project.setStatus(status);
                    project.setName_table(ID_TABLE_PROJECT);
                } catch (Exception e) {
                    createErrorAlertDialog("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    logger.error("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    e.printStackTrace();
                    throw e;
                }
                list_projects.add(project);
                break;
            case ID_TABLE_CURRENCY:
                try {
                    cur = new Currency();
                    if (id_column) {
                        String id = getStringValueFromCell(row.getCell(num_cell++));
                        if (!id.isEmpty()) {
                            cur.setId(Long.parseLong(id));
                        }
                    }
                    String month = getStringValueFromCell(row.getCell(num_cell++));
                    String year = getStringValueFromCell(row.getCell(num_cell++));
                    String date = getStringValueFromCell(row.getCell(num_cell++));
                    cur.setDate(LocalDate.parse(date));
                    for (int i = num_cell++; i < row.getLastCellNum(); i++) {
                        Relations relations = new Relations();
                        String relation = "";
                        if (id_column)
                            relation = columns.get(i - 1).getText();
                        else
                            relation = columns.get(i).getText();
                        relations.setRelation(relation);
                        String value = getStringValueFromCell(row.getCell(i));
                        relations.setValue_relation(value);
                        relations.setCurrency(cur);
                        cur.getRelations_list().add(relations);
                    }
                    list_currency.add(cur);
                } catch (Exception e) {
                    createErrorAlertDialog("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    logger.error("Ошибка добавления данных! Проверьте валидность столбцов в файле Excel и программы. Ошибка в строке № " + row.getRowNum());
                    e.printStackTrace();
                    throw e;
                }
                break;
        }
    }

    private void checkParams(String from, String to, String currency, String ncc, String type, String budget, String relations, String category, String status) {
        if (!from.isEmpty())
            getModel().checkParams(FROM, from);
        if (!to.isEmpty())
            getModel().checkParams(TO, to);
        if (!currency.isEmpty())
            getModel().createTypeCurrency(currency);
        if (!ncc.isEmpty())
            getModel().checkParams(NCC, ncc);
        if (!type.isEmpty())
            getModel().checkParams(TYPE, type);
        if (!budget.isEmpty())
            getModel().checkParams(BUDGET, budget);
        if (!relations.isEmpty())
            getModel().checkParams(RELATIONS, relations);
        if (!category.isEmpty())
            getModel().checkParams(CATEGORY, category);
        if (!status.isEmpty())
            getModel().checkParams(STATUS, status);
    }

    private void createDateFromCells(Project project, String year, String month) {
        LocalDate localDate = LocalDate.now();
        String date;
        if (!year.equals(""))
            date = year + "-" + month + "-" + localDate.getDayOfMonth();
        else
            date = LocalDate.now().getYear() + "-" + month + "-" + localDate.getDayOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-dd", Locale.US);
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            createErrorAlertDialog("Ошибка получения даты из столбцов.");
            e.printStackTrace();
            throw e;
        }
        project.setDate(localDate);
    }

    private String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return "";
        } else if (cell.getCellType().equals(CellType.STRING)) {
            return cell.getStringCellValue();
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            return String.valueOf((long) cell.getNumericCellValue());
        }

        return "";
    }

}
