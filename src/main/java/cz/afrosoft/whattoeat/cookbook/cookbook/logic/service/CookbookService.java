package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;

import java.util.Set;

/**
 * Service providing methods for operating on {@link cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook}.
 *
 * @author Tomas Rejent
 */
public interface CookbookService {

    /**
     * @return (NotNull) Return all cookbooks defined in application.
     */
    Set<Cookbook> getAllCookbooks();

    /**
     * Deletes specified cookbook.
     *
     * @param cookbook (NotNull) Cookbook to be deleted.
     */
    void delete(Cookbook cookbook);

    /**
     * Gets createOrUpdate object for new Cookbook. After data are filled it can be persisted using
     * {@link #createOrUpdate(CookbookUpdateObject)} method.
     *
     * @return (NotNull)
     */
    CookbookUpdateObject getCreateObject();

    /**
     * Gets update object for specified cookbook. Update object is used to modify cookbook.
     * Changes are not persisted until {@link #createOrUpdate(CookbookUpdateObject)} is called.
     *
     * @param cookbook (NotNull) Cookbook to modify.
     * @return (NotNull) Update object which enables you to specify changes to cookbook.
     */
    CookbookUpdateObject getUpdateObject(Cookbook cookbook);

    /**
     * Applies changes specified by cookbookChanges to cookbook for which cookbookChanges was constructed.
     * It can also be used to persist new cookbooks.
     *
     * @param cookbookChanges (NotNull) Changes to persist.
     * @return (NotNull) Cookbook with updated values.
     */
    Cookbook createOrUpdate(CookbookUpdateObject cookbookChanges);

}
