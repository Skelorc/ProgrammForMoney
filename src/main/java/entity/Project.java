package entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Project implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "from_location")
    private String from;
    @Column(name = "to_project")
    private String to;

    private String currency;
    private String NCC;
    private String type;
    private LocalDate date;
    private String budget;
    private int amount;
    private String description;
    private String relations;
    private String category;
    private String status;
    private String name_table;

    public Project(String from, String to, String currency, String NCC, String type, LocalDate date, String budget,
                   int amount, String description, String relations, String category, String status, String name_table) {
        this.from = from;
        this.to = to;
        this.currency = currency;
        this.NCC = NCC;
        this.type = type;
        this.date = date;
        this.budget = budget;
        this.amount = amount;
        this.description = description;
        this.relations = relations;
        this.category = category;
        this.status = status;
        this.name_table = name_table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Objects.equals(from, project.from) && Objects.equals(to, project.to) && Objects.equals(currency, project.currency) && Objects.equals(NCC, project.NCC) && Objects.equals(type, project.type) && Objects.equals(date, project.date) && Objects.equals(budget, project.budget) && Objects.equals(amount, project.amount) && Objects.equals(description, project.description) && Objects.equals(relations, project.relations) && Objects.equals(category, project.category) && Objects.equals(status, project.status) && Objects.equals(name_table, project.name_table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, currency, NCC, type, date, budget, amount, description, relations, category, status, name_table);
    }

    public String getMonth()
    {
        return String.valueOf(date.getMonth().getValue());
    }
    public String getYear()
    {
        return String.valueOf(date.getYear());
    }

}
