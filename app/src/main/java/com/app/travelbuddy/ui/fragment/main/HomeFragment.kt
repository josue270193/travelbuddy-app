package com.app.travelbuddy.ui.fragment.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.data.local.SharedPreferenceStringLiveData
import com.app.travelbuddy.data.local.entity.CountryFull
import com.app.travelbuddy.databinding.FragmentMainHomeBinding
import com.app.travelbuddy.ui.adapter.RemainCityCountryAdapter
import com.app.travelbuddy.ui.adapter.TipCountryAdapter
import com.app.travelbuddy.ui.interfaces.CardCityListener
import com.app.travelbuddy.ui.model.CityModel
import com.app.travelbuddy.ui.model.TipModel
import com.app.travelbuddy.ui.viewmodel.CountryViewModel
import com.app.travelbuddy.utils.ConstantUtil
import com.app.travelbuddy.utils.Resource
import com.app.travelbuddy.utils.StringUtil
import com.app.travelbuddy.utils.StringUtil.capitalizeWords
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), CardCityListener {

    private lateinit var binding: FragmentMainHomeBinding
    private lateinit var urlMapCountry: String
    private var tipCountryAdapter = TipCountryAdapter(listOf())
    private var remainCityCountryAdapter = RemainCityCountryAdapter(listOf(), this)

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val countryViewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.cityCardTransitionDuration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.cityCardTransitionDuration).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        configurationViewTips()
        configurationViewRemainCities()
        configurationViewCountry()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerHome.startShimmer()
    }

    override fun onPause() {
        binding.shimmerHome.stopShimmer()
        super.onPause()
    }

    override fun onClickCard(
        city: CityModel,
        cardView: MaterialCardView
    ): View.OnClickListener {
        return View.OnClickListener {
            val cityTransitionName = getString(R.string.cityCardDetailTransitionName)
            val extras = FragmentNavigatorExtras(
                cardView to cityTransitionName
            )
            val direction = HomeFragmentDirections.navigateToMainCity(city)
            it.findNavController().navigate(direction, extras)
        }
    }

    private fun setupObservers() {
        val observerCountry = Observer<Resource<CountryFull>> {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Timber.d("status: ${it.status}")
                }
                Resource.Status.ERROR -> {
                    Timber.d("status: ${it.status}")
                    Timber.d("message: ${it.message}")
                    binding.shimmerHome.stopShimmer()
                    Snackbar.make(
                        binding.scrollHome,
                        getString(R.string.erroConnection) + "\n" + it.message,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                Resource.Status.SUCCESS -> {
                    Timber.d("status: ${it.status}")
                    Timber.d("data: ${it.data}")
                    binding.shimmerHome.stopShimmer()
                    binding.shimmerHome.visibility = View.GONE
                    binding.scrollHome.visibility = View.VISIBLE
                    it.data?.let { response -> setUI(response) }
                }
            }
        }

        SharedPreferenceStringLiveData(
            sharedPreferences,
            ConstantUtil.PREFERENCE_VARIABLE_COUNTRY,
            ""
        ).observe(
            viewLifecycleOwner, {
                Timber.d("country: $it")
                if (!it.isNullOrBlank()) {
                    countryViewModel.getWithCitiesByCountry(it)
                        .observe(viewLifecycleOwner, observerCountry)
                }
            })
    }


    private fun configurationViewRemainCities() {
        binding.viewRemainCitiesCountry.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = remainCityCountryAdapter
        }
        binding.viewRemainCitiesCountry.adapter = remainCityCountryAdapter
    }

    private fun configurationViewTips() {
        binding.viewTipsCountry.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                val px = resources.getDimensionPixelSize(R.dimen.gridTipsSpacing)
                val spanCount = 2
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val index = parent.getChildLayoutPosition(view)
                    val isLeft = (index % spanCount == 0)
                    outRect.set(
                        if (isLeft) px else px / 2,
                        0,
                        if (isLeft) px / 2 else px,
                        px
                    )
                }
            })
            adapter = tipCountryAdapter
        }
        binding.viewTipsCountry.adapter = tipCountryAdapter
    }

    private fun configurationViewCountry() {
        binding.iconMapCountry.setOnClickListener {
            if (urlMapCountry.isNotBlank()) {
                val gmmIntentUri = Uri.parse(urlMapCountry)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage(ConstantUtil.PACKAGE_GOOGLE_MAPS)
                activity?.packageManager?.let { packageManager ->
                    mapIntent.resolveActivity(packageManager)?.let {
                        startActivity(mapIntent)
                    }
                }
            } else {
                Snackbar.make(
                    binding.scrollHome,
                    getString(R.string.locationNotAvailable),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun setUI(result: CountryFull) {
        val country = result.country
        val cities = result.cities
        val wiki = result.wiki

        // TITLE COUNTRY
        wiki?.wikiGeolocation?.let {
            urlMapCountry = "geo:${it.latitude},${it.longitude}?z=${it.precision}"
        }
        country.name.let {
            binding.titleCountry.text = it.capitalizeWords()
        }

        // FIRST CITY
        val firstCity = cities.getOrNull(0)
        if (firstCity != null) {
            val df = DecimalFormat("#.##")
            val name = firstCity.name
            binding.cardHomeFirst.transitionName = StringUtil.toSnakeCase(name)
            binding.cardHomeFirstTitle.text = name.capitalizeWords()
            binding.cardHomeFirstRanking.text = df.format(firstCity.ranking)
            binding.cardHomeFirst.setOnClickListener(
                onClickCard(
                    CityModel(
                        firstCity.id,
                        firstCity.name,
                        country.name,
                        firstCity.ranking,
                        firstCity.imageUrl
                    ),
                    binding.cardHomeFirst
                )
            )
            val urlImageCity = firstCity.imageUrl
            urlImageCity?.let {
                Glide.with(this).load(it)
                    .transition(withCrossFade())
                    .into(binding.cardHomeFirstImage)
            }
        } else {
            binding.layoutCardHomeFirst.visibility = View.INVISIBLE
        }

        // SECOND CITY
        val secondCity = cities.getOrNull(1)
        if (secondCity != null) {
            val df = DecimalFormat("#.##")
            val name = secondCity.name
            binding.cardHomeSecond.transitionName = StringUtil.toSnakeCase(name)
            binding.cardHomeSecondTitle.text = name.capitalizeWords()
            binding.cardHomeSecondRanking.text = df.format(secondCity.ranking)
            binding.cardHomeSecond.setOnClickListener(
                onClickCard(
                    CityModel(
                        secondCity.id,
                        secondCity.name,
                        country.name,
                        secondCity.ranking,
                        secondCity.imageUrl
                    ),
                    binding.cardHomeSecond
                )
            )
            val urlImageCity = secondCity.imageUrl
            urlImageCity?.let {
                Glide.with(this).load(it)
                    .transition(withCrossFade())
                    .into(binding.cardHomeSecondImage)
            }
        } else {
            binding.layoutCardHomeSecond.visibility = View.INVISIBLE
        }

        // THIRD CITY
        val thirdCity = cities.getOrNull(2)
        if (thirdCity != null) {
            val df = DecimalFormat("#.##")
            val name = thirdCity.name
            binding.cardHomeThird.transitionName = StringUtil.toSnakeCase(name)
            binding.cardHomeThirdTitle.text = name.capitalizeWords()
            binding.cardHomeThirdRanking.text = df.format(thirdCity.ranking)
            binding.cardHomeThird.setOnClickListener(
                onClickCard(
                    CityModel(
                        thirdCity.id,
                        thirdCity.name,
                        country.name,
                        thirdCity.ranking,
                        thirdCity.imageUrl
                    ),
                    binding.cardHomeThird
                )
            )
            val urlImageCity = thirdCity.imageUrl
            urlImageCity?.let {
                Glide.with(this).load(it)
                    .transition(withCrossFade())
                    .into(binding.cardHomeThirdImage)
            }
        } else {
            binding.layoutCardHomeThird.visibility = View.INVISIBLE
        }

        // REMAIN CITIES
        try {
            cities.slice(3 until cities.size).let {
                val remainCities = mutableListOf<CityModel>()
                it.forEach { city ->
                    remainCities += CityModel(
                        city.id,
                        city.name,
                        country.name,
                        city.ranking,
                        city.imageUrl
                    )
                }
                remainCityCountryAdapter.update(remainCities)
            }
        } catch (err: Exception) {
            Timber.e(err)
        }

        // WIKI COUNTRY
        wiki?.let { wikiData ->
            val tips = mutableListOf<TipModel>()
            wikiData.wikiPopulation?.let { tips += TipModel(it.description!!, it.value!!) }
            wikiData.wikiWebPage?.let { it ->
                tips += TipModel(
                    it.wikiWebPage.description!!,
                    it.wikiWebPageDetails?.map { item -> item.value }?.joinToString()!!
                )
            }
            wikiData.wikiGeolocation?.let {
                tips += TipModel(
                    it.description!!,
                    "${it.latitude!!}, ${it.longitude!!}"
                )
            }
            wikiData.wikiCurrency?.let {
                tips += TipModel(
                    it.description!!,
                    it.value!!,
                    getString(R.color.colorCountryTipCurrency)
                )
            }
            wikiData.wikiLanguage?.let { tips += TipModel(it.description!!, it.value!!) }
            wikiData.wikiDemonym?.let { tips += TipModel(it.description!!, it.value!!) }
            wikiData.wiki.emoji?.let { tips += TipModel(getString(R.string.countryTipEmoji), it) }
            tipCountryAdapter.update(tips)
            Timber.d("tips: ${tips.size}")
        }
    }
}