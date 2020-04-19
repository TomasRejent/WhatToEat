package cz.afrosoft.whattoeat.cookbook.user.lodic.service;

import cz.afrosoft.whattoeat.cookbook.user.data.entity.UserEntity;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;

import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface UserService {

    Set<User> getAllUsers();

    UserEntity userToEntity(User user);

    User entityToUser(UserEntity entity);
}
