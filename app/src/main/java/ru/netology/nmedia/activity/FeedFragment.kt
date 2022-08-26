package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FragmentNewMarkerByCoordinates.Companion.doubleArg1
import ru.netology.nmedia.activity.FragmentNewMarkerByCoordinates.Companion.doubleArg2
import ru.netology.nmedia.activity.FragmentNewMarkerByCoordinates.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.MarkerAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Marker
import ru.netology.nmedia.viewmodel.MarkerViewModel

val coordinatesMoscow = Point(55.7522200, 37.6155600)

class FeedFragment : Fragment() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = MarkerAdapter(object : OnInteractionListener {
            override fun onEdit(marker: Marker) {
                viewModel.edit(marker)
                findNavController().navigate(R.id.action_feedFragment_to_fragmentNewMarkerByMap,
                    Bundle().apply {
                        textArg = marker.name
                        doubleArg1 = marker.latitude
                        doubleArg2 = marker.longitude
                    })
            }

            override fun onRemove(marker: Marker) {
                viewModel.removeById(marker.id)
            }

            override fun onPreview(marker: Marker) {
                findNavController().navigate(R.id.action_feedFragment_to_fragmentMaps,
                    Bundle().apply {
                        textArg = marker.name
                        doubleArg1 = marker.latitude
                        doubleArg2 = marker.longitude
                    })
            }
        })
        binding.list.adapter = adapter

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, getString(R.string.error_loading), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_loading)) { viewModel.loadMarkers() }
                    .show()
            }
        }
        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.markers)
            binding.emptyText.isVisible = state.empty
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshMarkers()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_fragmentNewMarkerByMap,
                //action_feedFragment_to_fragmentNewMarkerByCoordinates)
                Bundle().apply {
                    textArg = null
                    doubleArg1 = coordinatesMoscow.latitude
                    doubleArg2 = coordinatesMoscow.longitude
                })
        }

        return binding.root
    }
}
