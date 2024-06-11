package com.example.pdmchat.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pdmchat.model.Message
import com.example.pdmchat.R

class MessageAdapter(context: Context, private val messageList: MutableList<Message>):
    ArrayAdapter<Message>(context, R.layout.tile_message, messageList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // pegar o contato no data source
        val message = messageList[position]
        Log.e("MIGUEL ADAPTER", message.toString())
        // inflo uma nova célula se necessário
        var messageTileView = convertView
        if (messageTileView == null) {
            messageTileView = LayoutInflater.from(context).inflate(
                R.layout.tile_message,
                parent,
                false
            ).apply{
                tag = TileContactHolder(
                    findViewById(R.id.nameTv),
                    findViewById(R.id.messageTv)
                )
            }
        }

        // colocar os valores de contato na célula
        (messageTileView?.tag as TileContactHolder).apply{
            nameTv.text = message.sender
            emailTv.text = message.message
        }

        // retorna a célula preenchida
        return messageTileView
    }

    private data class TileContactHolder(val nameTv: TextView, val emailTv: TextView)
}