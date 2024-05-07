package com.example.demo.controller

import com.example.demo.dto.TaskDTORequest
import com.example.demo.dto.TaskDTOResponse
import com.example.demo.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class TaskController(val service: TaskService) {
    @GetMapping("api/tasks")
    fun index(): List<TaskDTOResponse> = service.getTasks()

    @GetMapping("api/tasks/{id}")
    fun getTask(@PathVariable id: Long): TaskDTOResponse {
        return service.getTaskById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }

    @PostMapping("api/tasks")
    fun createTask(@RequestBody newTask: TaskDTORequest): TaskDTOResponse {
        return service.createTask(newTask)
    }

    @PutMapping("api/tasks/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody updatedTask: TaskDTORequest): TaskDTOResponse {
        return service.updateTask(id, updatedTask)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }

    @DeleteMapping("api/tasks/{id}")
    fun deleteTask(@PathVariable id: Long) {
        service.deleteTask(id)
    }
}