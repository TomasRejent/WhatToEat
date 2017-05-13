package cz.afrosoft.whattoeat.core.logic.service;

import java.util.UUID;

/**
 * Implementation of {@link UUIDService} based on {@link UUID}.
 * Created by Tomas Rejent on 25. 4. 2017.
 */
public class UuidServiceImpl implements UUIDService {

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
