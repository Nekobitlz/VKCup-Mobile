package com.nekobitlz.products.features.listeners

import com.nekobitlz.products.data.models.City

interface OnCheckedListener {
    fun onChecked(city: City)
}