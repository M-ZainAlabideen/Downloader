package com.downloader.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.downloader.R
import com.downloader.databinding.ItemFileBinding
import com.downloader.model.response.FileResponse


class FilesAdapter(
    private val context: Context,
    private val list: List<FileResponse>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<FilesAdapter.MyViewHolder>() {

    private var binding: ItemFileBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding?.fileName?.text = list[position].name
        if(list[position].url.contains(".pdf"))
            holder.binding.image.setImageResource(R.drawable.pdf_noimg)
        else
            holder.binding.image.setImageResource(R.drawable.video_noimg)
        clicks(position,holder)
    }


    private fun clicks(position: Int, holder: MyViewHolder) {
        holder.itemView.setOnClickListener {
            listener.click(position, holder)
        }

    }


    interface OnItemClickListener {
        fun click(position: Int, holder: MyViewHolder)
    }

    class MyViewHolder(itemView: ItemFileBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemFileBinding = itemView

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}