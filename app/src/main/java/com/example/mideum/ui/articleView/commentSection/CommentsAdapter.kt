package com.example.mideum.ui.articleView.commentSection


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mideum.MainActivity
import com.example.mideum.databinding.CommentCardBinding
import com.example.mideum.models.domain.Comment


class CommentsAdapter (private val clickListener : CommentClickListener) : ListAdapter<Comment, CommentViewHolder>(
    CommentDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        Log.i("adapter" , "works")
        return CommentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment , clickListener)
    }
}

class CommentClickListener(val doThis : (comment: Comment) -> Unit) {
    fun deleteComment(comment: Comment) = doThis(comment)
}

class CommentDiffCallback : DiffUtil.ItemCallback<Comment>(){
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}

class CommentViewHolder(private val binding : CommentCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment : Comment , clickListener: CommentClickListener) {
        binding.comment = comment
        binding.commentClickListener = clickListener
        if(MainActivity.user == null || MainActivity.user!!.username != comment.author.username) {
            binding.deleteComment.isVisible = false
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent : ViewGroup) : CommentViewHolder {
            val layoutInflater =LayoutInflater.from(parent.context)
            val binding = CommentCardBinding.inflate(layoutInflater , parent , false)
            return CommentViewHolder(binding)
        }
    }

}