package com.app.travelbuddy.ui.fragment.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.data.remote.dto.CityDetailResponse
import com.app.travelbuddy.databinding.FragmentMainCityBinding
import com.app.travelbuddy.ui.adapter.AttractionCityAdapter
import com.app.travelbuddy.ui.adapter.ServiceCityAdapter
import com.app.travelbuddy.ui.adapter.TipsCardAdapter
import com.app.travelbuddy.ui.interfaces.CardAttractionListener
import com.app.travelbuddy.ui.interfaces.CardServiceListener
import com.app.travelbuddy.ui.interfaces.CardTipsListener
import com.app.travelbuddy.ui.model.AttractionCityModel
import com.app.travelbuddy.ui.model.ServiceCityModel
import com.app.travelbuddy.ui.model.ServiceCityReviewModel
import com.app.travelbuddy.ui.model.TipModel
import com.app.travelbuddy.ui.model.enumeration.TypeTipModel
import com.app.travelbuddy.ui.viewmodel.CityViewModel
import com.app.travelbuddy.ui.viewmodel.UserViewModel
import com.app.travelbuddy.utils.ConstantUtil
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
class CityFragment : Fragment(), CardAttractionListener, CardServiceListener, CardTipsListener {

    private var attractionCityAdapter = AttractionCityAdapter(listOf(), this)
    private var serviceCityAdapter = ServiceCityAdapter(listOf(), this)
    private var tipCityAdapter = TipsCardAdapter(listOf(), this, 3)
    private var isFavorite: Boolean = false

    private lateinit var binding: FragmentMainCityBinding

    private val cityData by lazy {
        CityFragmentArgs.fromBundle(requireArguments()).cityData
    }

