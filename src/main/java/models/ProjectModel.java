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

    public void getDataByFilters(String from, String to, String currency, String ncc, String type,
                                 String relations, LocalDate date, String budget, String status,
                                 String category, String amount, String description, String name_table) {
        List<Project> list = new ArrayList<>();
        ObservableList<Project> observableList = getProjectForTable(name_table);
        for (int i = 0; i < observableList.size(); i++) {
            Project project = observableList.get(i);
            if (from != null)
                if (project.getFrom()== null || !project.getFrom().equals(from))
                    continue;
            if (to!= null)
                if (project.getTo()== null || !project.getTo().equals(to))
                    continue;
            if (currency!= null)
                if (project.getCurrency()== null || !project.getCurrency().equals(currency))
                    continue;
            if (ncc!= null)
                if (project.getNCC()== null || !project.getNCC().equals(ncc))
                    continue;
            if (type!= null)
                if (project.getType() == null || !project.getType().equals(type))
                    continue;
            if (relations!= null)
                if (project.getRelations()== null ||!project.getRelations().equals(relations))
                    continue;
            if(date != null)
                if(project.getDate()== null ||!project.getDate().equals(date))
                    continue;
            if(budget!= null)
                if(project.getBudget()== null ||!project.getBudget().equals(budget))
                    continue;
            if(status!= null)
                if(project.getStatus()== null ||!project.getStatus().equals(status))
                    continue;
            if(category!= null)
                if(project.getCategory()== null ||!project.getCategory().equals(category))
                    continue;
            if(!amount.isEmpty())
                if(project.getAmount()== null || !project.getAmount().equals(amount))
                    continue;
            if(!description.isEmpty())
                if(project.getDescription()== null ||!project.getDescription().equals(description))
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

    private ObservableList<Project> getListForTable(String name_table) {
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

    public ObservableList<Project> getProjectForTable(String name_table) {
        return getListForTable(name_table);
    }
}
