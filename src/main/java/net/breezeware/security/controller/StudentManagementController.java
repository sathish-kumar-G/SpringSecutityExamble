package net.breezeware.security.controller;

import java.util.Arrays;
import java.util.List;
import net.breezeware.security.entity.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/api/v1/student")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StudentManagementController {
    //Add Student Details
    private static final List<Student> students =
            Arrays.asList(new Student(1, "sathish"), new Student(2, "kumar"), new Student(3, "Thiyash"));

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        return students;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @PutMapping("/{student-id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable(name ="student-id") long id,@RequestBody Student student){
        System.out.println(String.format("%s %s",id,student));
    }

    @DeleteMapping("/{student-id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable(name ="student-id") long id){
        System.out.println(id);
    }
}
