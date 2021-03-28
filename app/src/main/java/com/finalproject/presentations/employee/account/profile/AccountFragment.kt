package com.finalproject.presentations.employee.account.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.account.EmployeeResponse
import com.finalproject.databinding.FragmentAccountBinding
import com.finalproject.utils.AppConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var loadingDialog: AlertDialog
    private lateinit var viewModel: AccountFragmentViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var idLogin : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
        idLogin = sharedPreferences.getString(AppConstant.APP_ID_LOGIN, "Tidak Tersedia")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog.build(requireContext())
        idLogin?.let {
            viewModel.fillProfileAccount(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_accountFragment_to_homeEmployeeFragment2)
        }
        binding.apply {
            btnEditAccount.setOnClickListener {
                idLogin?.let {
                    viewModel.sendBundleDataForEditAccount(it)
                }
            }
            btnLogOutAccount.setOnClickListener {
                clearSharedPreferencesWhenLogOut()
                findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
            }
            btnChangePassword.setOnClickListener {
                findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
            }
        }


    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AccountFragmentViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.setProfile.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val profileAccount = it?.data as EmployeeResponse?
                    Log.d("Employe Response", profileAccount.toString())
                    settingProfile(profileAccount)
                }
            }
        })
        viewModel.sendBundleData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.SUCCESS -> {
                    val data = it.data as EmployeeResponse
                    val bundle = bundleOf(AppConstant.SEND_BUNDLE_DATA_EMPLOYEE to data)
                    findNavController().navigate(R.id.action_accountFragment_to_formProfileEmployeeFragment, bundle)
                }
            }
        })
    }

    private fun clearSharedPreferencesWhenLogOut() {
        sharedPreferences.edit().remove(AppConstant.ON_LOGIN_FINISHED).apply()
        sharedPreferences.edit().remove(AppConstant.APP_ID_LOGIN).apply()
        sharedPreferences.edit().remove(AppConstant.APP_ID_EMPLOYEE).apply()
    }

    private fun settingProfile(employee: EmployeeResponse?) {
        binding.apply {
            employee?.apply {
                if(employee.gender.equals("MALE")) {
                    accountPhoto.setImageResource(R.drawable.male)
                    tvGenderAccount.text = "Pria"
                } else {
                    accountPhoto.setImageResource(R.drawable.female)
                    tvGenderAccount.text = "Wanita"
                }
                tvFullnameAccount.text = employee.fullname
                tvAddressKtpAccount.text = employee.ktpAddress
                tvBiologicalMothersAccount.text = employee.biologicalMothersName
                tvPlaceBirthAccount.text = employee.placeOfBirth
                tvDateBirthAccount.text = employee.dateOfBirth
                tvReligionAccount.text = employee.religion
                tvAddressKtpAccount.text = employee.ktpAddress?.let { subStringAddress(it) }
                tvPostalCodeAccount.text = employee.postalCodeOfIdCard
                tvNpwpAddressAccount.text = employee.npwpAddress?.let { subStringAddress(it) }
                tvDomicileAccount.text = employee.residenceAddress?.let { subStringAddress(it) }
                tvBloodTypeAccount.text = employee.bloodType
                tvNamaRekeningAccount.text = employee.accountName
                tvNoRekeningAccount.text = employee.accountNumber
                tvNikKtpAccount.text = employee.nik
                tvNpwpAccount.text = employee.npwp
                tvNumberPhoneAccount.text = employee.phoneNumber
                tvEmergencyContactAccount.text = employee.emergencyNumber
                if (employee.maritalStatus.equals("SINGLE") || employee.maritalStatus == null) {
                    linearLayoutSpouseNameAccount.visibility = View.GONE
                    linearLayoutNumberChildrenAccount.visibility = View.GONE
                    tvMaritalStatusAccount.text = "Lajang"
                } else if (employee.maritalStatus.equals("DIVORCED")) {
                    linearLayoutSpouseNameAccount.visibility = View.GONE
                    tvMaritalStatusAccount.text = "Cerai"
                    if(employee.numberOfChildren != null) {
                        tvNumberChildrenAccount.text = employee.numberOfChildren as String
                    } else {
                        tvNumberChildrenAccount.text = employee.numberOfChildren
                    }
                } else {
                    tvMaritalStatusAccount.text = "Menikah"
                    if(employee.spouseName != null) {
                        tvSpouseNameAccount.text =  employee.spouseName as String
                    } else {
                        tvNumberChildrenAccount.text = employee.spouseName
                    }
                    if(employee.numberOfChildren != null) {
                        tvNumberChildrenAccount.text = employee.numberOfChildren.toString()
                    } else {
                        tvNumberChildrenAccount.text = employee.numberOfChildren
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.cancel()
    }

    private fun subStringAddress(address : String) : String {
//        if(address.length > 35) {
//            return "${address.substring(0, 20)}\n${address.substring(20, 35)}\n${address.substring(35, address.length)}"
//        } else if(address.length > 20) {
//            return "${address.substring(0, 18)}\n${address.substring(18, address.length)}"
//        } else if(address.length > 15) {
//            return "${address.substring(0, 10)}\n${address.substring(10, address.length)}"
//        } else {
            return address
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}