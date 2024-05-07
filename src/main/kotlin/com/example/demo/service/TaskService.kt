package com.example.demo.service

import com.example.demo.dto.TaskDTORequest
import com.example.demo.dto.TaskDTOResponse
import com.example.demo.entity.TaskEntity
import com.example.demo.repository.TaskRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService(val repository: TaskRepository) {
    fun getTasks(): List<TaskDTOResponse> = repository.findAll().map { TaskDTOResponse(id = it.id!!, name = it.name, description = it.description, done = it.done) }.toList()

    fun getTaskById(id: Long): TaskDTOResponse? {
        return repository.findById(id).map { TaskDTOResponse(id = it.id!!, name = it.name, description = it.description, done = it.done) }.getOrNull()
    }

    fun createTask(newTask: TaskDTORequest): TaskDTOResponse {
        val save = repository.save(TaskEntity(id = null, name = newTask.name, description = newTask.description, done = newTask.done))
        return TaskDTOResponse(id = save.id!!, name = save.name, description = save.description, done = save.done)
    }

    fun updateTask(id: Long, updatedTask: TaskDTORequest): TaskDTOResponse? {
        return repository.findById(id).map {
            val save = repository.save(TaskEntity(id = it.id, name = updatedTask.name, description = updatedTask.description, done = updatedTask.done))
            TaskDTOResponse(id = save.id!!, name = save.name, description = save.description, done = save.done)
        }.orElseGet(null)
    }

    fun deleteTask(id: Long) {
        repository.deleteById(id)
    }
}