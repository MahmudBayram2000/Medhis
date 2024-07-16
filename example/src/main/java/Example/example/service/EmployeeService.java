package Example.example.service;

import Example.example.Dto.EmployeeDto;
import Example.example.entity.Organization;
import Example.example.repository.DepartmentRepository;
import Example.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {
        Organization.Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        Organization.Employee employee = new Organization.Employee(employeeDto.getFirstName(), employeeDto.getLastName(), department);
        employee = employeeRepository.save(employee);

        return convertToDto(employee);
    }


    @Transactional(readOnly = true)
    public Optional<EmployeeDto> findById(Long id) {
        return employeeRepository.findById(id).map(this::convertToDto);
    }


    @Transactional(readOnly = true)
    public List<EmployeeDto> findByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return employeeRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else if (firstName != null) {
            return employeeRepository.findByFirstName(firstName).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else if (lastName != null) {
            return employeeRepository.findByLastName(lastName).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
    private EmployeeDto convertToDto(Organization.Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null);
    }

}
