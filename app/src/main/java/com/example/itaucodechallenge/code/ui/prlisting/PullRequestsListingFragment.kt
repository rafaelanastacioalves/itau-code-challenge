package com.example.itaucodechallenge.code.ui.prlisting


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.domain.entities.Resource
import com.example.itaucodechallenge.code.listeners.RecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_pull_requests.*

class PullRequestsListingFragment : Fragment(), RecyclerViewClickListener {
    companion object {

        val ARG_CREATOR: String? = "ARG_CREATOR"
        var ARG_REPOSITORY: String? = "ARG_REPOSITORY"
    }

    lateinit private var mPullRequestListingViewModel: PullRequestListingViewModel
    lateinit var repository: String
    lateinit var creator: String


    private val mPullsListAdapter: PullsListAdapter by lazy {
        PullsListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    private fun loadData() {
        creator = arguments!!.getString(ARG_CREATOR)
        repository = arguments!!.getString(ARG_REPOSITORY)
        mPullRequestListingViewModel = ViewModelProvider.NewInstanceFactory().create(PullRequestListingViewModel::class.java)
        mPullRequestListingViewModel.loadData(creator, repository).observe(this, Observer { entityDetails ->
            when (entityDetails.status) {
                Resource.Status.SUCCESS -> {
                    if (entityDetails.data.isNullOrEmpty()) {
                        hideLoading()
                        showError()
                    } else {
                        hideLoading()
                        setViewsWith(entityDetails?.data)
                    }

                }
                Resource.Status.INTERNAL_SERVER_ERROR -> {
                    hideLoading()
                    showError()
                }
                Resource.Status.GENERIC_ERROR -> {
                    hideLoading()
                    showError()
                }
                Resource.Status.LOADING -> {
                    hideMainView()
                    showLoading()
                }
                else -> {
                    hideLoading()
                    showError()
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var inflatedView = inflateViews(inflater, container)
        setupRecyclerView(inflatedView.findViewById(R.id.pulls_list_recycler_view) as RecyclerView)
        setupActionBarWithTitle(repository)
        return inflatedView
    }

    private fun inflateViews(inflater: LayoutInflater, container: ViewGroup?): View {
        val rootView = inflater.inflate(R.layout.fragment_pull_requests, container, false)

        return rootView
    }

    private fun setupRecyclerView(mPullsListRecyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(activity)
        mPullsListRecyclerView.setLayoutManager(layoutManager)
        mPullsListAdapter.setRecyclerViewClickListener(this)
        mPullsListRecyclerView.setAdapter(mPullsListAdapter)
    }

    private fun setViewsWith(entityDetails: List<Pull>?) {
        pulls_list_recycler_view.visibility = View.VISIBLE
        entityDetails?.let { mPullsListAdapter.setItems(it) }
    }

    private fun setupActionBarWithTitle(title: String) {
        val mActivity = activity as AppCompatActivity?
        val actionBar = mActivity!!.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title
        }
    }

    override fun onClick(v: View, position: Int) {
        val aPull: Pull = mPullsListAdapter.currentList.get(position) as Pull
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(aPull.pullUrl))
        startActivity(browserIntent)
    }

    private fun hideMainView() {
        pulls_list_recycler_view.visibility = View.GONE
    }

    private fun showError() {
        noItemToShow.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        progressBar.setVisibility(View.VISIBLE)
    }

}
