package com.bot.nuitdesmeetups.service

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



val bedrockAgentDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
val bedrockAgentObjectMapper: ObjectMapper = jacksonObjectMapper()
        .registerModule(
                JavaTimeModule().addDeserializer(
                        LocalDateTime::class.java,
                        LocalDateTimeDeserializer(bedrockAgentDateFormatter)
                )
        )
        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

data class JsonBody(
    @JsonProperty("question")
    val question: String
)
interface BedrockApi {
    @POST("/agent")
    fun callEndpoint(
        @Header("accept") accept: String = "application/json, text/plain, */*",
        @Header("accept-charset") charset: String = "UTF-8",
        @Header("content-type") contentType: String = "application/json;charset=UTF-8",
        @Body request: JsonBody
    ): Call<ResponseBody>
}

class BedrockAgent(private val bedrockApi: BedrockApi) {

    private val logger = KotlinLogging.logger {}

    fun sendQuestionToTheEndpoint(question: String): String {
        return try {
            val response = bedrockApi.callEndpoint(request = JsonBody(question)).execute()
            if (response.isSuccessful) {
                response.body()?.string() ?: "Missing response body"
            } else {
                val errorMessage = "Bedrock Studio API error " + response.code() + " " + response.message()
                logger.error {errorMessage}
                errorMessage
            }
        } catch (e: Exception) {
            logger.error { e.message }
            e.message ?: "Unknown error"
        }
    }

}
