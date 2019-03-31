package com.mahesaiqbal.crudfirebasekotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mahesaiqbal.crudfirebasekotlin.R
import com.mahesaiqbal.crudfirebasekotlin.model.Users
import com.mahesaiqbal.crudfirebasekotlin.ui.adapter.UsersAdapter.UsersViewHolder
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(private val users: List<Users>) : RecyclerView.Adapter<UsersViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UsersViewHolder {
        return UsersViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.user_item, viewGroup, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindItem(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tv_nama
        val tvStatus = itemView.tv_status

        fun bindItem(user: Users) {
            tvName.text = user.nama
            tvStatus.text = user.status
        }
    }

}