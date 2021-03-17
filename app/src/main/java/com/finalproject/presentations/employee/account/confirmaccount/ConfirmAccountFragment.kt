package com.finalproject.presentations.employee.account.confirmaccount

import android.content.SharedPreferences
import android.os.Bundle
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
import com.finalproject.databinding.FragmentConfirmAccountBinding
import com.finalproject.utils.AppConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmAccountFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: FragmentConfirmAccountBinding
    private lateinit var viewModel : ConfirmAccountViewModel
    private lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog.build(requireContext())
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmAccountBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
        clearSharedPreferences()
        binding.btnGoEditForm.setOnClickListener {
            idLoginSharedPreferences()?.let {
                viewModel.sendBundleDataForEditAccount(it)
            }
        }
        return binding.root
    }





    override fun onDestroy() {
        super.onDestroy()
        viewModel.sendBundleData.removeObservers(this)
        loadingDialog.cancel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ConfirmAccountViewModel::class.java)
    }

    private fun subscribe() {
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
                    findNavController().navigate(R.id.action_confirmAccountFragment_to_formProfileEmployeeFragment2, bundle)
                }
            }
        })
    }

    private fun clearSharedPreferences() {
        sharedPreferences.edit().remove(AppConstant.ON_LOGIN_FINISHED).apply()
//        sharedPreferences.edit().remove(AppConstant.APP_ID_LOGIN).apply()
//        sharedPreferences.edit().remove(AppConstant.APP_ID_EMPLOYEE).apply()
    }

    private fun idLoginSharedPreferences() : String? {
        return sharedPreferences.getString(AppConstant.APP_ID_LOGIN, "ID LOGIN")
    }



    companion object {

        @JvmStatic
        fun newInstance() = ConfirmAccountFragment()
    }
}