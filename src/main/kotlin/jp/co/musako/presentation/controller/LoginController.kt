package jp.co.musako.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @GetMapping(name = "/authentication")
    fun invoke(): ResponseEntity<Object> {
    return ResponseEntity(HttpStatus.OK)
    }
}