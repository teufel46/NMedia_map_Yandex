package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardMarkerBinding
import ru.netology.nmedia.dto.Marker

interface OnInteractionListener {
    fun onEdit(marker: Marker) {}
    fun onRemove(marker: Marker) {}
    fun onPreview(marker: Marker)
}

class MarkerAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Marker, MarkerViewHolder>(MarkerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        val binding = CardMarkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class MarkerViewHolder(
    private val binding: CardMarkerBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(marker: Marker) {
        binding.apply {
            nameLabel.text = marker.name
            published.text =
                "(" + marker.latitude.toString() + ";" + marker.longitude.toString() + ")"

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_marker_options)
                    menu.setGroupVisible(R.id.menu, true)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(marker)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(marker)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            card.setOnClickListener {
                onInteractionListener.onPreview(marker)
            }
        }
    }
}

class MarkerDiffCallback : DiffUtil.ItemCallback<Marker>() {
    override fun areItemsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem == newItem
    }
}
