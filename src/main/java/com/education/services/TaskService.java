package com.education.services;

import com.education.repository.ChangeableTask;
import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.TaskEntity;
import com.education.repository.entity.UserEntity;
import com.education.repository.repo.ProjectRepository;
import com.education.repository.repo.TaskRepository;
import com.education.repository.repo.UserRepository;
import com.education.services.exceptions.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskEntity get(Long id) {
        TaskEntity task = taskRepository.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return taskRepository.get(id);
    }

    public List<TaskEntity> getAll() {
        return taskRepository.getAll();
    }

    public TaskEntity save(Long projectId, Long reporterId, TaskEntity taskEntity) {
        UserEntity reporter = userRepository.get(reporterId);
        if (reporter == null) {
            throw new UserNotFoundException(reporterId);
        }
        if (reporter.getTeam().getProjects().isEmpty()) {
            throw new IllegalArgumentException("Reporter assigned for task creation is not member of any project");
        }
        ProjectEntity projectEntity = projectRepository.get(projectId);
        if (reporter.getTeam().getProjects().contains(projectEntity)) {
            taskEntity.setProject(projectRepository.get(projectId));
        } else {
            throw new ProjectNotFoundException(projectId);
        }
        taskEntity.setReporter(reporter);
        return taskRepository.save(taskEntity);
    }

    public TaskEntity edit(Long id, ChangeableTask task) {
        TaskEntity entity = taskRepository.get(id);
        if (entity == null) {
            throw new TaskNotFoundException(id);
        }
        if (task.getDescription() != null) {
            entity.setDescription(task.getDescription());
        }
        if (task.getTitle() != null) {
            entity.setTitle(task.getTitle());
        }
        return taskRepository.save(entity);
    }

    public TaskEntity setAssignee(Long taskId, Long assigneeId) {
        if (taskRepository.get(taskId) == null) {
            throw new TaskNotFoundException(taskId);
        }
        if (userRepository.get(assigneeId) == null) {
            throw new UserNotFoundException(assigneeId);
        }
        if (userRepository.get(assigneeId).getTeam().getProjects().contains(taskRepository.get(taskId).getProject())) {
            return taskRepository.setAssignee(taskId, assigneeId);
        } else {
            throw new UserNotFoundException(assigneeId);
        }
    }
}
