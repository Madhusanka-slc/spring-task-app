package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
//@Scope("request")
@Transactional
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;
    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return null;
    }

    @Override
    public ProjectDTO getProjectDetails(String username, Integer projectId) {
        return null;
    }

    @Override
    public void renameProject(ProjectDTO project) {

    }

    @Override
    public void deleteProject(String username, int projectId) {

    }
}
