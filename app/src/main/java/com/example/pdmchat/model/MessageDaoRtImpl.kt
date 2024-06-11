package com.example.pdmchat.model

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageDaoRtImpl: MessageDao {
    companion object {
        private const val MESSAGE_LIST_ROOT_NODE = "message"
    }

    private val messageRtDbFbReference = Firebase.database.getReference(
        MESSAGE_LIST_ROOT_NODE
    )

    // Simula uma consulta no banco de dados
    private val messageList = mutableListOf<Message>()
    init {
        // Chamado sempre que houver uma modificação no banco de dados de tempo real do Firebase
        messageRtDbFbReference.addChildEventListener(
            object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue<Message>()
                    Log.e("MIGUEL ADD", message.toString())
                    //val message = snapshot.getValue(Message::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue<Message>()
                    Log.e("MIGUEL CHILD", message.toString())
                    if (message != null) {
                        val index = messageList.indexOfFirst { it.id == message.id }
                        if (index != -1) {
                            messageList[index] = message
                        }
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val message = snapshot.getValue<Message>()

                    if (message != null) {
                        val index = messageList.indexOfFirst { it.id == message.id }
                        if (index != -1) {
                            messageList.removeAt(index)
                        }
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    // NSA
                }

                override fun onCancelled(error: DatabaseError) {
                    // NSA
                }
            }
        )

        // Chamado uma única vez sempre que o aplicativo for executado
        messageRtDbFbReference.addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = snapshot.getValue(object : GenericTypeIndicator<List<Message>>() {})
                    if (messages != null) {
                        for (message in messages) {
                            println(message)
                        }
                    } else {
                        println("Nenhuma mensagem encontrada.")
                    }
//
//                    if (messageMap != null) {
//                        messageList.clear()
//                        messageList.addAll(messageMap.values)
//                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // NSA
                }
            }
        )
    }

    override fun createMessage(message: Message): Int {
        createOrUpdateMessage(message)
        return 1
    }

    override fun retrieveMessages(): MutableList<Message> {
        return messageList
    }

    private fun createOrUpdateMessage(message: Message) {
        messageRtDbFbReference.child(message.id.toString()).setValue(message)
    }
}