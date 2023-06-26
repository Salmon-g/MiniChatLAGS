package com.example.minichatlags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minichatlags.MessageAdapter
import com.example.minichatlags.R
import com.example.minichatlags.Message
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private var chatId = ""
    private var user = ""

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        intent.getStringExtra("chatId")?.let { chatId = it}
        intent.getStringExtra("user")?.let { user = it}

        if(chatId.isNotEmpty() && user.isNotEmpty()){
            initViews()
        }
    }

    private fun initViews(){
        findViewById<RecyclerView>(R.id.messagesRecylerView).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.messagesRecylerView).adapter = MessageAdapter(user)

        findViewById<Button>(R.id.sendMessageButton).setOnClickListener { sendMessage() }

        val chatRef = db.collection("chats").document(chatId)

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { messages ->
                val listMessages = messages.toObjects(Message::class.java)
                (findViewById<RecyclerView>(R.id.messagesRecylerView).adapter as MessageAdapter).setData(listMessages)
            }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(Message::class.java)
                        (findViewById<RecyclerView>(R.id.messagesRecylerView).adapter as MessageAdapter).setData(listMessages)
                    }
                }
            }
    }

    private fun sendMessage(){
        val message = Message(
            message = findViewById<EditText>(R.id.messageTextField).text.toString(),
            from = user
        )

        db.collection("chats").document(chatId).collection("messages").document().set(message)

        findViewById<EditText>(R.id.messageTextField).setText("")
    }
}