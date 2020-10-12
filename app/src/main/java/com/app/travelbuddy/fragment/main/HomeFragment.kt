package com.app.travelbuddy.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.travelbuddy.R
import com.app.travelbuddy.interfaces.CardCityListener
import com.app.travelbuddy.adapter.RemainCityCountryAdapter
import com.app.travelbuddy.adapter.TipCountryAdapter
import com.app.travelbuddy.databinding.FragmentMainHomeBinding
import com.app.travelbuddy.domain.City
import com.app.travelbuddy.domain.RemainCityModel
import com.app.travelbuddy.domain.TipModel
import com.app.travelbuddy.domain.WikiDetail
import com.app.travelbuddy.service.CityService
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class HomeFragment : Fragment(), CardCityListener {

    private lateinit var binding: FragmentMainHomeBinding
    private lateinit var cityService: CityService
    private lateinit var tipCountryAdapter: TipCountryAdapter
    private lateinit var remainCityCountryAdapter: RemainCityCountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityService = CityService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false)

        binding.viewTipsCountry.layoutManager = GridLayoutManager(this.context, 2)
        tipCountryAdapter = TipCountryAdapter(listOf())
        binding.viewTipsCountry.adapter = tipCountryAdapter

        binding.viewRemainCitiesCountry.layoutManager = GridLayoutManager(this.context, 2)
        remainCityCountryAdapter = RemainCityCountryAdapter(listOf(), this)
        binding.viewRemainCitiesCountry.adapter = remainCityCountryAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cityService.getList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                Log.i("HOME", "Cities: $result.size")
                setUI(result)
                binding.shimmerHome.stopShimmer()
                binding.shimmerHome.visibility = View.GONE
                binding.scrollHome.visibility = View.VISIBLE
            }

    }

    private fun setUI(result: List<City>) {
        setRankingFirst(result.getOrNull(0))
        setRankingSecond(result.getOrNull(1))
        setRankingThird(result.getOrNull(2))
        try {
            val remainCities = result.slice(3 until result.size)
            setRemainCities(remainCities)
        } catch (err: Exception) {
            Log.e("HOME", err.toString())
        }

        val countryWiki = obtainCountryWiki(result)
        countryWiki?.let { setTipsCountry(it) }
    }

    private fun setRemainCities(citiesData: List<City>) {
        val remainCities = mutableListOf<RemainCityModel>()
        citiesData.forEach { item ->
            remainCities += RemainCityModel(
                item,
                item.city.name,
                item.rating_average,
                obtainCityImage(item)
            )
        }
        remainCityCountryAdapter.update(remainCities)
    }

    private fun setTipsCountry(countryWiki: WikiDetail) {
        val tips = mutableListOf<TipModel>()
        countryWiki.population?.let { tips += TipModel(it.description!!, it.value!!) }
        countryWiki.webpage?.let { tips += TipModel(it.description!!, it.value!!.joinToString()) }
        countryWiki.geolocation?.let {
            tips += TipModel(
                it.description!!,
                "${it.latitude!!}, ${it.longitude!!}"
            )
        }
        countryWiki.currency?.let { tips += TipModel(it.description!!, it.value!!, "#FFEA77") }
        countryWiki.language?.let { tips += TipModel(it.description!!, it.value!!) }
        countryWiki.demonym?.let { tips += TipModel(it.description!!, it.value!!) }
        countryWiki.emoji?.let { tips += TipModel("Emoji", it) }

        tipCountryAdapter.update(tips)
    }

    private fun obtainCountryWiki(result: List<City>): WikiDetail? {
        val data = result.sortedByDescending { city -> city.updatedAt }
            .find { data -> data.country.wiki != null }
        return data?.country?.wiki
    }

    private fun setRankingThird(cityData: City?) {
        if (cityData != null) {
            val df = DecimalFormat("#.##")
            binding.cardHomeThirdTitle.text = cityData.city.name
            binding.cardHomeThirdRanking.text = df.format(cityData.rating_average)
            binding.cardHomeThird.setOnClickListener(onClickCity(cityData))
            val urlImageCity = obtainCityImage(cityData)
            urlImageCity?.let { Glide.with(this).load(it).into(binding.cardHomeThirdImage) }
        } else {
            binding.layoutCardHomeThird.visibility = View.INVISIBLE
        }
    }

    private fun setRankingSecond(cityData: City?) {
        if (cityData != null) {
            val df = DecimalFormat("#.##")
            binding.cardHomeSecondTitle.text = cityData.city.name
            binding.cardHomeSecondRanking.text = df.format(cityData.rating_average)
            binding.cardHomeSecond.setOnClickListener(onClickCity(cityData))
            val urlImageCity = obtainCityImage(cityData)
            urlImageCity?.let { Glide.with(this).load(it).into(binding.cardHomeSecondImage) }
        } else {
            binding.layoutCardHomeSecond.visibility = View.INVISIBLE
        }
    }

    private fun setRankingFirst(cityData: City?) {
        if (cityData != null) {
            val df = DecimalFormat("#.##")
            binding.cardHomeFirstTitle.text = cityData.city.name
            binding.cardHomeFirstRanking.text = df.format(cityData.rating_average)
            binding.cardHomeFirst.setOnClickListener(onClickCity(cityData))
            val urlImageCity = obtainCityImage(cityData)
            urlImageCity?.let { Glide.with(this).load(it).into(binding.cardHomeFirstImage) }
        } else {
            binding.layoutCardHomeFirst.visibility = View.INVISIBLE
        }
    }

    private fun obtainCityImage(cityData: City): String? {
        return cityData.city.wiki?.images?.value?.get(0)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerHome.startShimmer()
    }

    override fun onPause() {
        binding.shimmerHome.stopShimmer()
        super.onPause()
    }

    override fun onClickCity(cityData: City): View.OnClickListener {
        return View.OnClickListener {
            val direction = HomeFragmentDirections.navigateToMainCity(cityData)
            findNavController().navigate(direction)
        }
    }
}