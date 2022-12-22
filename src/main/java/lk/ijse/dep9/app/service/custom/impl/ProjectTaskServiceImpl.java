package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dao.util.Transformer;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Component
//@Scope("request")
@Transactional
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;

    private Transformer transformer;

    public ProjectTaskServiceImpl(ProjectDAO projectDAO, TaskDAO taskDAO, Transformer transformer) {
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
        this.transformer = transformer;
    }

    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return transformer.toProjectDTO(projectDAO.save(transformer.toProject(projectDTO)));
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return projectDAO.findAllProjectByUsername(username).stream()
                .map(transformer::toProjectDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectDetails(String username, Integer projectId) {
//        ProjectDTO project = projectDAO.findById(projectId).map(transformer::toProjectDTO).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
//        return project;

        return projectDAO.findById(projectId).map(transformer::toProjectDTO).get();

    }

    @Override
    public void renameProject(ProjectDTO project) {
//        Project projectEntity = projectDAO.findById(project.getId()).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!projectEntity.getUsername().matches(project.getUsername())) {
//            throw new AccessDeniedException();
//        }

        projectDAO.update(transformer.toProject(project));

    }

    @Override
    public void deleteProject(String username, int projectId) {
//        Project project = projectDAO.findById(projectId).orElseThrow(()->new EmptyResultDataAccessException(1));
//        if (!project.getUsername().matches(project.getUsername())) {
//            throw new AccessDeniedException();
//        }

        taskDAO.findAllTaskByProjectId(projectId).forEach(task -> taskDAO.deleteById(task.getId()));
        projectDAO.deleteById(projectId);

    }

    @Override
    public TaskDTO createNewTask(String username, TaskDTO task) {
        return transformer.toTaskDTO(taskDAO.save(transformer.toTask(task)));
    }

    @Override
    public void renameTask(String username, TaskDTO task) {
        Task taskEntity = taskDAO.findById(task.getId()).get();
        taskEntity.setContent(taskEntity.getContent());
        taskDAO.update(transformer.toTask(task));

    }

    @Override
    public void deleteTask(String username, TaskDTO taskDTO) {
        taskDAO.deleteById(taskDTO.getId());

    }

    @Override
    public TaskDTO getTaskDetails(String username, TaskDTO taskDTO) {
      return taskDAO.findById(taskDTO.getId()).map(transformer::toTaskDTO).get();
    }

    @Override
    public List<TaskDTO> getAllTasks(String username, int projectId) {
        return taskDAO.findAllTaskByProjectId(projectId).stream().map(transformer::toTaskDTO).collect(Collectors.toList());
    }

    @Override
    public void updateTaskStatus(String username, TaskDTO taskDTO, boolean completed) {
        Task task = taskDAO.findById(taskDTO.getId()).get();
        task.setStatus(completed? Task.Status.COMPLETED: Task.Status.NOT_COMPLETED);
        taskDAO.update(task);

    }
}
