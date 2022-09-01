package es.pedram.parcel_tracking.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping
    fun helloWorld(): String = "Hello World"
}
