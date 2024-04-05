package com.bot.nuitdesmeetups

import ai.tock.bot.definition.BotDefinition
import ai.tock.bot.engine.feature.FeatureType
import ai.tock.bot.registerAndInstallBot
import ai.tock.shared.addJacksonConverter
import ai.tock.shared.create
import ai.tock.shared.property
import ai.tock.shared.retrofitBuilderWithTimeoutAndLogger
import ai.tock.translator.Translator
import com.bot.nuitdesmeetups.service.BedrockAgent
import com.bot.nuitdesmeetups.service.BedrockApi
import com.bot.nuitdesmeetups.service.bedrockAgentObjectMapper
import io.vertx.ext.web.handler.LoggerFormat
import mu.KotlinLogging
import org.slf4j.bridge.SLF4JBridgeHandler
import java.util.logging.LogManager

fun main() {
    Start.start()
}

val logger = KotlinLogging.logger {}
val urlToEndpoint = "http://127.0.0.1:5000/agent/"

enum class FeatureType : FeatureType {
    DEMO_BEDROCK;
}

fun buildBedrockAgentService(): BedrockAgent {
    val bedrockAgentServiceApiUrl: String = property("bedrock_agent_url", urlToEndpoint)

    val bedrockAgentApi: BedrockApi = retrofitBuilderWithTimeoutAndLogger(
        100000L,
        KotlinLogging.logger {}
    )
        .baseUrl(bedrockAgentServiceApiUrl)
        .addJacksonConverter(bedrockAgentObjectMapper)
        .build()
        .create()
    return BedrockAgent(bedrockAgentApi)
}

/**
 * This is the entry point of the bot.
 */
object Start {

    fun start() {
        logger.warn { "System properties : ${System.getProperties()}" }
        System.setProperty("tock_default_zone", "Europe/Paris")
        val loggerFormat = LoggerFormat.DEFAULT

        //enable i18n
        Translator.enabled = true

        //default breath to 2s
        BotDefinition.defaultBreath = 2000L

        //register the bot
        registerAndInstallBot(
            nuitdesmeetups, emptyList(), { router ->
                run {
                    router.route("/").handler { it.response().setStatusCode(200).end() }
                }
            }
        )
        LogManager.getLogManager().reset()
        SLF4JBridgeHandler.install()
    }
}
