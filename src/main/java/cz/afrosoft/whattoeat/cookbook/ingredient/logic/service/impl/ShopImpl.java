package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Shop;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.ShopUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Tomas Rejent
 */
public class ShopImpl implements Shop {

    private final Integer id;
    private final String name;

    public ShopImpl(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ShopImpl shop = (ShopImpl) o;

        return new EqualsBuilder()
                .append(id, shop.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }

    @Override
    public int compareTo(final Shop otherShop) {
        return ShopComparator.INSTANCE.compare(this, otherShop);
    }

    static final class Builder implements ShopUpdateObject {

        private final Integer id;
        private String name;

        public Builder() {
            this.id = null;
        }

        public Builder(final Integer id) {
            Validate.notNull(id);
            this.id = id;
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        Shop build(){
            Validate.notNull(id);
            Validate.notEmpty(name);

            return new ShopImpl(id, name);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("name", name)
                    .toString();
        }
    }
}
