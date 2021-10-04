package com.example.spacehunters.main.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.spacehunters.R
import com.example.spacehunters.databinding.EpicPhotosFragmentBinding
import com.example.spacehunters.databinding.PhotoFromMarsFragmentBinding
import com.example.spacehunters.main.AppState
import com.example.spacehunters.main.ui.adapters.EpicFragmentAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class EpicPhotosFragment : Fragment() {
    private var _binding : EpicPhotosFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpicPhotosViewModel by viewModel()
    private var selectedDate: String = ""


    private var adapter: EpicFragmentAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EpicPhotosFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = getString(R.string.epic_default_date)
        binding.epicCalendarIconImageView.setOnClickListener({
            val year = 2021
            val month = 6
            val day = 3
            this.context?.let { context ->
                val dpd = DatePickerDialog(
                    context,
                    { view, year, monthOfYear, dayOfMonth ->
                        selectedDate =
                            "" + year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth.toString()
                        loadChosenData()
                    }
                        , year, month, day
                )
               // dpd.datePicker.minDate = Date(2012 -1900, 8 - 1, 6).time
               // dpd.datePicker.maxDate = Date(2015 -1900, 6 - 1, 3).time
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

                    is AppState.SuccessEpic -> {
                        adapter = EpicFragmentAdapter().apply{
                            setPhotos(appState.photos)
                        }
                        epicFragmentRecyclerView.adapter = adapter
                    }
                }
            })
        }
        loadChosenData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadChosenData() {
        binding.userChosenDateEpicTv.text = selectedDate
        viewModel.loadData(selectedDate)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = EpicPhotosFragment()
    }

}