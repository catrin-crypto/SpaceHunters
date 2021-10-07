package com.example.spacehunters.main.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.spacehunters.R
import com.example.spacehunters.databinding.PhotoFromMarsFragmentBinding
import com.example.spacehunters.main.AppState
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PhotoFromMarsFragment : Fragment() {
    private var _binding: PhotoFromMarsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoFromMarsViewModel by viewModel()
    private var selectedDate: String = "2015-06-03"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotoFromMarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarIconImageView.setOnClickListener({
            val year = 2015
            val month = 6
            val day = 3
            this.context?.let { context ->
                val dpd = DatePickerDialog(
                    context,
                    { view, year, monthOfYear, dayOfMonth ->
                        selectedDate =
                            "" + year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth.toString()
                        loadChosenData()
                    }, year, month, day
                )
                dpd.datePicker.minDate = Date(2012 -1900, 8 - 1, 6).time
                dpd.datePicker.maxDate = Date(2015 -1900, 6 - 1, 3).time
                dpd.show()
            }
        })

        with(binding) {
            viewModel.liveDataToObserve.observe(viewLifecycleOwner, { appState ->
                when (appState) {
                    is AppState.Error -> {
                        Toast.makeText(context, "Error : " + appState.error.toString() , Toast.LENGTH_LONG).show()
                        appState.error.printStackTrace();
                    }

                    is AppState.SuccessMars -> {
                        photoFromMarsAndIdLayout.removeAllViews()
                        for (photoItem in appState.photos) {
                            var marsImage = ImageView(photoFromMarsAndIdLayout.context)
                            marsImage.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

                            photoFromMarsAndIdLayout.addView(marsImage)

                            Picasso.get()
                                .load(photoItem.imgSrc)
                                .into(marsImage, object : Callback {
                                    override fun onSuccess() {
                                        //Toast.makeText(context,"success",Toast.LENGTH_LONG).show()
                                    }

                                    override fun onError(e: Exception?) {
                                        Toast.makeText(context,"error:" + e.toString(),Toast.LENGTH_LONG).show()
                                        e?.printStackTrace()
                                    }
                                })
                            var textId = TextView(photoFromMarsAndIdLayout.context)
                            textId.text = getString(R.string.mars_image_id_photo_signature,photoItem.id)
                            textId.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                            //textId.textSize = R.dimen.text_size.toFloat()
                            photoFromMarsAndIdLayout.addView(textId)
                        }
                    }
                }
            })
        }
        loadChosenData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadChosenData() {
        binding.userChosenDateTv.text = selectedDate
        viewModel.loadData(selectedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PhotoFromMarsFragment()
    }

}