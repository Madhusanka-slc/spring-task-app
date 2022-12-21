package lk.ijse.dep9.app.service.custom.impl;


import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dao.util.Transformer;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.User;
import lk.ijse.dep9.app.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional//AOP
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private Transformer transformer;

    public UserServiceImpl(UserDAO userDAO, Transformer transformer) {
        this.userDAO = userDAO;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDAO.save(transformer.toUser(userDTO));
//        if (true)throw new RuntimeException("Failed");
        userDAO.save(new User("testing","testing","testing"));
    }
}
