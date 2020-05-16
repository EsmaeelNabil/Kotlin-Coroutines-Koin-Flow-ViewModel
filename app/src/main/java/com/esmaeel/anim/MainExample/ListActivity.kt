package com.esmaeel.anim.MainExample

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.esmaeel.anim.Base.BaseActivity
import com.esmaeel.anim.Koin.Network.Status
import com.esmaeel.anim.MainExample.Models.CenterResponse
import com.esmaeel.anim.R
import com.esmaeel.anim.Utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : BaseActivity() {
    val viewModel: GlobalViewModel by viewModel()


    lateinit var centersAdapter: CentersAdapter
    lateinit var paginationListener: EndlessRecyclerOnScrollListener
    var randomKey = -1;
    var hasMore = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        paginationListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int, visibleItemPosition: Int) {
                if (hasMore)
                    viewModel.getSalons(pageNumber = currentPage, randomKey = randomKey)
            }

            override fun positionChange(lastVisibleItem: Int) {

            }
        }

        centersAdapter = CentersAdapter()
        /*handling events inside the list with a live data -> not done yet*/
        centersAdapter.clickEvent.observe(this, Observer {

        })
        centersRec.apply {
            adapter = centersAdapter;
            layoutManager = LinearLayoutManager(this@ListActivity)
            addOnScrollListener(paginationListener)
        }


        var list: ArrayList<CenterResponse.Data.Salon?>? = ArrayList()

        viewModel.centers.observe(this, Observer { contract ->

            when (contract.status) {
                Status.SUCCESS -> {
                    hideLoader()
                    hasMore = contract.data?.pagination?.has_more_pages ?: false
                    randomKey = contract.data?.data?.random_order_key ?: -1

//                    list?.addAll(contract.data?.data?.salons ?: listOf())
//                    println(list?.size)
                    centersAdapter.submitList(contract.data?.data?.salons)

                }
                Status.LOADING -> {
                    showLoader()
                }
                Status.ERROR -> {
                    hideLoader()
                }
            }

        })

        viewModel.getSalons(1, -1)
    }


}