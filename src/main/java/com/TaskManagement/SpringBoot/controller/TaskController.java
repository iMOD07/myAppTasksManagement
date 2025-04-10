package com.TaskManagement.SpringBoot.controller;

import com.TaskManagement.SpringBoot.model.Task;
import com.TaskManagement.SpringBoot.dto.TaskRequest;
import com.TaskManagement.SpringBoot.dto.TransferRequest;
import com.TaskManagement.SpringBoot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // إنشاء مهمة - Admin فقط
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        Task task = taskService.createTask(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getAssignedToId(),
                request.getDueDate()
        );
        return ResponseEntity.ok(task);
    }

    // حذف مهمة - Admin فقط
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

    // تحويل مهمة لموظف آخر - Employee فقط
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/transfer/{taskId}")
    public ResponseEntity<Task> transferTask(@PathVariable Long taskId, @RequestBody TransferRequest request) {
        Task task = taskService.transferTask(taskId, request.getNewAssignedToId());
        return ResponseEntity.ok(task);
    }

    // جلب جميع المهام - لجميع المستخدمين المصادق عليهم
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // جلب مهام مستخدم معين - لجميع المستخدمين المصادق عليهم
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    // إنهاء مهمة بواسطة العميل - Client فقط
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/complete/{taskId}")
    public ResponseEntity<Task> completeTask(@PathVariable Long taskId) {
        Task completedTask = taskService.completeTask(taskId);
        return ResponseEntity.ok(completedTask);
    }

    // إنهاء مهمة بواسطة المدير - Admin فقط (مسار مختلف)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/complete/{taskId}")
    public ResponseEntity<Task> completeTaskByAdmin(@PathVariable Long taskId) {
        Task completedTask = taskService.completeTask(taskId); // تم توحيد طريقة انهاء المهمة لاختلاف التسمية
        return ResponseEntity.ok(completedTask);
    }
}
