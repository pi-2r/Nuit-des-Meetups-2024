package com.bot.nuitdesmeetups

import ai.tock.bot.definition.bot
import com.bot.nuitdesmeetups.nuitdesmeetupsConfiguration.BOT_ID
import com.bot.nuitdesmeetups.nuitdesmeetupsConfiguration.NAMESPACE
import com.bot.nuitdesmeetups.story.bedrock

val nuitdesmeetups = bot(
    botId = BOT_ID,
    namespace = NAMESPACE,
    stories = listOf(
        bedrock
    ),
    unknownStory = bedrock
)

