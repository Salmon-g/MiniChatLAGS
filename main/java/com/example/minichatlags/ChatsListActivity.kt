package com.example.minichatlags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minichatlags.Chat
import com.example.minichatlags.ChatAdapter
import com.example.minichatlags.R
import com.google.firebase.firestore.ktx.firestore
//import kotlinx.android.syntethic.main.activity_list_of_chats.*
import com.google.firebase.ktx.Firebase
import java.util.*


class ChatsListActivity : AppCompatActivity() {

    private var user = ""

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats_list)

        intent.getStringExtra("user")?.let{ user = it }

        if (user.isNotEmpty())
        {
            initViews()
        }
    }

    private fun initViews()
    {
        val newChatbtn = findViewById<Button>(R.id.newChatButton)
        newChatbtn.setOnClickListener { newChat() }


        findViewById<RecyclerView>(R.id.listChatsRecyclerView).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.listChatsRecyclerView).adapter =
            ChatAdapter { chat ->
                chatSelected(chat)
            }

        val userRef = db.collection("users").document(user)

        userRef.collection("chats")
            .get()
            .addOnSuccessListener { chats ->
            val listChats = chats.toObjects(Chat::class.java)

            (findViewById<RecyclerView>(R.id.listChatsRecyclerView).adapter as ChatAdapter).setData(listChats)
        }

        userRef.collection("chats").addSnapshotListener{ chats, error ->
            if(error == null){
                chats?.let {
                    val listChats = chats.toObjects(Chat::class.java)

                    (findViewById<RecyclerView>(R.id.listChatsRecyclerView).adapter as ChatAdapter).setData(listChats)
                }
            }
        }
    }

    private fun chatSelected(chat: Chat)
    {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId",chat.id)
        intent.putExtra("user",user)
        startActivity(intent)
    }

    private fun newChat()
    {
        val chatId = UUID.randomUUID().toString()
        val otherUser  = findViewById<EditText>(R.id.newChatText).text.toString()
        val users = listOf(user, otherUser)

        val chat = Chat(
            id = chatId,
            name = "Chat con $otherUser",
            users = users
        )

        db.collection("chats").document(chatId).set(chat)
        db.collection("users").document(user).collection("chats").document(chatId).set(chat)
        db.collection("users").document(otherUser).collection("chats").document(chatId).set(chat)

        val intent = Intent(this,ChatActivity::class.java)
        intent.putExtra("chatId",chatId)
        intent.putExtra("user",user)
        startActivity(intent)
    }
}