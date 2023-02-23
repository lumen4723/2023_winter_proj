package com.eyo.winterproj.service

import com.sendgrid.*
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.helpers.mail.objects.Personalization
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class EmailService(@Value("\${sendgrid.apiKey}") private val sendGridApiKey: String) {

    @Throws(IOException::class)
    fun sendEmail(toEmail: String) {

        
        val from = Email("test@eyo.kr")
        val to = Email(toEmail)
        val mail = Mail()
        mail.from = from
        mail.subject = "Verify your email address"
        val p = Personalization()
        p.addTo(to)
//        p.addDynamicTemplateData("name", name)
//        p.addDynamicTemplateData("confirmationCode", confirmationCode)
        mail.personalization = listOf(p)

        val classPathResource = ClassPathResource("templates/confirmation-email.html")
        val emailTemplate = classPathResource.inputStream.bufferedReader().use { it.readText() }
        mail.addContent(Content("text/html", emailTemplate))

        val sg = SendGrid(sendGridApiKey)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            println("SendGrid status code: " + response.body)
        } catch (ex: IOException) {
            throw ex
        }
    }
}
