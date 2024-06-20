package com.example.final_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_project.databinding.ItemCommentBinding

class BoardViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

class BoardAdapter (val context: Context, val itemList: MutableList<ItemData>): RecyclerView.Adapter<BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BoardViewHolder(ItemCommentBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            idTextView.text = data.email
            dateTextView.text = data.date_time
            contentsTextView.text=data.coments
            ratingBar.rating=data.stars.toFloat()

        }
        val imageRef = MyApplication.storage.reference.child("images/${data.docId}.jpg")
        imageRef.downloadUrl.addOnCompleteListener{task ->
            if(task.isSuccessful){
                holder.binding.itemImageView.visibility = View.VISIBLE
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.itemImageView)
            }
        }
    }
}


