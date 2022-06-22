package models;

import dto.Transaction;
import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import service.ProjectService;

import java.time.LocalDate;
import java.util.*;

import static messages.StaticMessage.createErrorAlertDialog;
import static textConst.StringConst.*;


@Getter
@Setter
public class ProjectModel {

    private Project project;
    private ObservableList<Project> list_projects_dictionary;
    private ObservableList<Project> list_projects_data;
    private ObservableList<Project> list_projects_average;
    private ObservableList<Project> list_projects_balance;
    private ObservableList<Project> filtered_list_projects;
    private TransactionModel transactionModel;


    public ProjectModel() {
        ProjectService projectService = new ProjectService();
        List<Project> allProjects = projectService.getAll();
        addObjectsToLists(allProjects);
    }




    public void getDataByFilters(HashMap<String, List<String>> params, String name_table) {
        List<Project> list = new ArrayList<>();
        ObservableList<Project> observableList = getListForTable(name_table);
        for (int i = 0; i < observableList.size(); i++) {
            Project project = observableList.get(i);
            if (params.get(FROM) != null && !params.get(FROM).isEmpty())
                if (project.getFrom() == null || !params.get(FROM).contains(project.getFrom()))
                    continue;
            if (params.get(TO) != null && !params.get(TO).isEmpty())
                if (project.getTo() == null || !params.get(TO).contains(project.getTo()))
                    continue;
            if (params.get(CURRENCY) != null && !params.get(CURRENCY).isEmpty())
                if (project.getCurrency() == null || !params.get(CURRENCY).contains(project.getCurrency()))
                    continue;
            if (params.get(NCC) != null && !params.get(NCC).isEmpty())
                if (project.getNCC() == null || !params.get(NCC).contains(project.getNCC()))
                    continue;
            if (params.get(TYPE) != null && !params.get(TYPE).isEmpty())
                if (project.getType() == null || !params.get(TYPE).contains(project.getType()))
                    continue;
            if (params.get(RELATIONS) != null && !params.get(RELATIONS).isEmpty())
                if (project.getRelations() == null || !params.get(RELATIONS).contains(project.getRelations()))
                    continue;
            if (params.get(MONTH) != null && !params.get(MONTH).isEmpty())
                if (project.getDate() == null || !params.get(MONTH).contains(project.getDate().toString()))
                    continue;
            if (params.get(BUDGET) != null && !params.get(BUDGET).isEmpty())
                if (project.getBudget() == null || !params.get(BUDGET).contains(project.getBudget()))
                    continue;
            if (params.get(STATUS) != null && !params.get(STATUS).isEmpty())
                if (project.getStatus() == null || !params.get(STATUS).contains(project.getStatus()))
                    continue;
            if (params.get(CATEGORY) != null && !params.get(CATEGORY).isEmpty())
                if (project.getCategory() == null || !params.get(CATEGORY).contains(project.getCategory()))
                    continue;
            if (params.get(AMOUNT) != null && !params.get(AMOUNT).isEmpty())
                if (project.getAmount() == 0 || !params.get(AMOUNT).contains(String.valueOf(project.getAmount())))
                    continue;
            if (params.get(DESCRIPTION) != null && !params.get(DESCRIPTION).isEmpty())
                if (project.getDescription() == null || !params.get(DESCRIPTION).contains(project.getDescription()))
                    continue;
            list.add(project);
        }
        filtered_list_projects = FXCollections.observableArrayList(list);
    }


    public ObservableList<Project> getListForTable(String name_table) {
        switch (name_table) {
            case ID_TABLE_DATA:
                return list_projects_data;
            case ID_TABLE_AVERAGE:
                return list_projects_average;
            case ID_TABLE_PROJECT:
                return list_projects_dictionary;
            case ID_TABLE_BALANCE:
                return list_projects_balance;
        }
        return null;
    }

    public void addListProjects(List<Project> list) {
        for (int i = 0; i < list.size(); i++) {
            project = list.get(i);
            saveProjectAndAddToList();
        }
    }

    public void createNewProject(String from, String to, String currency, String ncc,
                                 String type, LocalDate date, String relations, String category, String status, String budget, String amount, String desc, String name_table) {
        switch (name_table) {
            case ID_TABLE_PROJECT:
                createProjectFromProject(from, to, relations, category, status, name_table);
                break;
            case ID_TABLE_AVERAGE:
                createProjectFromAverage(from, to, currency, ncc, type, date, amount, name_table);
                break;
            case ID_TABLE_DATA:
                createProjectFromData(from, to, currency, ncc, type, date, budget, amount, desc, name_table);
                break;
            case ID_TABLE_BALANCE:
                createProjectFromBalance(from, currency, ncc, date, amount, name_table);
                break;
        }
        saveProjectAndAddToList();
    }


