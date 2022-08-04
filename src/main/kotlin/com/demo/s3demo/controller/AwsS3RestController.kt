package com.demo.s3demo.controller

import com.demo.s3demo.service.AwsS3Service
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/s3"])
class AwsS3RestController(val awsS3Service: AwsS3Service) {

    @PostMapping(value = ["/file"])
    fun uploadFile(@RequestPart(value = "multipartFile") multipartFile: MultipartFile ): ResponseEntity<Any> {
        val uploadFile: String = awsS3Service.uploadFile(multipartFile, "static")

        return ResponseEntity.ok().body(uploadFile);
    }



}