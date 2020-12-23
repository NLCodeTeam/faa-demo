package com.kapait.faa.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.kapait.faa.R
import com.kapait.faa.databinding.FragmentProfileBinding
import com.kapait.faa.ui.common.material_date_picker.SlideDatePickerDialog
import com.kapait.faa.ui.common.material_date_picker.callback.SlideDatePickerDialogCallback
import java.util.*

class ProfileFragment: Fragment(), SlideDatePickerDialogCallback {

    private var profileBinding: FragmentProfileBinding? = null
    private val binding
        get() = profileBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.country.characteristicName.text = getString(R.string.country)
        binding.height.characteristicName.text = getString(R.string.height)
        binding.weight.characteristicName.text = getString(R.string.weight)
        binding.bodyType.characteristicName.text = getString(R.string.body_type)
        binding.hairColor.characteristicName.text = getString(R.string.hair_color)
        binding.eyeColor.characteristicName.text = getString(R.string.eye_color)
        binding.skills.characteristicName.text = getString(R.string.skills)
        fillOptionsSpinner()
        binding.dobHolderContainer.setEndIconOnClickListener { showDatePicker() }
    }

    private fun fillOptionsSpinner() {
        binding.bodyType.optionsSpinner.attachDataSource(getArrayFromResources(R.array.body_type_options))
        binding.hairColor.optionsSpinner.attachDataSource(getArrayFromResources(R.array.hair_color_options))
        binding.eyeColor.optionsSpinner.attachDataSource(getArrayFromResources(R.array.eye_color_options))
        binding.skills.optionsSpinner.attachDataSource(getArrayFromResources(R.array.skills_options))
    }

    private fun getArrayFromResources(resourceId: Int): List<String> {
        return resources.getStringArray(resourceId).toList()
    }

    private fun showDatePicker() {
        val datePickerDialog = SlideDatePickerDialog.Builder()
            .setThemeColor(ResourcesCompat.getColor(resources, R.color.purple_700, null))
            .build()
        datePickerDialog.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    override fun onPositiveClick(day: Int, month: Int, year: Int, calendar: Calendar) {
        binding.dobHolder.setText(getString(R.string.date_pattern, day, month, year))
    }

    companion object {
        private const val DATE_PICKER_TAG = "date-picker"
    }
}