package com.akih.submission2bfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akih.submission2bfaa.data.Users
import com.akih.submission2bfaa.databinding.ListUserGithubBinding
import com.bumptech.glide.Glide

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<Users>()
    private var onClickItemCallback: OnClickItemCallback? = null

    fun setOnItemClickCallback(onClickItemCallback: OnClickItemCallback){
        this.onClickItemCallback = onClickItemCallback
    }

    fun setList(users: ArrayList<Users>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ListUserGithubBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : Users){
            binding.root.setOnClickListener {
                onClickItemCallback?.onClickedItem(user)
            }

            Glide.with(binding.root)
                .load(user.avatar_url)
                .into(binding.imgItem)

            binding.tvItemUsername.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(list[position])

    interface OnClickItemCallback{
        fun onClickedItem(data : Users)
    }
}