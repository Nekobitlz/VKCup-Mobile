package com.nekobitlz.products.features.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.products.R
import com.nekobitlz.products.data.models.City
import com.nekobitlz.products.features.listeners.OnCheckedListener
import kotlinx.android.synthetic.main.item_city.view.*

class CityAdapter(private val listener: OnCheckedListener) : ListAdapter<City, CityAdapter.CityViewHolder>(CityDiffUtil) {

    private var checkedCity: City? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)

        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object CityDiffUtil : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(city: City) {
            itemView.apply {
                tv_city_name.text = city.name

                if (city.isChecked) {
                    checkedCity = city
                    iv_city_checked.visibility = View.VISIBLE
                } else {
                    iv_city_checked.visibility = View.GONE
                }

                setOnClickListener {
                    checkedCity?.isChecked = false
                    city.isChecked = true

                    listener.onChecked(city)
                    notifyDataSetChanged()
                }
            }
        }
    }
}