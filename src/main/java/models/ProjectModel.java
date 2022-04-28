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
        ObservableList<Project> list =  getListForTable(project.getName_table());
        list.add(project);
    }

    public void getDataByFilters(HashMap<String, List<String>> params, String name_table) {
        List<Project> list = new ArrayList<>();
        ObservableList<Project> observableList = getListForTable(name_table);
        for (int i = 0; i < observableList.size(); i++) {
            Project project = observableList.get(i);
            System.out.println(project.toString());
            if (params.get("from") != null && !params.get("from").isEmpty())
                if (project.getFrom()== null || !params.get("from").contains(project.getFrom()))
                    continue;
            if (params.get("to") != null && !params.get("to").isEmpty())
                if (project.getTo()== null || !params.get("to").contains(project.getTo()))
                    continue;
            if (params.get("currency") != null && !params.get("currency").isEmpty())
                if (project.getCurrency()== null || !params.get("currency").contains(project.getCurrency()))
                    continue;
            if (params.get("ncc") != null && !params.get("ncc").isEmpty())
                if (project.getNCC()== null || !params.get("ncc").contains(project.getNCC()))
                    continue;
            if (params.get("type") != null && !params.get("type").isEmpty())
                if (project.getType() == null || !params.get("type").contains(project.getType()))
                    continue;
            if (params.get("relations") != null && !params.get("relations").isEmpty())
                if (project.getRelations()== null || !params.get("relations").contains(project.getRelations()))
                    continue;
            if(params.get("date") != null && !params.get("date").isEmpty())
                if(project.getDate()== null || !params.get("date").contains(project.getDate().toString()))
                    continue;
            if(params.get("budget") != null && !params.get("budget").isEmpty())
                if(project.getBudget()== null || !params.get("budget").contains(project.getBudget()))
                    continue;
            if(params.get("status") != null && !params.get("status").isEmpty())
                if(project.getStatus()== null || !params.get("status").contains(project.getStatus()))
                    continue;
            if(params.get("category") != null && !params.get("category").isEmpty())
                if(project.getCategory()== null || !params.get("category").contains(project.getCategory()))
                    continue;
            if(params.get("amount") != null && !params.get("amount").isEmpty())
                if(project.getAmount()== null || !params.get("amount").contains(project.getAmount()))
                    continue;
            if(params.get("description") != null && !params.get("description").isEmpty())
                if(project.getDescription()== null || !params.get("description").contains(project.getDescription()))
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
            ObservableList<Project> listForTable = getListForTable(project.getName_table());
            listForTable.add(project);
        }
    }

    public ObservableList<Project> getListForTable(String name_table) {
        switch (name_table)
        {
            case "tableView_data":
                return list_projects_data;
            case "tableView_average":
                return list_projects_average;
            case "tableView_project":
                return list_projects_project;
        }
        return null;
    }

}
