package models;

import entity.Currency;
import entity.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import service.CurrencyService;
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

    public ObservableList<Project> getAllData() {
        return list_projects;
    }


    public void createNewProject(String to, String from, String currency, String cc, String type, LocalDate value, String budget, String amount, String desc) {
        project = new Project(to, from, currency, cc, type, value, budget, amount, desc);
        createNewProject();
    }

    public void updateProject(Project project) {
        ProjectService projectService = new ProjectService();
        projectService.saveOrUpdate(project);
    }

    public void delete(int index) {
        project = list_projects.get(index);
        ProjectService projectService = new ProjectService();
        projectService.delete(project);
        list_projects.remove(project);
        project = null;
    }

    public void createNewProject(@NonNull String to,@NonNull String from,@NonNull String relations,@NonNull String category,@NonNull String status) {
        project = new Project();
        project.setTo(to);
        project.setFrom(from);
        project.setRelations(relations);
        project.setCategory(category);
        project.setStatus(status);
        createNewProject();
    }

    private void createNewProject() {
        ProjectService projectService = new ProjectService();
        projectService.saveOrUpdate(project);
        list_projects.add(project);
    }
}
