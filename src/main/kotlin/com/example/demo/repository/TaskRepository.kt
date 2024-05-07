package com.example.demo.repository

import com.example.demo.entity.TaskEntity
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface TaskRepository : JpaRepository<TaskEntity, Long>
