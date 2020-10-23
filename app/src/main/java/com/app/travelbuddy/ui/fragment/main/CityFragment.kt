package com.app.travelbuddy.ui.fragment.main

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.data.remote.dto.CityDetailResponse
import com.app.travelbuddy.databinding.FragmentMainCityBinding
import com.app.travelbuddy.ui.adapter.AttractionCityAdapter
import com.app.travelbuddy.ui.adapter.ServiceCityAdapter
import com.app.travelbuddy.ui.adapter.TipCountryAdapter
import com.app.travelbuddy.ui.fragment.main.CityFragmentArgs.Companion.fromBundle
import com.app.travelbuddy.ui.interfaces.CardAttractionListener
import com.app.travelbuddy.ui.interfaces.CardServiceListener
import com.app.travelbuddy.ui.model.AttractionCityModel
import com.app.travelbuddy.ui.model.ServiceCityModel
import com.app.travelbuddy.ui.model.TipModel
import com.app.travelbuddy.ui.viewmodel.CityViewModel
import com.app.travelbuddy.utils.Resource
import com.app.travelbuddy.utils.StringUtil.capitalizeWords
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.DecimalFormat


@AndroidEntryPoint
class CityFragment : Fragment(), CardAttractionListener, CardServiceListener {

    private var attractionCityAdapter = AttractionCityAdapter(listOf(), this)
    private var serviceCityAdapter = ServiceCityAdapter(listOf(), this)
    private var tipCityAdapter = TipCountryAdapter(listOf())

    private lateinit var binding: FragmentMainCityBinding

    private val cityData by lazy {
        fromBundle(requireArguments()).cityData
    }
    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_fragment_container
            duration = resources.getInteger(R.integer.cityCardTransitionDuration).toLong()
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_city, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    override fun onClickCard(
        attractionModel: AttractionCityModel,
        cardView: MaterialCardView
    ): View.OnClickListener {
        return View.OnClickListener {
            Snackbar.make(
                binding.scrollCity,
                "Ir a Atraccion",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onClickCard(
        serviceModel: ServiceCityModel,
        cardView: MaterialCardView
    ): View.OnClickListener {
        return View.OnClickListener {
            val cityTransitionName = getString(R.string.cityServiceCardDetailTransitionName)
            val extras = FragmentNavigatorExtras(
                cardView to cityTransitionName
            )
            val direction = CityFragmentDirections.navigateToMainCityService()
            it.findNavController().navigate(direction, extras)
        }
    }

    private fun setupObservers() {

        cityViewModel.getDetailByCountryAndCity(cityData.city, cityData.country)
            .observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("status: ${it.status}")
                    }
                    Resource.Status.ERROR -> {
                        Timber.d("status: ${it.status}")
                        Timber.d("message: ${it.message}")
                        Snackbar.make(
                            binding.scrollCity,
                            getString(R.string.erroConnection) + "\n" + it.message,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("status: ${it.status}")
                        Timber.d("data: ${it.data}")
                        it.data?.let { response -> setUI(response) }
                    }
                }
            })
    }

    private fun setupRecyclerView() {
        // ATTRACTIONS
        binding.viewAttractionCity.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = attractionCityAdapter
        }
        binding.viewAttractionCity.adapter = attractionCityAdapter

        // SERVICES
        binding.viewServicesCity.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = serviceCityAdapter
        }
        binding.viewServicesCity.adapter = serviceCityAdapter

        // TIPS
        binding.viewTipsCity.apply {
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
            adapter = tipCityAdapter
        }
        binding.viewTipsCity.adapter = tipCityAdapter
    }

    private fun setUI(response: CityDetailResponse) {
        val df = DecimalFormat("#.##")
        binding.cardCityTitle.text = cityData.city.capitalizeWords()
        binding.cardCityRanking.rating = cityData.ranking.toFloat()
        binding.cardCityRankingTitle.text = df.format(cityData.ranking)
        cityData.imageUrl?.let { url ->
            val urlFull = url.substringBefore('?')
            Glide.with(this).load(urlFull)
                .thumbnail(
                    Glide.with(this).load(url)
                )
                .into(binding.cardCityImage)
        }

        response.entity_type

        val listAttraction = listOf(
            AttractionCityModel("Atracccion 1"),
            AttractionCityModel("Atracccion 2"),
            AttractionCityModel("Atracccion 3"),
            AttractionCityModel("Atracccion 4"),
            AttractionCityModel("Atracccion 5"),
            AttractionCityModel("Atracccion 6"),
            AttractionCityModel("Atracccion 7")
        )
        attractionCityAdapter.update(listAttraction)


        val listService = listOf(
            ServiceCityModel("Service 1", 3.5, 1000, R.drawable.ic_feature_transport),
            ServiceCityModel("Service 2", 3.5, 1000, R.drawable.ic_feature_transport),
            ServiceCityModel("Service 3", 3.5, 1000, R.drawable.ic_feature_transport),
            ServiceCityModel("Service 4", 3.5, 1000, R.drawable.ic_feature_transport),
            ServiceCityModel("Service 5", 3.5, 1000, R.drawable.ic_feature_transport),
            ServiceCityModel("Service 6", 3.5, 1000, R.drawable.ic_feature_transport)
        )
        serviceCityAdapter.update(listService)


        val listTips = listOf(
            TipModel("Descripcion 1", "Valor"),
            TipModel("Descripcion 2", "Valor"),
            TipModel("Descripcion 3", "Valor"),
        )
        tipCityAdapter.update(listTips)

    }
}