package com.esmaeel.anim.MainExample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esmaeel.anim.Base.ViewsEvents
import com.esmaeel.anim.MainExample.Models.CenterResponse
import com.esmaeel.anim.databinding.CenterItemBinding

class CentersAdapter() :
    ListAdapter<CenterResponse.Data.Salon, CentersAdapter.CentersHolder>(CentersDiffUtil) {
    val clickEvent: MutableLiveData<ViewsEvents> = MutableLiveData()

    private object CentersDiffUtil : DiffUtil.ItemCallback<CenterResponse.Data.Salon>() {
        override fun areItemsTheSame(
            oldItem: CenterResponse.Data.Salon,
            newItem: CenterResponse.Data.Salon
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CenterResponse.Data.Salon,
            newItem: CenterResponse.Data.Salon
        ) = oldItem.id == newItem.id
    }

    override fun submitList(list: List<CenterResponse.Data.Salon?>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CentersHolder(CenterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: CentersHolder, position: Int) {
        holder.bindViews(getItem(position), clickEvent)
    }

    class CentersHolder(val binder: CenterItemBinding) : RecyclerView.ViewHolder(binder.root) {

        fun bindViews(
            salon: CenterResponse.Data.Salon?,
            clickEvent: MutableLiveData<ViewsEvents>
        ) {
            salon?.let {
                binder.centerCenterName.text = it.salon_name
                binder.areaName.text = it.area?.area_name
                binder.centerLocation.text = it.city?.city_name
                binder.centerType.text = it.category?.category_name
                Glide.with(binder.centerSalonImage).load(salon.salon_logo)
                    .into(binder.centerSalonImage)

                binder.root.setOnClickListener {
                    clickEvent.postValue(ViewsEvents.onItemClicked(absoluteAdapterPosition, salon))
                }
            }
        }
    }
}
