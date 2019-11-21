package jp.co.sample.presentation.controller

import jp.co.sample.domain.model.User
import jp.co.sample.domain.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun findAll(): List<User> = userService.findAll()

    @PostMapping("/users")
    fun create(@RequestBody user: User): User = userService.create(user)

    @GetMapping("/users/{id}")
    fun findOne(@PathVariable id: Long): User = userService.findById(id).orElseThrow()

    @PutMapping("/users/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: User): User = userService.update(id, user)

    @DeleteMapping("/user{id}")
    fun delete(@PathVariable id: Long) = userService.delete(id)
}