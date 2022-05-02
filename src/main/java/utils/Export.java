package utils;

import entity.Currency;
import entity.Project;
import entity.Relations;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static messages.StaticMessage.closeProgressBar;
import static messages.StaticMessage.createErrorAlertDialog;
import static textConst.StringConst.*;

public class Export {
    private static final Logger logger = LoggerFactory.getLogger(Export.class);
    private Workbook workbook;
    private Sheet sheet;
    private CellStyle style;
    private String name_table;
    private TableView table;
    private List<Project> list_projects;
    private List<Currency> list_currency;

    public Export(String name_table, TableView table) {
        this.name_table = name_table;
        this.table = table;
        if (table.getId().equals(ID_TABLE_CURRENCY)) {
            list_currency = new ArrayList<>();
            list_currency.addAll(table.getItems());
        }
        else {
            list_projects = new ArrayList<>();
            list_projects.addAll(table.getItems());
        }
    }

    public void createDocumentAndAddHeaders() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(name_table);

        Row row_name_table = sheet.createRow(0);
        Row rows_headers = sheet.createRow(1);
        int size = table.getColumns().size() - 1;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, size+1));

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);

        CellStyle style_for_d_and_g = workbook.createCellStyle();
        style_for_d_and_g.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Segoe UI");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);
        style_for_d_and_g.setFont(font);

        style = workbook.createCellStyle();
        style.setWrapText(true);
        XSSFFont font_for_table = ((XSSFWorkbook) workbook).createFont();
        font_for_table.setFontName("Segoe UI");
        font_for_table.setFontHeightInPoints((short) 12);
        style.setFont(font_for_table);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);

        Cell nameTableCell = row_name_table.createCell(0);
        nameTableCell.setCellValue("Tab - " + name_table);
        nameTableCell.setCellStyle(style_for_d_and_g);
        sheet.autoSizeColumn(nameTableCell.getColumnIndex());

        createCells(table, rows_headers, headerStyle);
    }

    private void createCells(TableView table, Row rows_headers, CellStyle headerStyle) {
        Cell id_cell = rows_headers.createCell(0);
        id_cell.setCellValue("ID");
        id_cell.setCellStyle(headerStyle);
        ObservableList columns = table.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            TableColumn o = (TableColumn) columns.get(i);
            String name_column = o.getText();
            Cell headerCell = rows_headers.createCell(i+1);
            headerCell.setCellValue(name_column);
            headerCell.setCellStyle(headerStyle);
        }
    }

    public void addProjectData() {
        Platform.runLater(() ->
                {
                    try {
                        for (int i = 0; i < list_projects.size(); i++) {
                            int count_cell = 0;
                            Row row = sheet.createRow(i + 2);
                            Project project = list_projects.get(i);
                            Cell id = row.createCell(count_cell++);
                            id.setCellValue(project.getId());
                            id.setCellStyle(style);

                            Cell from_cell = row.createCell(count_cell++);
                            from_cell.setCellValue(project.getFrom());
                            from_cell.setCellStyle(style);

                            Cell to_cell = row.createCell(count_cell++);
                            to_cell.setCellValue(project.getTo());
                            to_cell.setCellStyle(style);

                            switch (name_table) {
                                case NAME_TABLE_PROJECT:
                                    Cell relations_cell = row.createCell(count_cell++);
                                    relations_cell.setCellValue(project.getRelations());
                                    relations_cell.setCellStyle(style);

                                    Cell category_cell = row.createCell(count_cell++);
                                    category_cell.setCellValue(project.getCategory());
                                    category_cell.setCellStyle(style);

                                    Cell status_cell = row.createCell(count_cell++);
                                    status_cell.setCellValue(project.getStatus());
                                    status_cell.setCellStyle(style);


                                    sheet.autoSizeColumn(relations_cell.getColumnIndex());
                                    sheet.autoSizeColumn(category_cell.getColumnIndex());
                                    sheet.autoSizeColumn(status_cell.getColumnIndex());

                                    break;
                                case NAME_TABLE_AVERAGE:
                                    Cell currency_cell = row.createCell(count_cell++);
                                    currency_cell.setCellValue(project.getCurrency());
                                    currency_cell.setCellStyle(style);

                                    Cell NCC_cell = row.createCell(count_cell++);
                                    NCC_cell.setCellValue(project.getNCC());
                                    NCC_cell.setCellStyle(style);

                                    Cell type_cell = row.createCell(count_cell++);
                                    type_cell.setCellValue(project.getType());
                                    type_cell.setCellStyle(style);

                                    Cell month_cell = row.createCell(count_cell++);
                                    month_cell.setCellValue(project.getDate().getMonthValue());
                                    month_cell.setCellStyle(style);

                                    Cell amount_cell = row.createCell(count_cell++);
                                    amount_cell.setCellValue(project.getAmount());
                                    amount_cell.setCellStyle(style);

                                    sheet.autoSizeColumn(currency_cell.getColumnIndex());
                                    sheet.autoSizeColumn(NCC_cell.getColumnIndex());
                                    sheet.autoSizeColumn(type_cell.getColumnIndex());
                                    sheet.autoSizeColumn(month_cell.getColumnIndex());
                                    sheet.autoSizeColumn(amount_cell.getColumnIndex());
                                    break;
                                case NAME_TABLE_DATA:
                                    Cell cur_cell = row.createCell(count_cell++);
                                    cur_cell.setCellValue(project.getCurrency());
                                    cur_cell.setCellStyle(style);

                                    Cell ncc_cell = row.createCell(count_cell++);
                                    ncc_cell.setCellValue(project.getNCC());
                                    ncc_cell.setCellStyle(style);

                                    Cell typ_cell = row.createCell(count_cell++);
                                    typ_cell.setCellValue(project.getType());
                                    typ_cell.setCellStyle(style);

                                    Cell year_Cell = row.createCell(count_cell++);
                                    year_Cell.setCellValue(project.getDate().getYear());
                                    year_Cell.setCellStyle(style);

                                    Cell month_Cell = row.createCell(count_cell++);
                                    month_Cell.setCellValue(project.getDate().getMonthValue());
                                    month_Cell.setCellStyle(style);

                                    Cell budget_cell = row.createCell(count_cell++);
                                    budget_cell.setCellValue(project.getBudget());
                                    budget_cell.setCellStyle(style);

                                    Cell amoun_cell = row.createCell(count_cell++);
                                    amoun_cell.setCellValue(project.getAmount());
                                    amoun_cell.setCellStyle(style);

                                    Cell description_cell = row.createCell(count_cell++);
                                    description_cell.setCellValue(project.getDescription());
                                    description_cell.setCellStyle(style);

                                    Cell cat_cell = row.createCell(count_cell++);
                                    cat_cell.setCellValue(project.getCategory());
                                    cat_cell.setCellStyle(style);

                                    Cell status_Cell = row.createCell(count_cell++);
                                    status_Cell.setCellValue(project.getStatus());
                                    status_Cell.setCellStyle(style);

                                    sheet.autoSizeColumn(cur_cell.getColumnIndex());
                                    sheet.autoSizeColumn(ncc_cell.getColumnIndex());
                                    sheet.autoSizeColumn(typ_cell.getColumnIndex());
                                    sheet.autoSizeColumn(year_Cell.getColumnIndex());
                                    sheet.autoSizeColumn(month_Cell.getColumnIndex());
                                    sheet.autoSizeColumn(budget_cell.getColumnIndex());
                                    sheet.autoSizeColumn(amoun_cell.getColumnIndex());
                                    sheet.autoSizeColumn(description_cell.getColumnIndex());
                                    sheet.autoSizeColumn(cat_cell.getColumnIndex());
                                    sheet.autoSizeColumn(status_Cell.getColumnIndex());
                                    break;
                            }

                            sheet.autoSizeColumn(from_cell.getColumnIndex());
                            sheet.autoSizeColumn(to_cell.getColumnIndex());
                        }
                        createFile(name_table);

                    } catch (Exception e) {
                        e.printStackTrace();
                        closeProgressBar("Ошибка экспорта данных!");
                        throw e;
                    }

                }

        );
    }

    public void addCurrencyData() {
        Platform.runLater(() ->
                {
                    try {
                        for (int i = 0; i < list_currency.size(); i++) {
                            int count_cell = 0;
                            Row row = sheet.createRow(i + 2);
                            Currency currency = list_currency.get(i);

                            Cell id = row.createCell(count_cell++);
                            id.setCellValue(currency.getId());
                            id.setCellStyle(style);

                            Cell month_cel = row.createCell(count_cell++);
                            month_cel.setCellValue(currency.getDate().getMonthValue());
                            month_cel.setCellStyle(style);

                            Cell year_cell = row.createCell(count_cell++);
                            year_cell.setCellValue(currency.getYear());
                            year_cell.setCellStyle(style);

                            Cell date_cel = row.createCell(count_cell++);
                            date_cel.setCellValue(currency.getDate().toString());
                            date_cel.setCellStyle(style);
                            Set<Relations> relations_list = currency.getRelations_list();

                            for (Relations r : relations_list) {
                                Cell value = row.createCell(count_cell++);
                                value.setCellValue(r.getValue_relation());
                                value.setCellStyle(style);
                                sheet.autoSizeColumn(value.getColumnIndex());
                            }

                            sheet.autoSizeColumn(month_cel.getColumnIndex());
                            sheet.autoSizeColumn(year_cell.getColumnIndex());
                            sheet.autoSizeColumn(date_cel.getColumnIndex());
                        }

                    } catch (Exception e) {
                        closeProgressBar("Ошибка сохранения файла!");
                        e.printStackTrace();
                        logger.error(e.toString());
                        throw e;
                    }
                    createFile(name_table);
                }

        );
    }

    private void createFile(String name) {
        File file_dir = new File("Export");
        if (!file_dir.exists()) {
            file_dir.mkdir();
        }
        File file_to_write = new File("Export/" + name + " Tab.xlsx");
        try {
            FileOutputStream stream = new FileOutputStream(file_to_write);
            workbook.write(stream);
            workbook.close();
            stream.close();
            closeProgressBar("Данные будут сохранены в папке \"Export\" под названием \"" + name + " Tab\"");
        } catch (FileNotFoundException e) {
            logger.error("Ошибка при создании файла {}", e);
            createErrorAlertDialog("Ошибка при создании файла! Файл не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Ошибка при записи файла {}!", e);
            createErrorAlertDialog("Ошибка при создании файла!");
            e.printStackTrace();
        }

    }
}
