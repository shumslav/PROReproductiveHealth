package com.example.pro_reproductivehealth.ui.registration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pro_reproductivehealth.R
import com.example.pro_reproductivehealth.databinding.RegistrationFragmentBinding
import com.example.pro_reproductivehealth.viewmodels.RegistrationViewModel
import java.util.*

class RegistrationFragment : Fragment() {

    private lateinit var viewModel:RegistrationViewModel
    private lateinit var binding:RegistrationFragmentBinding
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        calendar = Calendar.getInstance()

        viewModel.time.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.timeChosenCard.visibility = View.GONE
                binding.timeButtonCard.visibility = View.VISIBLE
            }
            else{
                binding.timeButtonCard.visibility = View.GONE
                binding.time.text = it
                binding.timeChosenCard.visibility = View.VISIBLE
            }
        }

        viewModel.date.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.dateChosenCard.visibility = View.GONE
                binding.dateButtonCard.visibility = View.VISIBLE
            }
            else{
                binding.dateButtonCard.visibility = View.GONE
                binding.date.text = it
                binding.dateChosenCard.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    fun setTime() {
        TimePickerDialog(
            requireContext(), viewModel.timeListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
            .show()
    }

    fun setDate() {
        DatePickerDialog(
            requireContext(), viewModel.dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }
}