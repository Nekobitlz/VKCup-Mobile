package com.nekobitlz.products.data.requests

import com.nekobitlz.products.data.models.City
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGetCitiesRequest(countryId: Int = 1) : VKRequest<List<City>>("database.getCities") {
    init {
        addParam("country_id", countryId)
    }

    override fun parse(r: JSONObject): List<City> {
        val cities = r.getJSONObject("response").getJSONArray("items")

        val result = mutableListOf<City>()

        for (i in 0 until cities.length()) {
            result.add(City.parse(cities.getJSONObject(i)))
        }

        return result
    }
}