package com.example.pdmchat.model

interface MessageDao {
    fun createMessage(message: Message): Int
    fun retrieveMessages(): MutableList<Message>
}