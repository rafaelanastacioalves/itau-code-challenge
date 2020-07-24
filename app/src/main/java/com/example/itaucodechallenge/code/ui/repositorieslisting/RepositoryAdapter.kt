package com.example.itaucodechallenge.code.ui.repositorieslisting;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.domain.entities.Repository
import com.example.itaucodechallenge.code.listeners.RecyclerViewClickListener

class RepositoryAdapter(diffCallback: DiffUtil.ItemCallback<Repository>) : PagedListAdapter<Repository, RepositoryViewHolder>(diffCallback) {
    constructor(context: Context) : this(DIFF_CALLBACK as DiffUtil.ItemCallback<Repository>) {
        this.mContext = context
    }

    companion object {
        protected val DIFF_CALLBACK: DiffUtil.ItemCallback<Repository?> = object : DiffUtil.ItemCallback<Repository?>() {
            override fun areItemsTheSame(@NonNull repository: Repository, @NonNull t1: Repository): Boolean {
                return repository.id.equals(t1.id) && repository.page.equals(t1.page)
            }

            override fun areContentsTheSame(@NonNull repository: Repository, @NonNull t1: Repository): Boolean {
                return repository.equals(t1)
            }
        }
    }

    private lateinit var mContext: Context

    lateinit private var recyclerViewClickListener: RecyclerViewClickListener


    fun setRecyclerViewClickListener(aRVC: RecyclerViewClickListener) {
        this.recyclerViewClickListener = aRVC;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_repository, parent, false), recyclerViewClickListener);
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val aRepoW = getItem(position) as Repository;
        holder.bind(aRepoW, mContext);
    }

    fun updateList() {
        notifyDataSetChanged()
    }
}

