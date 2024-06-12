package com.example.pdmchat.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmchat.R
import com.example.pdmchat.adapter.MessageAdapter
import com.example.pdmchat.controller.MessageRtController
import com.example.pdmchat.databinding.ActivityMainBinding
import com.example.pdmchat.model.Message

class MainActivity : AppCompatActivity() {

    companion object {
        const val GET_MESSAGES = 1 // Buscar os contatos na contactList do banco de dados
        const val GET_CONTACTS_INTERVAL = 2000L // Intervalo de busca de contatos
    }

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val messageList: MutableList<Message> = mutableListOf()

    // Controller
    private val messageController: MessageRtController by lazy {
        MessageRtController(this)
    }

    // Adapter
    private val messageAdapter: MessageAdapter by lazy {
        MessageAdapter(this, messageList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        // Create message test
//        val message = Message(1, "miguel", "2024-06-11", "17:29:30", "primeira mensagem")
//        Log.e("FIREBASE", "Before insert")
//        messageController.insertMessage(message)
//        Log.e("FIREBASE", "After insert")

        // ToolBar
        setSupportActionBar(amb.toolbar)

        uiUpdaterHandler.apply {
            sendMessageDelayed(
                android.os.Message.obtain().apply {
                    what = GET_MESSAGES
                },
                GET_CONTACTS_INTERVAL
            )
        }

        amb.messageLv.adapter = messageAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    val uiUpdaterHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            // Busca contatos ou atualiza a lista de acordo com o tipo de mensagem
            if (msg.what == GET_MESSAGES) {
                // Busca os contatos
                messageController.getMessages()
                sendMessageDelayed(
                    android.os.Message.obtain().apply {
                        what = GET_MESSAGES
                    },
                    GET_CONTACTS_INTERVAL
                )
            }
            else {
                msg.data.getParcelableArrayList<Message>("MESSAGE_ARRAY")?.let { _contactArray ->
                    updateMessagesList(_contactArray.toMutableList())
                }
            }
        }
    }

    fun updateMessagesList(messages: MutableList<Message>) {
        messageList.clear()
        messageList.addAll(messages)
        Log.e("MIGUEL Messages", messageList.toString())
        messageAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_message -> {
                val intent = Intent(this, SendMessageActivity::class.java)
                intent.putExtra("remetente", "")
                startActivity(intent)
                true
                Log.e("Miguel", "adicionar uma mensagem")
                true
            }
            R.id.exit_app -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}