package lk.ijse.dep9.app.dao.custom;

import lk.ijse.dep9.app.dao.CrudDAO;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.entity.User;

import java.util.List;

public interface TaskDAO extends CrudDAO<Task,Integer> {//row type

    List<Task> findAllTaskByProjectId(Integer projectId);
}
