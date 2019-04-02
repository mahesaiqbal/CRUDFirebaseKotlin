package com.mahesaiqbal.crudfirebasekotlin.ui.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.mahesaiqbal.crudfirebasekotlin.R
import com.mahesaiqbal.crudfirebasekotlin.model.Users
import com.mahesaiqbal.crudfirebasekotlin.ui.activity.UsersActivity
import com.mahesaiqbal.crudfirebasekotlin.ui.adapter.UsersAdapter.UsersViewHolder
import kotlinx.android.synthetic.main.update_user.view.*
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(val ctx: Context, val users: List<Users>) : RecyclerView.Adapter<UsersViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UsersViewHolder {
        return UsersViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.user_item, viewGroup, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindItem(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tv_nama
        val tvStatus = itemView.tv_status
        val btnUpdate = itemView.btn_update
        val btnDelete = itemView.btn_delete

        fun bindItem(user: Users) {
            tvName.text = user.nama
            tvStatus.text = user.status

            btnUpdate.setOnClickListener { showUpdateDialog(user) }
            btnDelete.setOnClickListener { deleteInfo(user) }
        }

        fun showUpdateDialog(user: Users) {
            val builder = AlertDialog.Builder(ctx)
            builder.setTitle("Update")

            val inflater = LayoutInflater.from(ctx)
            val view = inflater.inflate(R.layout.update_user, null)

            view.edt_nama.setText(user.nama)
            view.edt_status.setText(user.status)

            builder.setView(view)
            builder.setPositiveButton("Update") { dialog, which ->
                val dbUsers = FirebaseDatabase.getInstance().getReference("users")

                val nama = view.edt_nama.text.toString().trim()
                val status = view.edt_status.text.toString().trim()

                if (nama.isEmpty()) {
                    view.edt_nama.error = "Mohon masukan nama"
                    view.edt_nama.requestFocus()
                    return@setPositiveButton
                }

                if (status.isEmpty()) {
                    view.edt_status.error = "Mohon masukan status"
                    view.edt_status.requestFocus()
                    return@setPositiveButton
                }

                val user = Users(user.id, nama, status)

                dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                    Toast.makeText(ctx, "Berhasil di update.", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Tidak") { dialog, which ->

            }

            val alert = builder.create()
            alert.show()
        }

        fun deleteInfo(user: Users) {
            val progressDialog = ProgressDialog(ctx, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Menghapus...")
            progressDialog.show()

            val mydatabase = FirebaseDatabase.getInstance().getReference("users")
            mydatabase.child(user.id).removeValue()

            Toast.makeText(ctx, "Berhasil dihapus.", Toast.LENGTH_SHORT).show()

            val intent = Intent(ctx, UsersActivity::class.java)
            ctx.startActivity(intent)
        }
    }

}