package com.example.minichatlags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val user: String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages: List<Message> = emptyList()

    fun setData(list: List<Message>){
        messages = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): MessageAdapter.MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_message,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        val message = messages[position]

        if(user == message.from)
        {
            holder.itemView.findViewById<TextView>(R.id.myMessageLayout).visibility = View.VISIBLE
            holder. itemView.findViewById<TextView>(R.id.otherMessageLayout).visibility = View.GONE

            holder.itemView.findViewById<TextView>(R.id.otherMessageLayout).text = message.message
        } else {
            holder.itemView.findViewById<TextView>(R.id.myMessageLayout).visibility = View.GONE
            holder. itemView.findViewById<TextView>(R.id.otherMessageLayout).visibility = View.VISIBLE

            holder.itemView.findViewById<TextView>(R.id.otherMessageLayout).text = message.message
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}