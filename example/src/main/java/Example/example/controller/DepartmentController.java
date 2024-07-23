package Example.example.controller;

import Example.example.Dto.DepartmentDto;
import Example.example.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }


    @PostMapping("/create")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        logger.info("Received request to create department: {}", departmentDto);
        try {
            DepartmentDto createdDepartment = departmentService.createDepartment(departmentDto);
            logger.info("Successfully created department: {}", createdDepartment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DepartmentDto>> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<DepartmentDto>> findByName(
            @RequestParam(required = false) String name) {
        logger.info("Received request to search departments with name: {}", name);
        List<DepartmentDto> departments = departmentService.findByName(name);
        logger.info("Found departments: {}", departments);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/searchByLocation")
    public ResponseEntity<List<DepartmentDto>> findByLocation(
            @RequestParam(required = false) String location) {
        logger.info("Received request to search departments with location: {}", location);
        List<DepartmentDto> departments = departmentService.findByLocation(location);
        logger.info("Found departments: {}", departments);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/searchByNameAndLocation")
    public ResponseEntity<List<DepartmentDto>> findByNameAndLocation(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        logger.info("Received request to search departments with name: {} and location: {}", name, location);
        List<DepartmentDto> departments = departmentService.findByNameAndLocation(name, location);
        logger.info("Found departments: {}", departments);
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}


