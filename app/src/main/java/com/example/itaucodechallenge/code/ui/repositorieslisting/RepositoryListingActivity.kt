package com.example.itaucodechallenge.code.ui.repositorieslisting


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itaucodechallenge.code.R
import com.example.itaucodechallenge.code.domain.entities.Repository
import com.example.itaucodechallenge.code.listeners.RecyclerViewClickListener
import com.example.itaucodechallenge.code.ui.prlisting.PullRequestsListingActivity
import com.example.itaucodechallenge.code.ui.prlisting.PullRequestsListingFragment

class RepositoryListingActivity : AppCompatActivity(), RecyclerViewClickListener {

    private val mClickListener = this
    private var repositoryAdapter: RepositoryAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private val mLiveDataRepositoryListViewModel: LiveDataRepositoryListViewModel by lazy {
        ViewModelProvider(this).get(LiveDataRepositoryListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupRecyclerView()
        subscribe()

    }

    private fun setupViews() {
        setContentView(R.layout.activity_repository_list)
    }

    private fun subscribe() {
        mLiveDataRepositoryListViewModel.repositoryListLiveData.observeForever(Observer { repositories ->
            populateRecyclerView(repositories)
        })
    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById<View>(R.id.repository_list) as RecyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView!!.layoutManager = layoutManager
        if (repositoryAdapter == null) {
            repositoryAdapter = RepositoryAdapter(this)
        }
        repositoryAdapter!!.setRecyclerViewClickListener(mClickListener)
        mRecyclerView!!.adapter = repositoryAdapter
    }


    private fun populateRecyclerView(list: PagedList<Repository?>) {
        repositoryAdapter?.submitList(list)
    }

    private fun showLoadError() {
        Toast.makeText(this, "Erro de Carregamento", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View, position: Int) {
        val repository = repositoryAdapter?.currentList?.get(position)

        val i = Intent(this, PullRequestsListingActivity::class.java)
        i.putExtra(PullRequestsListingFragment.ARG_CREATOR, repository?.owner?.login)
        i.putExtra(PullRequestsListingFragment.ARG_REPOSITORY, repository?.name)
        startActivity(i)

    }

}
