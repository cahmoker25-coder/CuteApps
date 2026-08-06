package com.cuteapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var lvChat: ListView

    private val database = FirebaseDatabase.getInstance().reference
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    private val messageList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        etMessage = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_send)
        lvChat = findViewById(R.id.lv_chat)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messageList)
        lvChat.adapter = adapter

        // Anggap room chat ID sementara gabungan ID atau statis untuk uji coba
        val chatId = "room_global_chat"

        btnSend.setOnClickListener {
            val messageText = etMessage.text.toString().trim()
            if (messageText.isNotEmpty() && currentUserId != null) {
                val messageId = database.child("chats").child(chatId).push().key
                if (messageId != null) {
                    database.child("chats").child(chatId).child(messageId).setValue(messageText)
                    etMessage.setText("")
                }
            }
        }

        // Ambil data pesan secara real-time dari Firebase Database
        database.child("chats").child(chatId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (snap in snapshot.children) {
                    val msg = snap.value.toString()
                    messageList.add(msg)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
