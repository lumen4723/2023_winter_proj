package com.eyo.winterproj.service
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import org.json.simple.JSONArray
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class SmsSenderService() {
    val url =
        "/sms/v2/services/ncp:sms:kr:252909571871:ow-buster/messages" // Replace {serviceId} with your Ncloud SMS service ID
    val accessKey = "ZJAbk8iGPKZjsC9bhxHI" // Replace with your Ncloud API access key
    val secretKey = "vfq44SM1EPOZBMZd6f2gqUYFzoqg3dyTYjzcO9p8" // Replace with your Ncloud API secret key

    fun sendSMS() {

        // Set the sender and recipient phone numbers and message content
        val sender = "01098000336" // Replace with your phone number
        val recipient = "01098334241" // Replace with the recipient's phone number
        val message = "Hello from Ncloud SMS API!"

        // Set the request body as a JSON object
        val requestBody = JSONObject()
        requestBody.put("type", "SMS")
        requestBody.put("contentType", "COMM")
        requestBody.put("from", sender)
        requestBody.put("content", message)

        val messages = JSONArray()
        val messageObject = JSONObject()
        messageObject.put("to", recipient)
        messages.add(messageObject)
        requestBody.put("messages", messages)

        // Set the request headers
        val timestamp = System.currentTimeMillis().toString()
        val signature = getSignature(accessKey, secretKey, timestamp, url)
        val headers = Headers.Builder();
        headers.add("Content-Type", "application/json; charset=utf-8")
        headers.add("x-ncp-apigw-timestamp", timestamp)
        headers.add("x-ncp-iam-access-key", accessKey)
        headers.add("x-ncp-apigw-signature-v2", signature)
        headers.build()

        // Send the API request
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://sens.apigw.ntruss.com$url")
//            .post(
//                RequestBody.create(
//                    "application/json; charset=utf-8".toMediaTypeOrNull()!!,
//                    requestBody.toString()
//                )
//            )
            .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody.toString()))
            .headers(headers.build())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: okio.IOException) {
                println("API request failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                println("API response: $responseBody")
            }
        })
    }
}


// Helper function to generate the API signature
//fun getSignature(accessKey: String, secretKey: String, timestamp: String, url: String, space : String): String {
//
//    val message = StringBuilder()
//        .append("GET")
//        .append(space)
//        .append(url)
//        .append("\n")
//        .append(timestamp)
//        .append("\n")
//        .append(accessKey)
//        .toString()

    fun getSignature(accessKey: String, secretKey: String, timestamp: String, url: String): String {
        val space = " "

        val message = StringBuilder()
            .append("POST")
            .append(space)
            .append(url)
            .append("\n")
            .append(timestamp)
            .append("\n")
            .append(accessKey)
            .toString()

//    val hmac = Mac.getInstance("HmacSHA256")
//    val key = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
//    hmac.init(key)
//    val signatureBytes = hmac.doFinal(message.toByteArray())
//    return java.util.Base64.getEncoder().encodeToString(signatureBytes)


    val signingKey = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(signingKey)
    val rawHmac = mac.doFinal(message.toByteArray(charset("UTF-8")))

    return Base64.getEncoder().encodeToString(rawHmac)
}