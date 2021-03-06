package com.mahesaiqbal.crudfirebasekotlin.ui.activity

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

        btn_save.setOnClickListener {
            saveData()
            startActivity(Intent(this@MainActivity, UsersActivity::class.java))
        }

        btn_read.setOnClickListener {
            startActivity(Intent(this@MainActivity, UsersActivity::class.java))
        }
    }

    private fun saveData() {
        val nama = input_nama.text.toString()
        val status = input_status.text.toString()

        val userId = ref.push().key.toString()
        val user = Users(userId, nama, status)

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            input_nama.setText("")
            input_status.setText("")
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}
