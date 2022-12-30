package net.breezeware.security.controller;

import java.util.Arrays;
import java.util.List;
import net.breezeware.security.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    //Add Student Details
    private static final List<Student> students =
            Arrays.asList(new Student(1, "sathish"), new Student(2, "kumar"), new Student(3, "Thiyash"));

    @GetMapping("/{student-id}")
    public Student getStudentById(@PathVariable(name = "student-id") long id) {
        return students.stream().filter((student -> student.getId() == id)).findFirst()
                .orElseThrow(() -> new IllegalStateException("Id is Not Found"));
    }
}
