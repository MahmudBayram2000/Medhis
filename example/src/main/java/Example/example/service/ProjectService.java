package Example.example.service;

import Example.example.entity.Project;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProjectService {

    private final List<Project> projects = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Project> getAllProjects() {
        return projects;
    }

    public Project createProject(Project project) {
        project.setId(counter.incrementAndGet());
        projects.add(project);
        return project;
    }

    public Optional<Project> findById(Long id) {
        return projects.stream().filter(project -> project.getId().equals(id)).findFirst();
    }

    public void deleteProject(Long id) {
        projects.removeIf(project -> project.getId().equals(id));
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        return project;
    }
}
