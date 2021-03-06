/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

/**
 * A fragment representing a single Plant detail screen.
 */
class PlantDetailFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Retrieve the arguments safely.
        val plantId = PlantDetailFragmentArgs.fromBundle(arguments).itemId

        val factory = InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), plantId)
        val plantDetailViewModel = ViewModelProviders.of(this, factory)
                .get(PlantDetailViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
                inflater, R.layout.fragment_plant_detail, container, false).apply {
            viewModel = plantDetailViewModel
            setLifecycleOwner(this@PlantDetailFragment)
            fab.setOnClickListener { view ->
                plantDetailViewModel.addPlantToGarden()
                Snackbar.make(view, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show()
            }
        }

        // Navigation to Fragments / Activities
        binding.detailsActivity.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_plant_detail_fragment_to_gardenActivity))

        // OR
        binding.detailsActivity.setOnClickListener {
            // Some Analytics code, other stuff here
            it.findNavController().navigate(R.id.action_plant_detail_fragment_to_gardenActivity)
        }

        return binding.root
    }

    companion object {

        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "itemId"
    }
}
