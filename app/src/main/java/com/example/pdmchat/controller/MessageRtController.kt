package com.example.pdmchat.controller

import android.os.Message as Msg
import com.example.pdmchat.model.Message
import com.example.pdmchat.MainActivity
import com.example.pdmchat.model.MessageDao
import com.example.pdmchat.model.MessageDaoRtImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessageRtController (private val mainActivity: MainActivity) {
    private val messageDaoImpl: MessageDao = MessageDaoRtImpl()

    fun insertMessage(message: Message) {
        GlobalScope.launch {
            messageDaoImpl.createMessage(message)
        }
    }

    fun getMessages(){
        val messageList = messageDaoImpl.retrieveMessages()
        if (messageList.isNotEmpty()) {
            mainActivity.uiUpdaterHandler.sendMessage(
                Msg.obtain().apply{
                    data.putParcelableArrayList(
                        "MESSAGE_ARRAY",
                        ArrayList(messageList)
                    )
                }
            )
            println(messageList)
        }
    }
}