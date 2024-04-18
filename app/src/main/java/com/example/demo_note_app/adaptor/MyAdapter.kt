package com.example.demo_note_app.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_note_app.databinding.ItemCardBinding
import com.example.demo_note_app.roomdb.NoteDataClass



class MyAdapter(private var items: List<NoteDataClass>, private val clickListener: MyClickListener) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemCardBinding.inflate(inflater, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item,clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(private val itemBinding: ItemCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: NoteDataClass, clickListener1: MyClickListener) {
            itemBinding.itemVariable = item
            itemBinding.clickListener = clickListener1
            itemBinding.executePendingBindings()
        }

    }

    interface MyClickListener {
        fun onUpdateItemClick(item: NoteDataClass)
        fun onDeleteItemClick(item: NoteDataClass)
    }


}