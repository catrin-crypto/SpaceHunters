package com.example.spacehunters.main.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.spacehunters.R
import com.example.spacehunters.databinding.EpicPhotosFragmentBinding
import com.example.spacehunters.main.AppState
import com.example.spacehunters.main.ui.adapters.EpicFragmentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class EpicPhotosFragment : Fragment() {
    private var _binding: EpicPhotosFragmentBinding? = null
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
        val duration: Long = 3000
        binding.chooseEpicDateTv.animate().x(150f).setDuration(duration)
        selectedDate = getString(R.string.epic_default_date)
        binding.epicCalendarIconImageView.setOnClickListener({
            val year = 2019
            val month = 5
            val day = 30
            this.context?.let { context ->
                val dpd = DatePickerDialog(
                    context,
                    { view, year, monthOfYear, dayOfMonth ->
                        selectedDate =
                            "" + String.format("%04d", year) + "-" + String.format(
                                "%02d",
                                (monthOfYear + 1)
                            ) + "-" + String.format("%02d", dayOfMonth)
                        loadChosenData()
                    }, year, month - 1, day
                )
                dpd.show()
            }
        })

        with(binding) {
            viewModel.liveDataToObserve.observe(viewLifecycleOwner, { appState ->
                when (appState) {
                    is AppState.Error -> {
                        Toast.makeText(
                            context,
                            "Error : " + appState.error.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        appState.error.printStackTrace();
                    }

                    is AppState.SuccessEpic -> {
                        if (epicFragmentRecyclerView.adapter == null) {
                            adapter = EpicFragmentAdapter()
                            epicFragmentRecyclerView.adapter = adapter
                        }
                        adapter?.let {
                            it.setPhotos(appState.photos)
                        }
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