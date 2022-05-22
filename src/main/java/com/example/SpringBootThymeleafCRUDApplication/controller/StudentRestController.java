package com.example.SpringBootThymeleafCRUDApplication.controller;

import com.example.SpringBootThymeleafCRUDApplication.entity.Student;
import com.example.SpringBootThymeleafCRUDApplication.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/students/api/v2/")
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("insert")
    public Student addStudentByApi(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // It is Kind of Optional
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
