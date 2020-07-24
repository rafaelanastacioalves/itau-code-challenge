package com.example.itaucodechallenge.code.ui.repositorieslisting;

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.common.CircleImageTransformation
import com.example.itaucodechallenge.code.domain.entities.Repository
import com.example.itaucodechallenge.code.listeners.RecyclerViewClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_repository.*

class RepositoryViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer{

    lateinit private var aRecyclerViewListener: RecyclerViewClickListener


    constructor(itemView: View , clickListener: RecyclerViewClickListener) : this(itemView) {
        this.aRecyclerViewListener = clickListener


    }
    init {
        containerView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        aRecyclerViewListener.onClick(v, getAdapterPosition());
    }

    fun bind(repository: Repository, context: Context) {

        repo_text_view_title.setText(repository.name)
        repo_text_view_title.contentDescription = context.getString(R.string.repository_title_content_description, repository.name)
        repo_text_view_title.setTag(repository)

        repo_text_view_description.setText(repository.description)
        repo_text_view_description.contentDescription = context.getString(R.string.repository_description_content_description, repository.description)


        repo_textview_number_forks.setText(Integer.toString(repository.forks))
        repo_textview_number_forks.contentDescription = context.getString(R.string.repository_forks_content_description, repository.forks)

        repo_textview_number_stars.setText(Integer.toString(repository.stargazersCount))
        repo_textview_number_stars.contentDescription = context.getString(R.string.repository_star_count_content_description, repository.stargazersCount)


        repo_textview_owner_name.setText(repository.owner.login)
        repo_textview_owner_name.contentDescription = context.getString(R.string.repository_owner_user_content_description, repository.owner.login)

        Picasso.get()
                .load(repository.owner.avatarUrl)
                .resize(150, 150)
                .centerInside()
                .transform(CircleImageTransformation())
                .placeholder(R.drawable.placeholder_user)
                .into(repo_owner_photo)


        containerView.setTag(repository)
    }
}
