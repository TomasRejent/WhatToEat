package cz.afrosoft.whattoeat.cookbook.cookbook.data.entity;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorUpdateObject;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity for {@link Author}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "AUTHOR")
public class AuthorEntity implements Author, AuthorUpdateObject {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(mappedBy = "authors")
    private Set<CookbookEntity> cookbooks;

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

    @Override
    public AuthorUpdateObject setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public AuthorUpdateObject setEmail(final String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public AuthorUpdateObject setDescription(final String description) {
        this.description = description;
        return this;
    }

    @Override
    public Set<? extends Cookbook> getCookbooks() {
        return cookbooks;
    }

    @Override
    public Author getAuthor() {
        return this;
    }
}
