package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "rel")
@NoArgsConstructor
@Getter
@Setter
public class Rel implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private LocalDate date;
    private String relation;
    private String value;

    public Rel(LocalDate date, String relation, String value) {
        this.date = date;
        this.relation = relation;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rel rel = (Rel) o;
        return Objects.equals(date, rel.date) && Objects.equals(relation, rel.relation) && Objects.equals(value, rel.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, relation, value);
    }

    @Override
    public String toString() {
        return "Rel{" +
                "id=" + id +
                ", date=" + date +
                ", relation='" + relation + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
