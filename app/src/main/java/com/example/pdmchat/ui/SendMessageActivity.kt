package com.example.pdmchat.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pdmchat.R
import com.example.pdmchat.controller.MessageCreateRtController
import com.example.pdmchat.controller.MessageRtController
import com.example.pdmchat.databinding.ActivitySendMessageBinding
import com.example.pdmchat.model.Message
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SendMessageActivity : AppCompatActivity() {

    // Biding
    private lateinit var sendMessageBinding: ActivitySendMessageBinding

    // Controller
    private val messageController: MessageCreateRtController by lazy {
        MessageCreateRtController(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        sendMessageBinding = ActivitySendMessageBinding.inflate(layoutInflater)
        setContentView(sendMessageBinding.root)

        sendMessageBinding.sendButton.setOnClickListener{
            val sendler = sendMessageBinding.chatSendler.text.toString()
            val recipient = sendMessageBinding.recipientEditText.text.toString()
            val messageText = sendMessageBinding.messageText.text.toString()
            sendMessage(sendler, recipient, messageText)
        }
    }

    private fun sendMessage(sendler: String?, recipient: String, conteudo: String) {
        val mensagem = if (conteudo.length > 150) conteudo.substring(0, 150) else conteudo
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val data = dateFormat.format(cal.time)
        val horario = timeFormat.format(cal.time)

        val message = Message(
            sender = sendler ?: "",
            recipient = recipient,
            date = data,
            time = horario,
            message = mensagem
        )

        messageController.insertMessage(message)
        Toast.makeText(this, "Mensagem enviada", Toast.LENGTH_SHORT).show()

        finish()
    }

}