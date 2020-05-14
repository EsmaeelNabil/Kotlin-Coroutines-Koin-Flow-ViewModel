package com.esmaeel.anim.MainExample

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.esmaeel.anim.Base.BaseActivity
import com.esmaeel.anim.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.ExperimentalTime

class MainActivity : BaseActivity() {

    val viewModel: GlobalViewModel by viewModel()


    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupUI()

        viewModel.getCountries().observe(this, Observer {
            it?.let { contract ->
                when (contract.status) {

                    Status.SUCCESS -> {
                        contract.data?.let { Toast.makeText(applicationContext, contract.data.body()?.data?.get(0)?.country_name, Toast.LENGTH_LONG).show() }
                        hideLoader()
                    }

                    Status.LOADING -> {
                        showLoader()
                    }

                    Status.ERROR -> {
                        hideLoader()
                    }
                }
            }
        })

        viewModel.getUserDetails().observe(this, Observer {
            it?.let { contract ->
                when (contract.status) {

                    Status.SUCCESS -> {
                        contract.data?.let {
                            Toast.makeText(
                                applicationContext,
                                contract.data.body()?.data?.user?.email,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        hideLoader()
                    }

                    Status.LOADING -> {
                        showLoader()
                    }

                    Status.ERROR -> {
                        hideLoader()
                    }
                }
            }
        })


    }

    private fun setupUI() {
        fab.setOnClickListener {
            click.startTransform()
            card.startTransformWithDelay(400)
        }

        done.setOnClickListener {
            card.finishTransform()
            click.finishTransformWithDelay(450)
        }

        countryBtn.setOnClickListener {
            viewModel.getCountries()
        }

        infoButton.setOnClickListener {
            viewModel.getUserDetails().value
        }

    }


}
