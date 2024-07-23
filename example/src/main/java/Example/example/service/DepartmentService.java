package Example.example.service;

import Example.example.Dto.DepartmentDto;
import Example.example.entity.Organization;
import Example.example.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final List<Organization.Department> departments = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private DepartmentRepository departmentRepository;


    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Organization.Department department = new Organization.Department(departmentDto.getName(), departmentDto.getLocation());
        department = departmentRepository.save(department);
        return convertToDto(department);
    }

    @Transactional(readOnly = true)
    public Optional<DepartmentDto> findById(Long id) {
        return departmentRepository.findById(id).map(this::convertToDto);
    }

    private DepartmentDto convertToDto(Organization.Department department) {
        return new DepartmentDto(department.getId(), department.getName(), department.getLocation());
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> findByNameAndLocation(String name, String location) {
        if (name != null && location != null) {
            return departmentRepository.findByNameAndLocation(name, location).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else if (name != null) {
            return departmentRepository.findByName(name).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else if (location != null) {
            return departmentRepository.findByLocation(location).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        List<Organization.Department> departments = departmentRepository.findByName(name);
        return departments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> findByLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }

        List<Organization.Department> departments = departmentRepository.findByLocation(location);
        return departments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist.");
        }
        departmentRepository.deleteById(id);
    }

}

