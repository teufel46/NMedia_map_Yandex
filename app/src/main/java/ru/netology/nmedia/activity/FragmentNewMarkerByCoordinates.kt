package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewMarkerByCoordinatesBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.DoubleArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.MarkerViewModel

class FragmentNewMarkerByCoordinates : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
        var Bundle.doubleArg1 : Double by DoubleArg
        var Bundle.doubleArg2 : Double by DoubleArg
    }

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment,
    )

    private var fragmentBinding: FragmentNewMarkerByCoordinatesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_new_marker, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.save -> {
                fragmentBinding?.let {
                    viewModel.changeContent(
                        it.editNameMarker.text.toString(),
                        it.editLatitude.text.toString().toDouble(),
                        it.editLongitude.text.toString().toDouble()
                    )
                    viewModel.save()
                    AndroidUtils.hideKeyboard(requireView())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewMarkerByCoordinatesBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding

        arguments?.textArg
            ?.let(binding.editNameMarker::setText)

        binding.editNameMarker.requestFocus()

        viewModel.markerCreated.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }


        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}