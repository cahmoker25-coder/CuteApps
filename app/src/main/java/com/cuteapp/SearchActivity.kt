package com.cuteapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnCari: Button
    private lateinit var tvResult: TextView
    private lateinit var btnAddFriend: Button

    private val database = FirebaseDatabase.getInstance().reference
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearch = findViewById(R.id.et_search_username)
        btnCari = findViewById(R.id.btn_cari)
        tvResult = findViewById(R.id.tv_result_name)
        btnAddFriend = findViewById(R.id.btn_add_friend)

        btnCari.setOnClickListener {
            val queryUsername = etSearch.text.toString().trim()
            if (queryUsername.isNotEmpty()) {
                searchUserByUsername(queryUsername)
            }
        }
    }

    private fun searchUserByUsername(username: String) {
        database.child("users").orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnap in snapshot.children) {
                            val targetUserId = userSnap.key
                            val name = userSnap.child("name").value.toString()

                            if (targetUserId == currentUserId) {
                                tvResult.text = "Itu adalah akun kamu sendiri!"
                                btnAddFriend.visibility = View.GONE
                                return
                            }

                            tvResult.text = "Ditemukan: $name (@$username)"
                            btnAddFriend.visibility = View.VISIBLE

                            // Aksi Kirim Friend Request
                            btnAddFriend.setOnClickListener {
                                sendFriendRequest(targetUserId!!)
                            }
                        }
                    } else {
                        tvResult.text = "User dengan username @$username tidak ditemukan."
                        btnAddFriend.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@SearchActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun sendFriendRequest(targetUserId: String) {
        if (currentUserId == null) return

        // Simpan data friend request ke Realtime Database
        database.child("friend_requests").child(targetUserId).child(currentUserId).setValue("pending")
            .addOnSuccessListener {
                Toast.makeText(this, "Permintaan pertemanan terkirim!", Toast.LENGTH_SHORT).show()
                btnAddFriend.visibility = View.GONE
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal mengirim permintaan.", Toast.LENGTH_SHORT).show()
            }
    }
}
