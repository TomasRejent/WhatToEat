package cz.afrosoft.whattoeat.core.data.entity;

import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
