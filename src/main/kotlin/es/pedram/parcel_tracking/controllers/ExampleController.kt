package es.pedram.parcel_tracking.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

class User(
    val name: String,
    val age: Int,
    val employment: Company,
    val previousEmployments: List<Company>,
)

class Company(val name: String)

@RestController
class ExampleController {
    @GetMapping("/hello")
    fun hello(name: String?): String = "Hello ${name ?: "World"}"

    @GetMapping("/hello/{id}")
    fun hello(@PathVariable("id") id: Long) = "Hello from $id"

    @PostMapping
    fun post(@RequestBody user: User): List<Company> = user.previousEmployments
}
