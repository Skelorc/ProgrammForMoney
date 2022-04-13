package models;

import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import service.ProjectService;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class ProjectModel {

    private Project project;
    private ObservableList<Project> list_projects;

    public ProjectModel() {
        ProjectService projectService = new ProjectService();
        List<Project> allProjects = projectService.getAll();
        list_projects = FXCollections.observableArrayList(allProjects);
    }

    public ObservableList<Project> getAllData()
    {
        return list_projects;
    }

    public void createNewProject()
    {
        ProjectService projectService = new ProjectService();
        projectService.saveEntity(project);
        list_projects.add(project);
    }

    public void createNewProject(String to, String from, String currency, String cc, String type, LocalDate value, String budget, String amount, String desc) {
        project = new Project(to,from,currency,cc,type,value,budget,amount,desc);
    }

    public void updateProject(Project project)
    {
        ProjectService projectService = new ProjectService();
        projectService.updateEntity(project);
    }

    public void delete() {
        if(project != null)
        {
            ProjectService projectService = new ProjectService();
            projectService.delete(project);
            list_projects.remove(project);
        }
    }
}
