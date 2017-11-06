package cz.afrosoft.whattoeat.core.data.entity;

import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import javax.persistence.*;

/**
 * Entity representing {@link Keyword}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "KEYWORD")
public class KeywordEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public KeywordEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public KeywordEntity setName(final String name) {
        this.name = name;
        return this;
    }
}
