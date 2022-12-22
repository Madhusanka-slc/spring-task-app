package lk.ijse.dep9.app.service.custom;

import lk.ijse.dep9.app.dto.ProjectDTO;

import java.util.List;

public interface ProjectTaskService {
    ProjectDTO createNewProject(ProjectDTO projectDTO);

    List<ProjectDTO> getAllProjects(String username);

    ProjectDTO getProjectDetails(String username, Integer projectId);

    void renameProject(ProjectDTO project);

    void deleteProject(String username, int projectId);
}
