package com.kapait.faa.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kapait.faa.R
import com.kapait.faa.databinding.FragmentProfileBinding
import com.kapait.faa.models.User
import com.kapait.faa.ui.common.material_date_picker.SlideDatePickerDialog
import com.kapait.faa.ui.common.material_date_picker.callback.SlideDatePickerDialogCallback
import com.kapait.faa.ui.profile.adapter.CharacteristicOptionsAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileFragment: Fragment(), SlideDatePickerDialogCallback {

    private var profileBinding: FragmentProfileBinding? = null
    private var userLogined = true
    private lateinit var profileViewModel: ProfileViewModel
    private val binding
        get() = profileBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUser()
        initUI()
        profileViewModel.loadUserLiveData.observe(viewLifecycleOwner, ::userLoaded)
        profileViewModel.errorLiveData.observe(viewLifecycleOwner,::handleError)
    }

    private fun loadUser() {
        profileViewModel.loadProfile()
    }

    private fun handleError(unit: Unit?) {
        userLogined = false
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, ErrorFragment())
            .commit()
    }

    private fun userLoaded(user: User?) {
        user?.run {
            binding.fullNameEditText.setText(fullName)
            binding.emailEditText.setText(userEmail)
            binding.dobHolder.setText(dob)
            binding.country.optionsSpinner.selectedItem
            binding.height.optionsSpinner.selectedIndex = height
            binding.weight.optionsSpinner.selectedIndex = weight
            binding.bodyType.optionsSpinner.selectedIndex = bodyType
            binding.hairColor.optionsSpinner.selectedIndex = hairColor
            binding.eyeColor.optionsSpinner.selectedIndex = eyeColor
        }
    }

    private fun initUI() {
        binding.country.characteristicName.text = getString(R.string.country)
        binding.height.characteristicName.text = getString(R.string.height)
        binding.weight.characteristicName.text = getString(R.string.weight)
        binding.bodyType.characteristicName.text = getString(R.string.body_type)
        binding.hairColor.characteristicName.text = getString(R.string.hair_color)
        binding.eyeColor.characteristicName.text = getString(R.string.eye_color)
        binding.skills.characteristicsName.text = getString(R.string.skills)
        binding.dobHolderContainer.setEndIconOnClickListener { showDatePicker() }
        binding.userAvatar.setOnClickListener { setUserAvatar() }
        fillOptionsSpinner()
        initRecyclerView()
    }

    private fun setUserAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = data?.data
            binding.userAvatar.apply {
                setImageURI(imageUri)
                clipToOutline = true
            }
        }
    }

    private fun initRecyclerView() {
        val clickListener: (String) -> Unit = { profileViewModel.addSelectedSkill(it) }
        val skillsAdapter = CharacteristicOptionsAdapter(clickListener).apply {
            setSkills(getArrayFromResources(R.array.skills_options))
            setSelectedSkills(profileViewModel.getSelectedSkills())
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.skills.characteristicOptions.apply {
            adapter = skillsAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun fillOptionsSpinner() {
        binding.bodyType.optionsSpinner.attachDataSource(getArrayFromResources(R.array.body_type_options))
        binding.hairColor.optionsSpinner.attachDataSource(getArrayFromResources(R.array.hair_color_options))
        binding.eyeColor.optionsSpinner.attachDataSource(getArrayFromResources(R.array.eye_color_options))
    }

    private fun getArrayFromResources(resourceId: Int): List<String> {
        return resources.getStringArray(resourceId).toList()
    }

    private fun showDatePicker() {
        val datePickerDialog = SlideDatePickerDialog.Builder()
            .setThemeColor(ResourcesCompat.getColor(resources, R.color.purple_700, null))
            .build()
        datePickerDialog.show(childFragmentManager, DATE_PICKER_TAG)
    }

    override fun onPositiveClick(day: Int, month: Int, year: Int, calendar: Calendar) {
        binding.dobHolder.setText(SimpleDateFormat(DOB_TEXT_PATTERN, Locale.getDefault()).format(calendar.time))
    }

    override fun onDestroyView() {
        saveUser()
        super.onDestroyView()
    }

    private fun saveUser() {
        if (userLogined)
            profileViewModel.updateUserInfo(collectUserInfo())
    }

    private fun collectUserInfo(): User {
        val user = User()
        user.apply {
            fullName = binding.fullNameEditText.text.toString()
            userEmail = binding.emailEditText.text.toString()
            dob = binding.dobHolder.text.toString()
            country = binding.country.optionsSpinner.selectedIndex
            height = binding.height.optionsSpinner.selectedIndex
            weight = binding.weight.optionsSpinner.selectedIndex
            bodyType = binding.bodyType.optionsSpinner.selectedIndex
            hairColor = binding.hairColor.optionsSpinner.selectedIndex
            eyeColor = binding.eyeColor.optionsSpinner.selectedIndex
        }
        return user
    }

    companion object {
        private const val DATE_PICKER_TAG = "date-picker"
        private const val DOB_TEXT_PATTERN = "dd/MM/yyyy"
    }
}