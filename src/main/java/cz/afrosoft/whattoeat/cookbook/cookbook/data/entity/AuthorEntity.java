package cz.afrosoft.whattoeat.cookbook.cookbook.data.entity;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity for {@link Author}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DESCRIPTION", columnDefinition = "CLOB")
    private String description;

    @ManyToMany(targetEntity = CookbookEntity.class, mappedBy = "authors")
    private Set<CookbookEntity> cookbooks;

    public Integer getId() {
        return id;
    }

    public AuthorEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AuthorEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthorEntity setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AuthorEntity setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Set<CookbookEntity> getCookbooks() {
        return cookbooks;
    }

    public AuthorEntity setCookbooks(final Set<CookbookEntity> cookbooks) {
        this.cookbooks = cookbooks;
        return this;
    }
}
