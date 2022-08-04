package com.demo.s3demo.service


import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Service
class AwsS3ServiceImpl: AwsS3Service {

    @Value(value = "\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String;

    @Autowired
    private lateinit var amazonS3Client: AmazonS3Client

    override fun uploadFile(multipartFile: MultipartFile, dirName: String): String {
        val convertFile: File = convertMultipartFileToFile(multipartFile)

        return upload(convertFile, dirName)
    }

    private fun upload(file: File, dirName: String): String {
        val fileName: String = "${dirName}/${file.name}"
        val uploadImageUrl = awsS3Upload(file, fileName)
        removeNewFile(file)
        return uploadImageUrl
    }

    private fun removeNewFile(file: File) {
        if(file.delete()) {
            println("파일이 삭제 되었습니다.")
        }else {
            println("파일이 삭제되지 않았습니다.")
        }
    }

    private fun awsS3Upload(file: File, fileName: String): String {
        val putObjectRequest = PutObjectRequest(bucket, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead)

        amazonS3Client.putObject(putObjectRequest)
        return amazonS3Client.getUrl(bucket, fileName).toString()
    }

    fun convertMultipartFileToFile(multipartFile: MultipartFile): File {
//        val file: File = File(multipartFile.originalFilename)
//        multipartFile.transferTo(file)
//        return file

        val file: File = File(multipartFile.originalFilename)
        if(file.createNewFile()){
            val fileOutputStream: FileOutputStream = FileOutputStream(file)
            fileOutputStream.write(multipartFile.bytes)
        }
        return file
    }



    //    fun String.createFileName(): String {
//        return UUID.randomUUID().toString().plus(this)
//    }

    fun createFileName(fileName: String): String {
        return UUID.randomUUID().toString().plus(fileName)
    }


}