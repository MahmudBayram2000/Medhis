package Example.example.controller;

import Example.example.Dto.EmployeeDto;
import Example.example.entity.Organization;
import Example.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    public static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDto> employeeDto = employeeService.findById(id);
        return employeeDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        logger.info("Received request to create employee: {}", employeeDto);
        EmployeeDto createdEmployee = employeeService.create(employeeDto);
        logger.info("Successfully created employee: {}", createdEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> findByFirstNameAndLastName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        List<EmployeeDto> employees;
        if (firstName != null && lastName != null) {
            employees = employeeService.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            employees = employeeService.findByFirstName(firstName);
        } else if (lastName != null) {
            employees = employeeService.findByLastName(lastName);
        } else {
            employees = new ArrayList<>();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/searchByLastName")
    public ResponseEntity<List<EmployeeDto>> findByLastName(@RequestParam String lastName) {
        logger.info("Received request to search employees with last name: {}", lastName);
        List<EmployeeDto> employees = employeeService.findByLastName(lastName);
        logger.info("Found employees: {}", employees);
        return ResponseEntity.ok(employees);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.info("Received request to delete employee with id: {}", id);
        try {
            employeeService.deleteEmployee(id);
            logger.info("Successfully deleted employee with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting employee: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}





