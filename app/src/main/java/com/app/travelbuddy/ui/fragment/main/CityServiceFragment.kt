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
import com.app.travelbuddy.ui.fragment.main.CityServiceFragmentArgs.Companion.fromBundle
import com.app.travelbuddy.ui.model.ReviewServiceModel
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CityServiceFragment : Fragment() {

    private var reviewServiceAdapter = ReviewServiceAdapter(listOf())

    private lateinit var binding: FragmentMainCityServiceBinding

    private val serviceData by lazy {
        fromBundle(requireArguments()).serviceData
    }

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
        val listReview = mutableListOf<ReviewServiceModel>()
        serviceData.reviews?.map { listReview += ReviewServiceModel(it.text, it.ranking) }
        reviewServiceAdapter.update(listReview)

        binding.cityServiceTitle.text = serviceData.title
        binding.cityServiceRanking.rating = serviceData.ranking.toFloat()
    }
}