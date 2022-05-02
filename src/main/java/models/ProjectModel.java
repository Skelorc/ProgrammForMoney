package models;

import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import service.ProjectService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static textConst.StringConst.*;


@Getter
@Setter
public class ProjectModel {

    private Project project;
    private ObservableList<Project> list_projects_project;
    private ObservableList<Project> list_projects_data;
    private ObservableList<Project> list_projects_average;
    private ObservableList<Project> filtered_list_projects;

    public ProjectModel() {
        ProjectService projectService = new ProjectService();
        List<Project> allProjects = projectService.getAll();
        addObjectsToLists(allProjects);
    }


    public void createNewProject(@NonNull String to, @NonNull String from, @NonNull String currency, @NonNull String cc,
                                 @NonNull String type, @NonNull LocalDate value, String budget, String amount, String desc, String name_table) {
        project = new Project(to, from, currency, cc, type, value, budget, amount, desc, name_table);
        createProjectAndAddToList();
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

    public void createNewProject(@NonNull String to, @NonNull String from,
                                 @NonNull String relations, @NonNull String category,
                                 @NonNull String status, String name_table) {
        project = new Project();
        project.setTo(to);
        project.setFrom(from);
        project.setRelations(relations);
        project.setCategory(category);
        project.setStatus(status);
        project.setName_table(name_table);
        createProjectAndAddToList();
    }

    public void createNewProject(@NonNull String to, @NonNull String from, @NonNull String currency,
                                 @NonNull String ncc, @NonNull String type,
                                 @NonNull LocalDate date, String amount, String name_table) {
        project = new Project();
        project.setTo(to);
        project.setFrom(from);
        project.setCurrency(currency);
        project.setNCC(ncc);
        project.setType(type);
        project.setDate(date);
        project.setAmount(amount);
        project.setName_table(name_table);
        createProjectAndAddToList();
    }


    private void createProjectAndAddToList() {
        ProjectService projectService = new ProjectService();
        projectService.saveOrUpdate(project);
        ObservableList<Project> list = getListForTable(project.getName_table());
        list.add(project);
    }

    public void getDataByFilters(HashMap<String, List<String>> params, String name_table) {
        List<Project> list = new ArrayList<>();
        ObservableList<Project> observableList = getListForTable(name_table);
        for (int i = 0; i < observableList.size(); i++) {
            Project project = observableList.get(i);
            System.out.println(project.toString());
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
                if (project.getAmount() == null || !params.get(AMOUNT).contains(project.getAmount()))
                    continue;
            if (params.get(DESCRIPTION) != null && !params.get(DESCRIPTION).isEmpty())
                if (project.getDescription() == null || !params.get(DESCRIPTION).contains(project.getDescription()))
                    continue;
            list.add(project);
        }
        filtered_list_projects = FXCollections.observableArrayList(list);
    }

    private void addObjectsToLists(List<Project> allProjects) {
        list_projects_data = FXCollections.observableArrayList(new ArrayList<>());
        list_projects_average = FXCollections.observableArrayList(new ArrayList<>());
        list_projects_project = FXCollections.observableArrayList(new ArrayList<>());
        for (int i = 0; i < allProjects.size(); i++) {
            Project project = allProjects.get(i);
            getListForTable(project.getName_table()).add(project);
        }
    }

    public ObservableList<Project> getListForTable(String name_table) {
        switch (name_table) {
            case ID_TABLE_DATA:
                return list_projects_data;
            case ID_TABLE_AVERAGE:
                return list_projects_average;
            case ID_TABLE_PROJECT:
                return list_projects_project;
        }
        return null;
    }

    public void addListProjects(String name_table, List<Project> list) {
        ObservableList<Project> listForTable = getListForTable(name_table);
        ProjectService service = new ProjectService();
        for (int i = 0; i < list.size(); i++) {
            Project project = list.get(i);
            service.saveOrUpdate(project);
        }
        listForTable.clear();
        List<Project> all = service.getAll();
        for (int i = 0; i < all.size(); i++) {
            Project project = all.get(i);
            getListForTable(project.getName_table()).add(project);
        }
    }
}
