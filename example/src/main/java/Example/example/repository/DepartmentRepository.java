package Example.example.repository;

import Example.example.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Organization.Department, Long> {
    List<Organization.Department> findByName(String name);
    List<Organization.Department> findByLocation(String location);
    List<Organization.Department> findByNameAndLocation(String name, String location);
}
