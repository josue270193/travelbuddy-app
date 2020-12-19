package com.app.josuelopez.travelbuddy.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.data.local.entity.User
import com.app.josuelopez.travelbuddy.databinding.FragmentMainUserBinding
import com.app.josuelopez.travelbuddy.ui.adapter.FavoriteCityAdapter
import com.app.josuelopez.travelbuddy.ui.interfaces.CardFavoriteListener
import com.app.josuelopez.travelbuddy.ui.model.FavoriteCityModel
import com.app.josuelopez.travelbuddy.ui.viewmodel.UserViewModel
import com.app.josuelopez.travelbuddy.utils.Resource
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserFragment : Fragment(), CardFavoriteListener {

    private lateinit var binding: FragmentMainUserBinding

    private var favoriteCityAdapter = FavoriteCityAdapter(listOf(), this)

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_user, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        val navController = findNavController()
        userViewModel.getUserLogin().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    Timber.d("status: ${response.status}")
                }
                Resource.Status.ERROR -> {
                    Timber.d("status: ${response.status}")
                    Timber.d("message: ${response.message}")
                    navController.navigate(R.id.main_welcome)
                }
                Resource.Status.SUCCESS -> {
                    Timber.d("status: ${response.status}")
                    Timber.d("data: ${response.data}")
                    response.data?.let { setUI(it.user) }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        // FAVORITES
        binding.viewFavoriteUser.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteCityAdapter
        }
        binding.viewFavoriteUser.adapter = favoriteCityAdapter
    }

    private fun setUI(user: User) {
        binding.nameUser.text = user.name
    }

    override fun onClickCardFavorite(
        favoriteModel: FavoriteCityModel,
        cardView: MaterialCardView
    ): View.OnClickListener {
        return View.OnClickListener {

        }
    }
}