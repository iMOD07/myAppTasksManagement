package com.TaskManagement.SpringBoot.service;

import com.TaskManagement.SpringBoot.model.Task;
import com.TaskManagement.SpringBoot.model.UserEmployee;
import com.TaskManagement.SpringBoot.repository.TaskRepository;
import com.TaskManagement.SpringBoot.repository.UserEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserEmployeeRepository userEmployeeRepository;

    // إنشاء مهمة جديدة (يستخدمها الـ Admin)
    public Task createTask(String title, String description, String status, Long assignedToId, LocalDateTime dueDate) {
        Optional<UserEmployee> user = userEmployeeRepository.findById(assignedToId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setAssignedTo(user.get());
        task.setDueDate(dueDate);
        return taskRepository.save(task);
    }

    // جلب جميع المهام
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // جلب المهام الخاصة بمستخدم معين
    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }

    // تحديث مهمة موجودة (يستخدمها الـ Admin)
    public Task updateTask(Long taskId, String title, String description, String status, LocalDateTime dueDate) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found!");
        }
        Task task = taskOptional.get();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setDueDate(dueDate);
        return taskRepository.save(task);
    }

    // حذف مهمة (يستخدمها الـ Admin)
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found!");
        }
        taskRepository.deleteById(taskId);
    }

    // تحويل مهمة لموظف آخر (يستخدمها الـ Employee)
    public Task transferTask(Long taskId, Long newAssignedToId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found!");
        }
        Optional<UserEmployee> newUser = userEmployeeRepository.findById(newAssignedToId);
        if (newUser.isEmpty()) {
            throw new RuntimeException("Target user not found!");
        }
        Task task = taskOptional.get();
        task.setAssignedTo(newUser.get());
        return taskRepository.save(task);
    }

    // إنهاء مهمة بواسطة العميل أو المدير (تحديث الحالة إلى "Completed")
    public Task completeTask(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found!");
        }
        Task task = taskOptional.get();
        task.setStatus("Completed");
        return taskRepository.save(task);
    }
}
