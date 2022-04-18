package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "currency")
@NoArgsConstructor
@Getter
@Setter
public class Currency implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;

    @OneToMany(mappedBy = "currency",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Relations> relations_list = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        if (!Objects.equals(date, currency.date)) return false;
        return Objects.equals(relations_list, currency.relations_list);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (relations_list != null ? relations_list.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", date=" + date +
                ", relations_list=" + relations_list.size() +
                '}';
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
