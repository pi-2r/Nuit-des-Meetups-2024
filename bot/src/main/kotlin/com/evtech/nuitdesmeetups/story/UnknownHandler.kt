package com.bot.nuitdesmeetups.story

import ai.tock.bot.definition.ConnectorDef
import ai.tock.bot.definition.HandlerDef
import ai.tock.bot.definition.storyDef
import ai.tock.bot.engine.BotBus
import com.bot.nuitdesmeetups.FeatureType.DEMO_BEDROCK
import com.bot.nuitdesmeetups.buildBedrockAgentService

val bedrock = storyDef<UnknownDef>("bedrock") {}
const val UNKNOW_TEXT = "Je n'ai pas compris votre demande :/, pouvez-vous reformuler ?"

class UnknownDef(bus: BotBus) : HandlerDef<UnknownConnector>(bus) {
    override fun answer() {

        if (isFeatureEnabled(DEMO_BEDROCK, false)) {
            val demoSendQuestion = buildBedrockAgentService().sendQuestionToTheEndpoint(bus.userText.toString())
            end(demoSendQuestion)
        }
        end(UNKNOW_TEXT)
    }
}

/**
 * Connector specific behaviour.
 */
abstract class UnknownConnector(context: UnknownDef) : ConnectorDef<UnknownDef>(context) {
}