    private val cityViewModel: CityViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_fragment_container
            duration = resources.getInteger(R.integer.cityCardTransitionDuration).toLong()
            scrimColor = Color.TRANSPARENT
        }
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.city_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_option_favorite -> {
                onClickFavorite()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun onClickFavorite() {
        val navController = findNavController()
        userViewModel.getUserLogin().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    Timber.d("status: ${response.status}")
                }
                Resource.Status.ERROR -> {
                    Timber.d("status: ${response.status}")
                    Timber.d("message: ${response.message}")
                    Snackbar.make(
                        binding.scrollCity, R.string.textNeedLogin,
                        Snackbar.LENGTH_LONG
                    ).setAction(R.string.textGoLoginWelcome) {
                        navController.navigate(R.id.main_welcome)
                    }.show()
                }
                Resource.Status.SUCCESS -> {
                    Timber.d("status: ${response.status}")
                    Timber.d("data: ${response.data}")

                    Snackbar.make(
                        binding.scrollCity,
                        "Se marca como favorito: ${cityData.id}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    isFavorite = !isFavorite
                    activity?.invalidateOptionsMenu()
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val itemFavorite = menu.findItem(R.id.toolbar_option_favorite)
        if (isFavorite) {
            itemFavorite.setIcon(R.drawable.ic_icon_favorite)
        } else {
            itemFavorite.setIcon(R.drawable.ic_icon_favorite_outline)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onClickCardAttraction(
        attractionModel: AttractionCityModel,
        cardView: MaterialCardView
    ): View.OnClickListener {
        return View.OnClickListener {
            Snackbar.make(
                binding.scrollCity,
                "Funcionalidad no Implementada",
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
            val direction = CityFragmentDirections.navigateToMainCityService(serviceModel)
            it.findNavController().navigate(direction, extras)
        }
    }

    override fun onClickCardTips(
        tipModel: TipModel,
        cardView: MaterialCardView
    ): View.OnClickListener {
        return View.OnClickListener {
            when (tipModel.type) {
                TypeTipModel.WEBPAGE -> {
                    val url = tipModel.value
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
                TypeTipModel.GEOLOCATION -> {
                    val gmmIntentUri = Uri.parse(tipModel.value)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage(ConstantUtil.PACKAGE_GOOGLE_MAPS)
                    activity?.packageManager?.let { packageManager ->
                        mapIntent.resolveActivity(packageManager)?.let {
                            startActivity(mapIntent)
                        }
                    }
                }
                else -> {
                    context?.copyToClipboard(tipModel.value)
                    Snackbar.make(
                        binding.scrollCity,
                        "Se ha copiado el valor",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun Context.copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip =
            ClipData.newPlainText(
                getString(R.string.appName), text
            )
        clipboard.setPrimaryClip(clip)
    }

    private fun setupObservers() {

        cityViewModel.getDetailByCountryAndCity(cityData.country, cityData.city)
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

    private fun setUI(response: List<CityDetailResponse>) {
        val df = DecimalFormat("#.##")
        binding.cardCityTitle.text = cityData.city.capitalizeWords()
        binding.cardCityRanking.rating = cityData.ranking.toFloat()
        binding.cardCityRankingTitle.text =
            getString(R.string.descriptionRankingCity, df.format(cityData.ranking))
        cityData.imageUrl?.let { url ->
            val urlFull = url.substringBefore('?')
            Glide.with(this).load(urlFull)
                .thumbnail(
                    Glide.with(this).load(url)
                )
                .into(binding.cardCityImage)
        }

        var serviceTransport: ServiceCityModel? = null
        var serviceTransportService: ServiceCityModel? = null
        var serviceActivity: ServiceCityModel? = null
        var serviceFood: ServiceCityModel? = null
        var serviceFoodAttraction: ServiceCityModel? = null
        var serviceService: ServiceCityModel? = null
        var serviceEnvironment: ServiceCityModel? = null

        response.forEach { entity ->
            when {
                entity.entity_type.compareTo("ATTRACTION", true) == 0 -> {
                    val listAttraction = mutableListOf<AttractionCityModel>()
                    entity.entity_value.forEach { listAttraction += AttractionCityModel(it) }
                    attractionCityAdapter.update(listAttraction)
                }
                entity.entity_type.compareTo("TRANSPORT", true) == 0 -> {
                    serviceTransport = ServiceCityModel(
                        getString(R.string.featureTransport),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_transport,
                        R.drawable.im_feature_transport
                    )
                }
                entity.entity_type.compareTo("TRANSPORT_SERVICE", true) == 0 -> {
                    serviceTransportService = ServiceCityModel(
                        getString(R.string.featureTransportService),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_transport_service,
                        R.drawable.im_feature_transport_service
                    )
                }
                entity.entity_type.compareTo("ACTIVITY", true) == 0 -> {
                    serviceActivity = ServiceCityModel(
                        getString(R.string.featureActivity),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_activity,
                        R.drawable.im_feature_activity
                    )
                }
                entity.entity_type.compareTo("FOOD", true) == 0 -> {
                    serviceFood = ServiceCityModel(
                        getString(R.string.featureFood),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_food,
                        R.drawable.im_feature_food
                    )
                }
                entity.entity_type.compareTo("ATTRACTION_FOOD", true) == 0 -> {
                    serviceFoodAttraction = ServiceCityModel(
                        getString(R.string.featureFoodAttraction),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_attraction_food,
                        R.drawable.im_feature_attraction_food
                    )
                }
                entity.entity_type.compareTo("SERVICE", true) == 0 -> {
                    serviceService = ServiceCityModel(
                        getString(R.string.featureService),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_service,
                        R.drawable.im_feature_service
                    )
                }
                entity.entity_type.compareTo("ENVIRONMENT", true) == 0 -> {
                    serviceEnvironment = ServiceCityModel(
                        getString(R.string.featureEnvironment),
                        entity.score_average.toDouble(),
                        entity.reviews.size,
                        entity.entity_value,
                        entity.reviews.map { ServiceCityReviewModel(it.text, it.score.toDouble()) },
                        R.drawable.ic_feature_environment,
                        R.drawable.im_feature_environment
                    )
                }
            }
        }

        val listService = mutableListOf<ServiceCityModel>()
        serviceTransport?.let { listService += it }
        serviceTransportService?.let { listService += it }
        serviceActivity?.let { listService += it }
        serviceFood?.let { listService += it }
        serviceFoodAttraction?.let { listService += it }
        serviceService?.let { listService += it }
        serviceEnvironment?.let { listService += it }
        serviceCityAdapter.update(listService)

        cityData.tips?.let {
            tipCityAdapter.update(it)
        }
    }
}