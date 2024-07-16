package Example.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

public class Organization {

    @Entity
    public static class Employee{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String firstName;

        private String lastName;

        @ManyToOne
        @JoinColumn(name = "department_id")
        private Department department;

        public Employee() {
        }

        public Employee(String firstName, String lastName, Department department) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.department = department;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }
    }

    @Entity
    @Table(name = "departments")
    public static class Department {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String name;
        private String location;

        @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
        @JsonIgnore
        private List<Employee> employees;

        public Department() {

        }
        public Department(String name, String location) {
            this.name = name;
            this.location = location;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }
}
