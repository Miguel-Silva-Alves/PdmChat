package com.example.pdmchat

import com.example.pdmchat.model.Message
import com.example.pdmchat.model.MessageDao
import com.example.pdmchat.model.MessageDaoRtImpl
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class MessageRtControllerTest {

    @Test
    fun insertMessage() {
        println("Testando o insert...")
        val messageDaoImpl: MessageDao = MessageDaoRtImpl()
        val message = Message(1, "miguel", "2024-06-11", "17:29:30", "primeira mensagem")
        messageDaoImpl.createMessage(message)
    }

    @Test
    fun getMessages() {
    }
}