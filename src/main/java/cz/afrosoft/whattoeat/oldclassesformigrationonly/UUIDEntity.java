package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Implementation of {@link PersistentEntity} which use UUID as entity key.
 * Created by Tomas Rejent on 25. 4. 2017.
 */
public abstract class UUIDEntity implements PersistentEntity<String>{

    private String uuid;

    public UUIDEntity() {
        this.uuid = ServiceHolder.getUUIDService().generateUUID();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getKey() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UUIDEntity entity = (UUIDEntity) o;

        return new EqualsBuilder()
                .append(uuid, entity.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uuid)
                .toHashCode();
    }
}
