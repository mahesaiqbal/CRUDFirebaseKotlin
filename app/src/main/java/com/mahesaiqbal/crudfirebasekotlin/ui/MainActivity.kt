package com.mahesaiqbal.crudfirebasekotlin.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mahesaiqbal.crudfirebasekotlin.R
import com.mahesaiqbal.crudfirebasekotlin.model.Users
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("users")

        btnSave.setOnClickListener {
            saveData()
            val showUsers = Intent(this, UsersActivity::class.java)
            startActivity(showUsers)
        }
    }

    private fun saveData() {
        val nama = input_nama.text.toString()
        val status = input_status.text.toString()

        val user = Users(nama, status)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            input_nama.setText("")
            input_status.setText("")
        }
    }
}
