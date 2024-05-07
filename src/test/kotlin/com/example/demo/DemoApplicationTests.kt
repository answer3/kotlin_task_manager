package com.example.demo

import com.example.demo.dto.TaskDTORequest
import com.example.demo.dto.TaskDTOResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(
	classes = [DemoApplication::class],
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TaskDTOResponseCRUDIntegrationTest(@Autowired var restTemplate: TestRestTemplate) {
	var taskId: Long = 0

	@Test
	fun createTask() {
		val taskDTORequest = TaskDTORequest("Task", "description", false)
		val result = this.restTemplate.postForEntity("/task", taskDTORequest, TaskDTOResponse::class.java)
		taskId = result.body?.id!!
		assertTrue { result.body?.name.equals("Task") }
		assertTrue { result.body?.description.equals("description") }
	}

	@Test
	fun returnTaskSuccessfully() {
		createTask()
		val result = this.restTemplate.getForEntity("/task/{id}", TaskDTOResponse::class.java, taskId)
		assertTrue { result.body?.name.equals("Task") }
	}

	@Test
	fun deleteTaskSuccessfully() {
		createTask()
		this.restTemplate.delete("/task/{id}", taskId)
		val result = this.restTemplate.getForEntity("/task/{id}", String::class.java, taskId)
		assertTrue { result.statusCode.equals(HttpStatus.NOT_FOUND) }
	}

	@Test
	fun putTaskSuccessfully() {
		createTask()
		val taskDTORequest = TaskDTORequest("Task", "description", true)
		this.restTemplate.put("/task/{id}", taskDTORequest, taskId)
		val result = this.restTemplate.getForEntity("/task/{id}", TaskDTOResponse::class.java, taskId)
		assertTrue { result.statusCode.is2xxSuccessful }
		assertTrue { result.body?.done!! }
	}
}
