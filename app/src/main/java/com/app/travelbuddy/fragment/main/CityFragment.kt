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
import com.app.travelbuddy.fragment.main.CityFragmentArgs.Companion.fromBundle
import com.app.travelbuddy.service.CityService
import com.app.travelbuddy.service.NplService

class CityFragment : Fragment() {

    private lateinit var binding: FragmentMainCityBindingImpl
    private lateinit var cityService: CityService

    val cityData by lazy {
        fromBundle(requireArguments()).cityData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityService = CityService.create()
//        nplService = NplService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_city, container, false)
//        cityRatingTotal = binding.cityRatingTotal
//        cityRatingFeatureTransport = binding.cityRatingFeatureTransport
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.i("CITY", "Data: $cityData")

//        main_city_text.text = "Text"
//        card.setOnLongClickListener {
//            card.isChecked = !card.isChecked
//            true
//        }
//        parentContainer.addDraggableChild(card)
//        parentContainer.setViewDragListener(object
//            : DraggableCoordinatorLayout.ViewDragListener {
//
//            override fun onViewCaptured(view: View, pointerId: Int) {
//                card.isDragged = true
//            }
//
//            override fun onViewReleased(view: View, xVelocity: Float, yVelocity: Float) {
//                card.isDragged = false
//            }
//        })

//        cityService.getList()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ result ->
//                result.forEach { showCity(it) }
//            },
//                { t -> Log.e("CITY_SERVICE", t.message, t) })

//        val request = NplRequest()
//        request.type = "azure"
//        request.text =
//            "Hay otro bus 8 que funciona los domingos, no es semirrápido y dice x Liniers. Tarda más en llegar porque no toma autopista y los deja cerca de Plaza de Mayo también."
//        nplService.postSentimentAnalysis(request)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ result ->
//                var total = result.sentimentScore?.toDouble()!!
//                result.sentiment?.forEach { nplSentiment ->
//                    run {
//                        val positive = nplSentiment.confidenceScores?.positive?.toDouble()!!
//                        val negative = nplSentiment.confidenceScores?.negative?.toDouble()!!
//                        val neutral = nplSentiment.confidenceScores?.neutral?.toDouble()!!
//                        var subtotal = positive
//                        if (positive < negative) {
//                            subtotal = -negative
//                        }
//                        if (subtotal < neutral) {
//                            subtotal = 0.0
//                        }
//                        total = (total + subtotal) / 2
//                    }
//                }
//                Log.i("CITY_SERVICE" , "Total[1.0:-1.0]: $total")
//                total += 1
//                setRatingCity(total)
//            },
//                { t -> Log.e("CITY_SERVICE", t.message, t) })

    }

//    private fun setRatingCity(total: Double) {
//        cityRatingTotal.max = 2
//        cityRatingTotal.rating = total.toFloat()
//        cityRatingFeatureTransport.max = 2
//        cityRatingFeatureTransport.rating = total.toFloat()
//    }

//    fun showCity(city: City) {
////        Log.i("CITY", "${city.id} ${city.name} ${city.populationNumber}")
//    }

}