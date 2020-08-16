package com.app.travelbuddy.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.travelbuddy.R
import com.app.travelbuddy.databinding.FragmentMainCityBindingImpl
import com.app.travelbuddy.domain.City
import com.app.travelbuddy.service.CityService
import io.material.catalog.draggable.DraggableCoordinatorLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main_city.*

class CityFragment : Fragment() {

    private lateinit var binding: FragmentMainCityBindingImpl
    private lateinit var cityService: CityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityService = CityService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_city, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        main_city_text.text = "Text"
        card.setOnLongClickListener {
            card.isChecked = !card.isChecked
            true
        }
        parentContainer.addDraggableChild(card)
        parentContainer.setViewDragListener(object
            : DraggableCoordinatorLayout.ViewDragListener {

            override fun onViewCaptured(view: View, pointerId: Int) {
                card.isDragged = true
            }

            override fun onViewReleased(view: View, xVelocity: Float, yVelocity: Float) {
                card.isDragged = false
            }
        })

        cityService.getList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.forEach { showCity(it) }
            },
                { t -> Log.e("CITY_SERVICE", t.message, t) })
    }

    fun showCity(city: City) {
        Log.i("CITY", "${city.id} ${city.name} ${city.populationNumber}")
    }


}