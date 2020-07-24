package com.example.itaucodechallenge.code.ui.prlisting

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.common.CircleImageTransformation
import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.listeners.RecyclerViewClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_pull_request.*

class PullViewHolder(override val containerView: View, private val aRecyclerViewListener: RecyclerViewClickListener) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {

    init {
        containerView.setOnClickListener(this)
    }


    fun bind(aPull: Pull) {
        val context = containerView.context
        pull_text_view_description.setText(aPull.body)
        pull_text_view_description.contentDescription = context.getString(R.string.pullrequest_description_content_description, pull_text_view_description.text.substring(
                pull_text_view_description.layout.getLineEnd(pull_text_view_description.getLineCount() - 1)
        ))
        pull_textview_title.setText(aPull.title)
        pull_textview_title.contentDescription = context.getString(R.string.pullrequest_description_content_description,aPull.title)
        pull_textview_owner_username.setText(aPull.user.login)
        pull_textview_owner_username.contentDescription = context.getString(R.string.pullrequest_description_content_description, aPull.user.login)
        Picasso.get()
                .load(aPull.user.avatarUrl)
                .resize(150, 150)
                .centerInside()
                .transform(CircleImageTransformation())
                .placeholder(R.drawable.placeholder_user)
                .into(pull_textview_owner_photo)
    }

    override fun onClick(v: View) {
        aRecyclerViewListener.onClick(v, getAdapterPosition())
    }
}