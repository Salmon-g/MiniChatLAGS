package com.example.minichatlags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.minichatlags.R
import com.example.minichatlags.Chat
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val chatClick: (Chat) -> Unit) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    var chats: List<Chat> = emptyList()

    fun setData(list: List<Chat>){
        chats = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.chatNameText).text = chats[position].name
        holder.itemView.findViewById<TextView>(R.id.usersTextView).text = chats[position].users.toString()

        holder.itemView.setOnClickListener{
            chatClick(
                chats[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }
    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}