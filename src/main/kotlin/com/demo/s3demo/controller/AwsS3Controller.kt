package com.demo.s3demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AwsS3Controller {

    @GetMapping(value = ["index"])
    fun index(): String {
        return "/aws/index"
    }

}