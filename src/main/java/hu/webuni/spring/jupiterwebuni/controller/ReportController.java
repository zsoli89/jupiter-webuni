package hu.webuni.spring.jupiterwebuni.controller;

import hu.webuni.spring.jupiterwebuni.model.course.CourseStat;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final CourseRepository courseRepository;

    @GetMapping("/averageSemestersPerCourse")
    @Async
    public CompletableFuture<List<CourseStat>> getSemesterReport() {
        System.out.println("ReportController.getSemesterReport called at thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        return CompletableFuture.completedFuture(
                courseRepository.getCourseStats()
        );
    }
}
