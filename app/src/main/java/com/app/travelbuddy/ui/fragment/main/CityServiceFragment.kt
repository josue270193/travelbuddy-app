package com.app.travelbuddy.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.travelbuddy.R
import com.app.travelbuddy.databinding.FragmentMainCityServiceBinding
import com.app.travelbuddy.ui.adapter.ReviewServiceAdapter
import com.app.travelbuddy.ui.model.ReviewServiceModel
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CityServiceFragment : Fragment() {

    private var reviewServiceAdapter = ReviewServiceAdapter(listOf())

    private lateinit var binding: FragmentMainCityServiceBinding

//    private val cityServiceData by lazy {
//        fromBundle(requireArguments()).cityServiceData
//    }

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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_city_service, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setUI()
    }

    private fun setupRecyclerView() {
        // REVIEWS
        binding.viewCityServiceReviews.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = reviewServiceAdapter
        }
        binding.viewCityServiceReviews.adapter = reviewServiceAdapter
    }

    private fun setUI() {
//        val df = DecimalFormat("#.##")
//        binding.cardCityTitle.text = cityData.name.capitalizeWords()
//        binding.cardCityRanking.rating = cityData.ranking.toFloat()
//        binding.cardCityRankingTitle.text = df.format(cityData.ranking)
//        cityData.imageUrl?.let { url ->
//            val urlFull = url.substringBefore('?')
//            Glide.with(this).load(urlFull)
//                .thumbnail(
//                    Glide.with(this).load(url)
//                )
//                .into(binding.cardCityImage)
//        }
//
        val listReview = listOf(
            ReviewServiceModel("Review 1", 2.1),
            ReviewServiceModel("Review 2", 2.2),
            ReviewServiceModel("Review 3", 2.3),
            ReviewServiceModel("Review 4", 2.4),
            ReviewServiceModel("Review 5", 2.5),
            ReviewServiceModel("Review 6", 2.6),
            ReviewServiceModel("Review 7", 2.7),
            ReviewServiceModel("Review 8", 2.8),
            ReviewServiceModel("Review 9", 2.9)
        )
        reviewServiceAdapter.update(listReview)
//
//
//        val listService = listOf(
//            ServiceCityModel("Service 1", 3.5, 1000, R.drawable.ic_feature_transport),
//            ServiceCityModel("Service 2", 3.5, 1000, R.drawable.ic_feature_transport),
//            ServiceCityModel("Service 3", 3.5, 1000, R.drawable.ic_feature_transport),
//            ServiceCityModel("Service 4", 3.5, 1000, R.drawable.ic_feature_transport),
//            ServiceCityModel("Service 5", 3.5, 1000, R.drawable.ic_feature_transport),
//            ServiceCityModel("Service 6", 3.5, 1000, R.drawable.ic_feature_transport)
//        )
//        serviceCityAdapter.update(listService)
//
//
//        val listTips = listOf(
//            TipModel("Descripcion 1", "Valor"),
//            TipModel("Descripcion 2", "Valor"),
//            TipModel("Descripcion 3", "Valor"),
//        )
//        tipCityAdapter.update(listTips)

    }
}