package entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "params")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Params implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "str_values", joinColumns = @JoinColumn(name = "BoxValues_id"))
    @Column(name="str_values_id", columnDefinition="TEXT")
    private Set<String> values = new TreeSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Params params = (Params) o;
        return Objects.equals(name, params.name) && Objects.equals(values, params.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }
}
