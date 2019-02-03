package com.github.sunnus3.example.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author: zhangwei
 * @date: 15:33/2019-02-03
 */
@SpringBootApplication
@RestController()
@RequestMapping("/kotiln")
open class KotilnApplication {

    @Autowired
    private lateinit var calculator: Calculator

    @GetMapping("/sum")
    fun sum(@RequestParam a: Int, @RequestParam b: Int): Int {
        return calculator.sum(a, b)
    }

    @Bean
    open fun calculator():Calculator{
        return Calculator()
    }
}

class Calculator {

    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}

fun main(array: Array<String>) {
    SpringApplicationBuilder(KotilnApplication::class.java)
            .run(*array)
}