    public void updateProject(Project project) {
        ProjectService projectService = new ProjectService();
        projectService.saveOrUpdate(project);
    }

    public void delete(int index, String name_table) {
        ObservableList<Project> listForTable = getListForTable(name_table);
        project = listForTable.get(index);
        ProjectService projectService = new ProjectService();
        projectService.delete(project);
        listForTable.remove(project);
        project = null;
    }

    private void saveProjectAndAddToList() throws NullPointerException {
        boolean add_relations = false;
        if (project.getName_table().equals(ID_TABLE_PROJECT) || project.getName_table().equals(ID_TABLE_BALANCE))
            add_relations = true;
        if (project.getRelations() == null || project.getRelations().isEmpty()) {
            for (Project p : list_projects_dictionary) {
                if (p.getFrom().equals(project.getFrom()) && p.getTo().equals(project.getTo())) {
                    project.setRelations(p.getRelations());
                    add_relations = true;
                    break;
                }
            }
        }
        if (!add_relations) {
            createErrorAlertDialog("Не найдено Relations для " + project.getFrom() + " и " + project.getTo());
            throw new IllegalArgumentException("Не найдено Relations для " + project.getFrom() + " и " + project.getTo());
        }
        ProjectService projectService = new ProjectService();
        projectService.saveOrUpdate(project);
        ObservableList<Project> list = getListForTable(project.getName_table());
        list.add(project);
    }

    private void addObjectsToLists(List<Project> allProjects) {
        list_projects_data = FXCollections.observableArrayList(new ArrayList<>());
        list_projects_average = FXCollections.observableArrayList(new ArrayList<>());
        list_projects_dictionary = FXCollections.observableArrayList(new ArrayList<>());
        list_projects_balance = FXCollections.observableArrayList(new ArrayList<>());
        for (int i = 0; i < allProjects.size(); i++) {
            Project project = allProjects.get(i);
            getListForTable(project.getName_table()).add(project);
        }
    }

    private void createProjectFromAverage(@NonNull String from, @NonNull String to, @NonNull String currency,
                                          @NonNull String ncc, @NonNull String type, @NonNull LocalDate date, String amount, String name_table) {
        project = new Project();
        project.setFrom(from);
        project.setTo(to);
        project.setCurrency(currency);
        project.setNCC(ncc);
        project.setType(type);
        project.setDate(date);
        project.setBudget("Budget");
        if (amount != null && !amount.isEmpty())
            project.setAmount(Integer.parseInt(amount));
        else
            project.setAmount(0);
        project.setName_table(name_table);
    }

    private void createProjectFromProject(@NonNull String from, @NonNull String to, @NonNull String relations, @NonNull String category, @NonNull String status, String name_table) {
        project = new Project();
        project.setFrom(from);
        project.setTo(to);
        project.setRelations(relations);
        project.setCategory(category);
        project.setStatus(status);
        project.setName_table(name_table);
    }

    private void createProjectFromBalance(@NonNull String from, @NonNull String currency, @NonNull String ncc, @NonNull LocalDate date, @NonNull String amount, @NonNull String name_table) {
        project = new Project();
        project.setFrom(from);
        project.setCurrency(currency);
        project.setNCC(ncc);
        project.setDate(date);
        if (!amount.isEmpty())
            project.setAmount(Integer.parseInt(amount));
        else
            project.setAmount(0);
        project.setName_table(name_table);
    }

    private void createProjectFromData(@NonNull String from, @NonNull String to, @NonNull String currency, @NonNull String ncc,
                                       @NonNull String type, @NonNull LocalDate date, @NonNull String budget, String amount, String desc, String name_table) {
        project = new Project();
        project.setFrom(from);
        project.setTo(to);
        project.setCurrency(currency);
        project.setNCC(ncc);
        project.setType(type);
        project.setDate(date);
        project.setBudget(budget);
        if (amount != null && !amount.isEmpty())
            project.setAmount(Integer.parseInt(amount));
        else
            project.setAmount(0);
        project.setDescription(desc);
        project.setName_table(name_table);
    }



}
