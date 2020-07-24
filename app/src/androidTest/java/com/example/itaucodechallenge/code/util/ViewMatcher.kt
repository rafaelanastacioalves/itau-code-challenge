package com.example.itaucodechallenge.code.util

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.ui.repositorieslisting.RepositoryViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher


 class ViewMatcher {
     companion object {
         fun showRepositoryItemWithTitle(title: String, position: Int): Matcher<in View?>? {
             return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
                 override fun describeTo(description: Description) {
                     description.appendText("Item with title " + title + " at position " +
                             position + ".")
                 }

                 override fun matchesSafely(item: RecyclerView): Boolean {
                     val checkedViewHolder = item.findViewHolderForAdapterPosition(position) as RepositoryViewHolder?
                     val checkedTitleTextView = checkedViewHolder!!.itemView.findViewById<TextView>(R.id.repo_text_view_title)
                     return checkedTitleTextView.text.toString() == title &&
                             checkedTitleTextView.visibility == View.VISIBLE
                 }
             }
         }
     }
 }