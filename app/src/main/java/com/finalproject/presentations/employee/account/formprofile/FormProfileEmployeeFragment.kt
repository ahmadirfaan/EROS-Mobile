package com.finalproject.presentations.employee.account.formprofile

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.account.EmployeeResponse
import com.finalproject.data.models.account.FormAccountRequest
import com.finalproject.databinding.FragmentFormProfileEmployeeBinding
import com.finalproject.utils.AppConstant
import com.finalproject.utils.DateUtils
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormProfileEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentFormProfileEmployeeBinding
    private lateinit var viewModel: FormProfileEmployeeViewModel
    private lateinit var loadingDialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    //Variable request Form Account
    private var fullname = ""
    private var mothersname = ""
    private var placebirth = ""
    private var datebirth = ""
    private var gender = ""
    private var maritalStatus = ""
    private var spouseName = ""
    private var numberChildren = ""
    private var religion = ""
    private var nomerKtp = ""
    private var ktpAddress = ""
    private var postalCode = ""
    private var nomerNpwp = ""
    private var npwpAddress = ""
    private var residence = ""
    private var bloodType = ""
    private var accountName = ""
    private var accountNumber = ""
    private var numberPhone = ""
    private var numberEmergency = ""

    //For Enum
    private var genderInt: Int? = null
    private var maritalStatusInt: Int? = null
    private var religionInt: Int? = null
    private var bloodTypeInt: Int? = null


    //Data Employee
    private var dataUpdate: EmployeeResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            dataUpdate = this.getParcelable(AppConstant.SEND_BUNDLE_DATA_EMPLOYEE)
        }
        binding = FragmentFormProfileEmployeeBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        loadingDialog = LoadingDialog.build(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onSpinnerItemBloodType()
        onSpinnerItemMaritalStatus()
        onSpinnerItemGender()
        onSpinnerItemReligion()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateInputOnRunTime()
        if (dataUpdate != null) {
            setTextIfDataNotNull()
        }
        binding.apply {
            etDateBirthName.setOnClickListener {
                DateUtils.show(requireContext()) {
                    etDateBirthName.setText(it)
                }
            }
            dateIcon.setOnClickListener {
                DateUtils.show(requireContext()) {
                    etDateBirthName.setText(it)
                }
            }
            btnSubmitForm.setOnClickListener {
                fillVariableWithTheInput()
                if (nomerKtp.length == 16) {
                    viewModel.inputValidation(
                        fullname, mothersname, placebirth, datebirth,
                        gender, maritalStatus, spouseName, numberChildren,
                        religion, nomerKtp, ktpAddress, postalCode,
                        nomerNpwp, npwpAddress, residence, bloodType,
                        accountName, accountNumber, numberPhone, numberEmergency
                    )
                } else {
                    Toast.makeText(requireContext(), "Masukkan Nomer KTP dengan Benar", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.inputValidation.removeObservers(this)
        loadingDialog.cancel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(FormProfileEmployeeViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.inputValidation.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val formRequest = FormAccountRequest(
                        fullname = fullname, biologicalMothersName = mothersname, placeOfBirth = placebirth, dateOfBirth = datebirth,
                        gender = genderInt, maritalStatus = maritalStatusInt, spouseName = spouseName, numberOfChildren = numberChildren.toInt(),
                        religion = religionInt, nik = nomerKtp, ktpAddress = ktpAddress, postalCodeOfIdCard = postalCode,
                        npwp = nomerNpwp, npwpAddress = npwpAddress, residenceAddress = residence, bloodType = bloodTypeInt,
                        accountName = accountName, accountNumber = accountNumber, phoneNumber = numberPhone, emergencyNumber = numberEmergency
                    )
                    Log.d("Masuk Subscribe", "Start Subscribe Success")
                    Log.d("Form Request : ", "$formRequest")
//                    getIdEmployee()?.let { it1 ->
//                        Log.d("ID Employee", it1)
//                        viewModel.fillFormProfile(
//                            it1,
//                            request = formRequest
//                        )
//                    }
                    getIdEmployee()?.let { it1 ->
                        viewModel.fillEditFormProfileForFirstTime(
                            idEmployee = it1, request = formRequest
                        )
                    }
                    Log.d("Masuk Subscribe", "End Subscribe Success")

                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.formLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    if(dataUpdate != null) {
                        findNavController().popBackStack()
                    } else {
                        findNavController().navigate(R.id.action_formProfileEmployeeFragment_to_confirmAccountFragment)
                    }
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setTextIfDataNotNull() {
        val lengthNomerHp = dataUpdate?.phoneNumber?.length
        val lengthNomerEmergency = dataUpdate?.emergencyNumber?.length
        binding.apply {
            etFullName.setText(dataUpdate?.fullname)
            etMothersName.setText(dataUpdate?.biologicalMothersName)
            etPlaceBirthName.setText(dataUpdate?.placeOfBirth)
            etDateBirthName.setText(dataUpdate?.dateOfBirth)
            etNameCouple.setText(dataUpdate?.spouseName)
            etNumberChildren.setText(dataUpdate?.numberOfChildren.toString())
            etNomerKtp.setText(dataUpdate?.nik)
            etAddress.setText(dataUpdate?.ktpAddress)
            etPostalCode.setText(dataUpdate?.postalCodeOfIdCard)
            etNomerNpwp.setText(dataUpdate?.npwp)
            etAddressNpwp.setText(dataUpdate?.npwpAddress)
            etDomicile.setText(dataUpdate?.residenceAddress)
            etNamaRekening.setText(dataUpdate?.accountName)
            etNomerRekening.setText(dataUpdate?.accountNumber)
            etNomerHp.setText(lengthNomerHp?.let { dataUpdate?.phoneNumber?.substring(3, it) })
            etNomerEmergency.setText(lengthNomerEmergency?.let { dataUpdate?.emergencyNumber?.substring(3, it) })
            tvTitleForm.setText("Change Data Employee")
            btnSubmitForm.text = "Edit Data"
            //Logic For Enum
            when(dataUpdate?.gender) {
                "MALE" -> spinnerGender.setSelection(0)
                "FEMALE" -> spinnerGender.setSelection(1)
            }
            when(dataUpdate?.maritalStatus) {
                "MARRIED" -> spinnerMaritalStatus.setSelection(0)
                "SINGLE" -> spinnerMaritalStatus.setSelection(1)
                "DIVORCE" -> spinnerMaritalStatus.setSelection(2)
            }
            when(dataUpdate?.religion) {
                "ISLAM" -> spinnerReligion.setSelection(0)
                "PROTESTAN"-> spinnerReligion.setSelection(1)
                "KATOLIK" -> spinnerReligion.setSelection(2)
                "BUDDHA" -> spinnerReligion.setSelection(3)
                "HINDU" -> spinnerReligion.setSelection(4)
                "KONGHUCU" -> spinnerReligion.setSelection(5)
            }
            when(dataUpdate?.bloodType) {
                "A" -> spinnerBloodType.setSelection(0)
                "B" -> spinnerBloodType.setSelection(1)
                "O" -> spinnerBloodType.setSelection(2)
                "AB" -> spinnerBloodType.setSelection(3)
            }

        }
    }

    private fun fillVariableWithTheInput() {
        binding.apply {
            fullname = etFullName.editableText.toString()
            mothersname = etMothersName.editableText.toString()
            placebirth = etPlaceBirthName.editableText.toString()
            datebirth = etDateBirthName.editableText.toString()
            gender = etInputGender.editableText.toString()
            maritalStatus = etInputMaritalStatus.editableText.toString()
            spouseName = etNameCouple.editableText.toString()
            numberChildren = etNumberChildren.editableText.toString()
            religion = etInputReligion.editableText.toString()
            nomerKtp = etNomerKtp.editableText.toString()
            ktpAddress = etAddress.editableText.toString()
            postalCode = etPostalCode.editableText.toString()
            nomerNpwp = etNomerNpwp.editableText.toString()
            npwpAddress = etAddressNpwp.editableText.toString()
            residence = etDomicile.editableText.toString()
            bloodType = etInputBloodType.editableText.toString()
            accountName = etNamaRekening.editableText.toString()
            accountNumber = etNomerRekening.editableText.toString()
            numberPhone = "+62"+etNomerHp.editableText.toString()
            numberEmergency = "+62"+etNomerEmergency.editableText.toString()
        }
    }


    private fun onSpinnerItemBloodType() {
        binding.apply {
            val adapterBlood = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, AppConstant.BLOOD_TYPE_ARRAY)
            spinnerBloodType.adapter = adapterBlood
            etInputBloodType.isEnabled = false
            spinnerBloodType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    etInputBloodType.setText(AppConstant.BLOOD_TYPE_ARRAY[position])
                    bloodTypeInt = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }
    }

    private fun validateInputOnRunTime() {
        binding.apply {
            tilInputNomerKtp.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().length < 16) {
                        binding.tilInputNomerKtp.editText?.error = "Harap Masukkan Format KTP dengan Benar"
                    }
                }
            })
        }
    }

    private fun onSpinnerItemMaritalStatus() {
        binding.apply {
            val adapterMaritalStatus =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, AppConstant.MARITAL_STATUS_ARRAYS)
            spinnerMaritalStatus.adapter = adapterMaritalStatus
            etInputMaritalStatus.isEnabled = false
            spinnerMaritalStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    etInputMaritalStatus.setText(AppConstant.MARITAL_STATUS_ARRAYS[position])
                    maritalStatusInt = position
                    when (position) {
                        0 -> {
                            tvNameCouple.visibility = View.VISIBLE
                            tilInputNameCouple.visibility = View.VISIBLE
                            tvNumberChildren.visibility = View.VISIBLE
                            tilInputNumberChildren.visibility = View.VISIBLE
                        }
                        1 -> {
                            tvNameCouple.visibility = View.GONE
                            tilInputNameCouple.visibility = View.GONE
                            tilInputNameCouple.editText?.setText("Tidak Ada") //Untuk mengset nilai dari namaa couple
                            tilInputNumberChildren.editText?.setText("0") //Untuk mengset nilai dari number children
                            tvNumberChildren.visibility = View.GONE
                            tilInputNumberChildren.visibility = View.GONE
                        }
                        2 -> {
                            tvNameCouple.visibility = View.GONE
                            tilInputNameCouple.visibility = View.GONE
                            tilInputNameCouple.editText?.setText("Tidak Ada")
                            tvNumberChildren.visibility = View.VISIBLE
                            tilInputNumberChildren.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        }
    }

    private fun onSpinnerItemGender() {
        binding.apply {
            val adapterGender =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, AppConstant.GENDER_ARRAYS)
            spinnerGender.adapter = adapterGender
            etInputGender.isEnabled = false
            spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    genderInt = position
                    etInputGender.setText(AppConstant.GENDER_ARRAYS[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }
    }

    private fun onSpinnerItemReligion() {
        binding.apply {
            val adapterReligion =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, AppConstant.RELIGION_ARRAYS)
            spinnerReligion.adapter = adapterReligion
            etInputReligion.isEnabled = false
            spinnerReligion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    religionInt = position
                    etInputReligion.setText(AppConstant.RELIGION_ARRAYS[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }
    }

    private fun getIdEmployee(): String? {
        return sharedPreferences.getString(AppConstant.APP_ID_EMPLOYEE, "ID EMPLOYEE")
    }

    companion object {

        @JvmStatic
        fun newInstance() = FormProfileEmployeeFragment()
    }
}