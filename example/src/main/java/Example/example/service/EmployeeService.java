package Example.example.service;

import Example.example.Dto.EmployeeDto;
import Example.example.entity.Organization;
import Example.example.repository.DepartmentRepository;
import Example.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("departmentRepository")
    private DepartmentRepository departmentRepository;


    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {
        if (employeeDto.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }

        Organization.Employee employee = new Organization.Employee(employeeDto.getFirstName(), employeeDto.getLastName(), null);
        employee = employeeRepository.save(employee);
        return convertToDto(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        List<Organization.Employee> employees = employeeRepository.findByFirstName(firstName);
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<EmployeeDto> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        List<Organization.Employee> employees = employeeRepository.findByLastName(lastName);
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findByFirstNameAndLastName(String firstName, String lastName) {
        List<Organization.Employee> employees = employeeRepository.findByFirstNameAndLastName(firstName, lastName);
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto convertToDto(Organization.Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDto> findById(Long id) {
        return employeeRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee with id " + id + " does not exist.");
        }
        employeeRepository.deleteById(id);
    }
}
