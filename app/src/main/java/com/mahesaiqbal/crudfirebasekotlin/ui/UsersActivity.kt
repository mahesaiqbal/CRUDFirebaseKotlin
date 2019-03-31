package com.mahesaiqbal.crudfirebasekotlin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.mahesaiqbal.crudfirebasekotlin.R
import com.mahesaiqbal.crudfirebasekotlin.model.Users
import com.mahesaiqbal.crudfirebasekotlin.ui.adapter.UsersAdapter
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var users: MutableList<Users>
    lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        supportActionBar?.title = "Users"

        ref = FirebaseDatabase.getInstance().getReference("users")
        users = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (usr in dataSnapshot.children) {
                        val user = usr.getValue(Users::class.java)
                        users.add(user!!)
                    }

                    usersAdapter = UsersAdapter(users)

                    rv_users.apply {
                        layoutManager = LinearLayoutManager(this@UsersActivity)
                        adapter = usersAdapter
                    }
                }
            }
        })
    }
}
