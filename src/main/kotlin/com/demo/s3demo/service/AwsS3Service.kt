package com.demo.s3demo.service

import org.springframework.web.multipart.MultipartFile


interface AwsS3Service {
    fun uploadFile(multipartFile: MultipartFile, dirName: String): String
}