package Example.example.controller;

import Example.example.Dto.EmployeeDto;
import Example.example.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

        if (employeeDto.getDepartmentId() == null) {
            logger.error("Department ID is null");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            EmployeeDto createdEmployee = employeeService.create(employeeDto);
            logger.info("Successfully created employee: {}", createdEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating employee: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> findByFirstNameAndLastName(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        List<EmployeeDto> employees = employeeService.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(employees);
    }



}


