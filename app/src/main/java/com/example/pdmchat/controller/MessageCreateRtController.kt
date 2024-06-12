package com.example.pdmchat.controller

import com.example.pdmchat.model.Message
import com.example.pdmchat.model.MessageDao
import com.example.pdmchat.model.MessageDaoRtImpl
import com.example.pdmchat.ui.MainActivity
import com.example.pdmchat.ui.SendMessageActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessageCreateRtController(private val activity: SendMessageActivity) {
    private val messageDaoImpl: MessageDao = MessageDaoRtImpl()

    fun insertMessage(message: Message) {
        GlobalScope.launch {
            messageDaoImpl.createMessage(message)
        }
    }
}