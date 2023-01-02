package com.example.pro_reproductivehealth.ui.registration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.pro_reproductivehealth.R
import com.example.pro_reproductivehealth.databinding.RegistrationFragmentBinding
import com.example.pro_reproductivehealth.viewmodels.RegistrationViewModel
import java.util.*

class RegistrationFragment : Fragment() {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: RegistrationFragmentBinding
    private lateinit var calendar: Calendar
    private var isHospitalSpinnerFirstCreate = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        calendar = Calendar.getInstance()
        binding.fragment = this
        binding.viewModel = viewModel

        binding.hospitalSpinner.prompt = "Поликлиника"

        binding.hospitalSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (isHospitalSpinnerFirstCreate)
                    isHospitalSpinnerFirstCreate = false
                else {
                    if (!viewModel.hospitalList.value.isNullOrEmpty()) {
                        val selectedHospital = viewModel.hospitalList.value!![p2]
                        viewModel.hospital.value = selectedHospital
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        viewModel.hospitalList.observe(viewLifecycleOwner){
            if (!it.contains(viewModel.hospital.value))
                viewModel.hospital.value = ""
            val hospitalArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.spinner_hospital, it)
            hospitalArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.hospitalSpinner.adapter = hospitalArrayAdapter
        }

        viewModel.hospital.observe(viewLifecycleOwner){
            if (it.isEmpty()) {
                binding.hospitalChosenCard.visibility = View.GONE
                binding.hospitalButtonCard.visibility = View.VISIBLE
            } else {
                binding.hospitalButtonCard.visibility = View.GONE
                binding.hospital.text = it
                binding.hospitalChosenCard.visibility = View.VISIBLE
            }
        }

        viewModel.time.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.timeChosenCard.visibility = View.GONE
                binding.timeButtonCard.visibility = View.VISIBLE
            } else {
                binding.timeButtonCard.visibility = View.GONE
                binding.time.text = it
                binding.timeChosenCard.visibility = View.VISIBLE
            }
        }

        viewModel.date.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.dateChosenCard.visibility = View.GONE
                binding.dateButtonCard.visibility = View.VISIBLE
            } else {
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
        val dateDialog = DatePickerDialog(
            requireContext(), viewModel.dateListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dateDialog.datePicker.minDate = calendar.timeInMillis - 1000
        dateDialog.show()
    }
}