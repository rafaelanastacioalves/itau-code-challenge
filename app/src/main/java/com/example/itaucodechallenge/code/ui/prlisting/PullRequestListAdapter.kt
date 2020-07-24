package com.example.itaucodechallenge.code.ui.prlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.listeners.RecyclerViewClickListener
import java.util.*

class PullsListAdapter : RecyclerView.Adapter<PullViewHolder?>() {
    lateinit private var recyclerViewClickListener: RecyclerViewClickListener
    private var items: List<Pull> = ArrayList<Pull>()
    private fun getItems(): List<Pull>? {
        return items
    }

    fun setItems(data: List<Pull>) {
        items = data
        notifyDataSetChanged()
    }

    fun setRecyclerViewClickListener(aRVC: RecyclerViewClickListener) {
        recyclerViewClickListener = aRVC
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): PullViewHolder {
        return PullViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_pull_request, parent, false), recyclerViewClickListener)
    }

    override fun onBindViewHolder(@NonNull holder: PullViewHolder, position: Int) {
        val aPull: Pull = getItems()!![position]
        holder.bind(aPull)
    }


    val currentList: List<Any>
        get() = items

    override fun getItemCount(): Int {
        return if (getItems() != null) {
            items.size
        } else {
            0
        }

    }
}