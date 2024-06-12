package com.example.pdmchat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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

    // AutoComplete users
    private lateinit var recipientsAdapter: ArrayAdapter<String>
    private lateinit var recipientsList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        sendMessageBinding = ActivitySendMessageBinding.inflate(layoutInflater)
        setContentView(sendMessageBinding.root)

        val sharedPreferences = getSharedPreferences("Sendler", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("Sendler", "")
        if (name != ""){
            sendMessageBinding.chatSendler.setText(name)
        }

        sendMessageBinding.sendButton.setOnClickListener{
            val sendler = sendMessageBinding.chatSendler.text.toString()
            val recipient = sendMessageBinding.recipientEditText.text.toString()
            val messageText = sendMessageBinding.messageText.text.toString()
            sendMessage(sendler, recipient, messageText)
        }

        // Auto complete recipients
        recipientsList = mutableListOf()

        val sharedPreferencesRecipients = getSharedPreferences("Recipients", Context.MODE_PRIVATE)
        val destinatariosSet = sharedPreferencesRecipients.getStringSet("Recipients", emptySet())
        Log.e("destinatariosSet", destinatariosSet.toString())
        recipientsList.addAll(destinatariosSet ?: emptySet())

        recipientsAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            recipientsList
        )
        sendMessageBinding.recipientEditText.setAdapter(recipientsAdapter)


    }

    private fun sendMessage(sendler: String?, recipient: String, conteudo: String) {

        // Change ou Create sendler
        val sharedPreferences = getSharedPreferences("Sendler", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Sendler", sendler)
        editor.apply()

        // Update recipients
        if (!recipientsList.contains(recipient)) {
            Log.e("recipientsList", recipient)
            recipientsList.add(recipient)
            recipientsAdapter.notifyDataSetChanged()

            val sharedPreferencesRecipients = getSharedPreferences("Recipients", Context.MODE_PRIVATE)
            val editorRec = sharedPreferencesRecipients.edit()
            editorRec.putStringSet("Recipients", recipientsList.toSet())
            editorRec.apply()
        }

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

        Intent().apply {
            setResult(RESULT_OK, this)
            finish()
        }
    }

}