package com.example.itaucodechallenge.code.ui.prlisting

import android.os.Bundle

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.example.itaucodechallenge.code.R


class PullRequestsListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_requests)
        setupActionBar()


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val arguments = Bundle()
            arguments.putString(PullRequestsListingFragment.ARG_CREATOR,
                    intent.getStringExtra(PullRequestsListingFragment.ARG_CREATOR))
            arguments.putString(PullRequestsListingFragment.ARG_REPOSITORY,
                    intent.getStringExtra(PullRequestsListingFragment.ARG_REPOSITORY))


            val fragment = PullRequestsListingFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.entity_detail_fragment_container, fragment)
                    .commit()


            supportPostponeEnterTransition()
        }
    }

    private fun setupActionBar() {
        val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)

    }

}
