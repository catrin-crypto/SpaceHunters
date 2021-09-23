package com.example.spacehunters.main.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.spacehunters.R
import com.example.spacehunters.R.id.*
import com.example.spacehunters.databinding.MainFragmentBinding
import com.example.spacehunters.main.AppState
import com.example.spacehunters.main.model.entities.AstroPOD
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(binding.bottomSheetDescriptionContainer)
        binding.chipGroup.setSingleSelection(true);
        binding.chipGroup.setOnCheckedChangeListener { chipGroup, id ->
            when (id) {
                R.id.chip_two_days_ago -> viewModel.loadData(2)
                R.id.chip_yesterday -> viewModel.loadData(1)
                R.id.chip_today -> viewModel.loadData()
            }
        }

        binding.inputLayout.setEndIconOnClickListener() {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        with(binding) {
            viewModel.liveDataToObserve.observe(viewLifecycleOwner, { appState ->
                when (appState) {
                    is AppState.Error -> {
                        mainView.visibility = View.INVISIBLE
                        loadingLayout.visibility = View.GONE
                        errorTV.visibility = View.VISIBLE
                    }
                    AppState.Loading -> {
                        mainView.visibility = View.INVISIBLE
                        binding.loadingLayout.visibility = View.VISIBLE
                    }
                    is AppState.Success -> {
                        loadingLayout.visibility = View.GONE
                        mainView.visibility = View.VISIBLE
                        photoTitle.text = appState.photoOfTheDay.title
                        appState.photoOfTheDay.image.let {
                            Picasso.get().load(it).into(photoOfTheDay)
                        }
                        photoDescription.text = appState.photoOfTheDay.description
                        creationDate.text = appState.photoOfTheDay.date
                        if (appState.photoOfTheDay.image == null) {
                            setDefaultImage()
                        }
                    }
                }
            })
            viewModel.loadData()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDefaultImage() = with(binding) {
        photoOfTheDay.setImageResource(R.drawable.orion_nebula)
        Toast.makeText(context, "Nasa's got no photo this day(", Toast.LENGTH_LONG).show()
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(true);
    }

    companion object {
        const val BUNDLE_EXTRA = "pictureOfTheDay"
        fun newInstance() = MainFragment()
    }

//    companion object {
//        const val BUNDLE_EXTRA = "pictureOfTheDay"
//
//        fun newInstance(bundle: Bundle): MainFragment {
//            val fragment = MainFragment()
//            fragment.arguments = bundle
//            return fragment
//        }
//    }

}