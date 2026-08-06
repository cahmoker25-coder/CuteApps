package com.cuteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // Cek apakah user sudah login atau belum
        if (auth.currentUser == null) {
            // Jika belum login, arahkan kembali ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Tombol menuju fitur cari teman
        val btnCariTeman = findViewById<Button>(R.id.btn_cari_teman)
        btnCariTeman.setOnClickListener {
            // TODO: Buka halaman pencarian username teman
        }
    }
}
