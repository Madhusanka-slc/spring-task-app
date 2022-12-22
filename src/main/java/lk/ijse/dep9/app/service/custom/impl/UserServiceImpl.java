package lk.ijse.dep9.app.service.custom.impl;


import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dao.util.Transformer;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exception.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Transactional//AOP
public class UserServiceImpl implements UserService {

    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;

    private final UserDAO userDAO;
    private final Transformer transformer;

    public UserServiceImpl(ProjectDAO projectDAO, TaskDAO taskDAO, UserDAO userDAO, Transformer transformer) {
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.save(transformer.toUser(userDTO));
//        if (true)throw new RuntimeException("Failed");
//        userDAO.save(new User("testing","testing","testing"));
    }

    @Override
    public UserDTO verifyUser(String username, String password) {
        UserDTO user = userDAO.findById(username).map(transformer::toUserDTO)
                .orElseThrow(AuthenticationException::new);
        if (DigestUtils.sha256Hex(password).equals(user.getPassword())) {
            return user;
//            System.out.println("Ok");

        }
        throw  new AuthenticationException();
    }

    @Override
    public UserDTO getUserAccountDetails(String username) {
        return userDAO.findById(username).map(transformer::toUserDTO).get();
    }

    @Override
    public void updateUserAccountDetails(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.update(transformer.toUser(userDTO));
    }

    @Override
    public void deleteUserAccount(String username) {
        List<Project> projectList = projectDAO.findAllProjectByUsername(username);
        for (Project project : projectList) {
            List<Task> taskList = taskDAO.findAllTaskByProjectId(project.getId());
            taskList.forEach(task -> taskDAO.deleteById(task.getId()));
            projectDAO.deleteById(project.getId());
        }

        userDAO.deleteById(username);

    }
}
