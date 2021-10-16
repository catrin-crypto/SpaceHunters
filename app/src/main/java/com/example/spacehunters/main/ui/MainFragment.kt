package com.example.spacehunters.main.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.spacehunters.R
import com.example.spacehunters.R.id.*
import com.example.spacehunters.databinding.MainFragmentStartBinding
import com.example.spacehunters.main.AppState
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import android.view.animation.Animation
import android.view.animation.ScaleAnimation


class MainFragment : Fragment() {
    private var _binding: MainFragmentStartBinding? = null
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
        _binding = MainFragmentStartBinding.inflate(inflater, container, false)
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
                        animateComponents()
                        loadingLayout.visibility = View.GONE
                        mainView.visibility = View.VISIBLE
                        photoTitle.text = appState.photoOfTheDay.title
                        appState.photoOfTheDay.image?.let {
                            Picasso.get()
                                .load(it)
                                .into(photoOfTheDay, object : Callback {
                                    override fun onSuccess() {
                                        scaleView(photoOfTheDay, 0.01f, 1f)
                                    }

                                    override fun onError(e: Exception?) {
                                        Toast.makeText(
                                            context,
                                            "error:" + e.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
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

    fun scaleView(v: View, startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            startScale, endScale,  // Start and end values for the X axis scaling
            startScale, endScale,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0.5f // Pivot point of Y scaling
        )
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 2000
        anim.interpolator = DecelerateInterpolator()
        v.startAnimation(anim)
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
    private fun animateComponents() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.main_fragment_pod_loaded)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(binding.mainView, transition)
        constraintSet.applyTo(binding.mainView)
    }


}