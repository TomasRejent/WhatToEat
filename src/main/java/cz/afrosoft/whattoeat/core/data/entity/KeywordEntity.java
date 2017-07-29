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
public class KeywordEntity implements Keyword {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
