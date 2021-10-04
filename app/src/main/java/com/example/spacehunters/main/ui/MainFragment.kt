package com.example.spacehunters.main.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.spacehunters.R
import com.example.spacehunters.R.id.*
import com.example.spacehunters.databinding.MainFragmentBinding
import com.example.spacehunters.main.AppState
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()
  //  private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
    //    setBottomSheetBehavior(binding.bottomSheetDescriptionContainer)
        binding.chipGroup.setSingleSelection(true);
        binding.chipGroup.setOnCheckedChangeListener { chipGroup, id ->
            when (id) {
                chip_two_days_ago -> viewModel.loadData(2)
                chip_yesterday -> viewModel.loadData(1)
                chip_today -> viewModel.loadData()
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
                        errorTV.text = errorTV.text.toString() + appState.error.toString()
                    }
                    AppState.Loading -> {
                        mainView.visibility = View.INVISIBLE
                        binding.loadingLayout.visibility = View.VISIBLE
                    }
                    is AppState.Success -> {
                        loadingLayout.visibility = View.GONE
                        mainView.visibility = View.VISIBLE
                        photoTitle.text = appState.photoOfTheDay.title
                        appState.photoOfTheDay.image?.let {
                            Picasso.get()
                                .load(it)
                                .into(photoOfTheDay, object : Callback {
                                    override fun onSuccess() {
                                        //Toast.makeText(context,"success",Toast.LENGTH_LONG).show()
                                    }

                                    override fun onError(e: Exception?) {
                                        Toast.makeText(context,"error:" + e.toString(),Toast.LENGTH_LONG).show()
                                        e?.printStackTrace()
                                    }
                                })
                        }
                        photoDesRiption.text = appState.photoOfTheDay.description
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
        Toast.makeText(context, getString(R.string.no_photo_this_day), Toast.LENGTH_LONG).show()
    }

//    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
//        bottomSheetBehavior.setHideable(true)
//        bottomSheetBehavior.isDraggable
//    }

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