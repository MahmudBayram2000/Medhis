package Example.example.repository;

import Example.example.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Organization.Employee, Long> {

    List<Organization.Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Organization.Employee> findByFirstName(String firstName);

    List<Organization.Employee> findByLastName(String lastName);

    void deleteById(Long id);


}




