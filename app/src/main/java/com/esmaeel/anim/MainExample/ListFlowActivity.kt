package com.esmaeel.anim.MainExample

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.esmaeel.anim.Base.BaseActivity
import com.esmaeel.anim.Koin.Network.Status
import com.esmaeel.anim.MainExample.Models.CenterResponse
import com.esmaeel.anim.R
import com.esmaeel.anim.Utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFlowActivity : BaseActivity() {
    val viewModel: GlobalViewModel by viewModel()


    lateinit var centersAdapter: CentersAdapter
    lateinit var paginationListener: EndlessRecyclerOnScrollListener
    var randomKey = -1;
    var hasMore = true;
    var pageNumber = 1;
    var salonsList: ArrayList<CenterResponse.Data.Salon?>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        topbar.setOnClickListener {
            if (hasMore)
                viewModel.getSalonsFlow(pageNumber = pageNumber, randomKey = randomKey)
        }

        initPagination()
        initRecycler()

        viewModel.centersFlow.observe(this, Observer { contract ->
            when (contract.status) {
                Status.SUCCESS -> {
                    hideLoader()
                    hasMore = contract.data?.pagination?.has_more_pages ?: false
                    randomKey = contract.data?.data?.random_order_key ?: -1
                    contract.data?.data?.salons?.let { salonsList?.addAll(it) }
                    centersAdapter.submitList(salonsList)
                }
                Status.LOADING -> {
                    showLoader()
                }
                Status.ERROR -> {
                    Toast.makeText(applicationContext, contract.message, Toast.LENGTH_SHORT).show()
                    hideLoader()
                }
            }

        })

        viewModel.getSalonsFlow(1, -1)
    }

    private fun initRecycler() {
        centersAdapter = CentersAdapter()
        /*handling events inside the list with a live data -> not done yet*/
        centersAdapter.clickEvent.observe(this, Observer { event ->
            event.data?.let {
                Toast.makeText(applicationContext, it.salon_name, Toast.LENGTH_SHORT).show()
            }
        })

        centersRec.apply {
            adapter = centersAdapter;
            layoutManager = LinearLayoutManager(this@ListFlowActivity)
            addOnScrollListener(paginationListener)
        }
    }

    var scrollTo: Int? = null

    private fun initPagination() {
        paginationListener = object : EndlessRecyclerOnScrollListener() {
            override fun positionChange(lastVisibleItem: Int) {
//                scrollTo == lastVisibleItem
            }

            override fun onLoadMore(currentPage: Int, visibleItemPosition: Int) {
                if (hasMore) {
                    pageNumber = currentPage
                    viewModel.getSalonsFlow(pageNumber = pageNumber, randomKey = randomKey)
                }
            }
        }

    }


}