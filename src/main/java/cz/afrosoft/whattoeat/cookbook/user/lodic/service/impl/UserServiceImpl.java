package cz.afrosoft.whattoeat.cookbook.user.lodic.service.impl;

import cz.afrosoft.whattoeat.cookbook.user.data.entity.UserEntity;
import cz.afrosoft.whattoeat.cookbook.user.data.repository.UserRepository;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.cookbook.user.lodic.service.UserService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Set;

/**
 * @author Tomas Rejent
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    private void tmpInitUsers(){
        if(repository.findAll().isEmpty()){
            UserEntity alex = new UserEntity().setName("Alex");
            UserEntity tom = new UserEntity().setName("Tom");
            repository.save(alex);
            repository.save(tom);
        }
    }

    @Override
    public Set<User> getAllUsers() {
        return ConverterUtil.convertToSet(repository.findAll(), this::entityToUser);
    }

    @Override
    public User entityToUser(UserEntity entity){
        return new UserImpl(entity.getId(), entity.getName());
    }

    public UserEntity userToEntity(User user){
        if(user == null){
            return null;
        }else{
            return new UserEntity().setId(user.getId()).setName(user.getName());
        }
    }
}
