package lk.ijse.dep9.app.api;


import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private ProjectTaskService projectTaskService;

    public ProjectController(ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json",produces = "application/json")
    private ProjectDTO createNewProject(
            @Validated(ValidationGroups.Create.class) @RequestBody ProjectDTO projectDTO,
            @RequestAttribute String username){
        projectDTO.setUsername(username);
      return projectTaskService.createNewProject(projectDTO);
    }

    @GetMapping(produces = "application/json")
    private List<ProjectDTO> getAllProjects(@RequestAttribute String username){
       return projectTaskService.getAllProjects(username);
    }

    @GetMapping(value = "/{projectId:\\d+}",produces = "application/json")
    private ProjectDTO getProject(@PathVariable Integer projectId,
                                  @RequestAttribute String username){
        return  projectTaskService.getProjectDetails(username,projectId);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{projectId:\\d+}",consumes = "application/json")
    private void renameProject(@PathVariable int projectId,
                               @Validated @RequestBody ProjectDTO project,
                               @RequestAttribute String username){

        project.setId(projectId);
        project.setUsername(username);

        projectTaskService.renameProject(project);

    }
@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{projectId:\\d+}")
    private void deleteProject(@PathVariable int projectId,
                               @RequestAttribute String username){
        projectTaskService.deleteProject(username,projectId);
    }
}
