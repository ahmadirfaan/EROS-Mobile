package com.finalproject.presentations.employee.history.onprogress

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.R
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.FragmentHistoryOnprogressBinding
import com.finalproject.presentations.employee.history.HistoryViewAdapter
import com.finalproject.utils.AppConstant
import com.finalproject.utils.HistoryConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryOnProgressFragment : Fragment() {

    private lateinit var binding: FragmentHistoryOnprogressBinding
    private lateinit var viewModel: HistoryOnProgressViewModel
    private lateinit var historyViewAdapter: HistoryViewAdapter
    private lateinit var loadingDialog: AlertDialog
    private var categoryId = ""

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog.build(requireContext())
        initViewModel()
        subscribe()
        historyViewAdapter = HistoryViewAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryOnprogressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_historyOnProgressFragment_to_homeEmployeeFragment2)
        }
        binding.tvMoveFragmentHistorySuccess.setOnClickListener {
            findNavController().navigate(R.id.action_historyOnProgressFragment_to_historySuccessFragment2)
        }
        onSpinnerFilterBy()
        onSpinnerFilterCategory()
        binding.apply {
            btnFilterCategory.setOnClickListener {
                val request = ReimbursementListRequest(employeeId = getEmployeeId(), categoryId = categoryId)
                viewModel.getAllHistoryProgress(request)
            }
        }

        binding.rvOnProgressHistory.apply {
            adapter = historyViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HistoryOnProgressViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.reimburseListLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.apply {
                        tvNotAvalaible.text = "Data Tidak Ditemukan"
                        tvNotAvalaible.visibility = View.VISIBLE
                        rvOnProgressHistory.visibility = View.GONE
                    }
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.apply {
                        tvNotAvalaible.visibility = View.GONE
                        rvOnProgressHistory.visibility = View.VISIBLE
                    }
                    val listHistory = it.data as List<ReimbursementResponse>
                    historyViewAdapter.setHistoryList(listHistory)
                }
            }
        })
        viewModel.onDetailReimburseLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val data = it?.data as ReimbursementResponse
                    val bundle = bundleOf(HistoryConstant.SEND_BUNDLE_DATA_REIMBURSEMENT to data)
                    findNavController().navigate(R.id.action_historyOnProgressFragment_to_detailReimbursementFragment, bundle)
                }
            }
        })
    }

    private fun getEmployeeId(): String? {
        return sharedPreferences.getString(AppConstant.APP_ID_EMPLOYEE, "ID Employee")
    }

    private fun onSpinnerFilterBy() {
        binding.apply {
            val adapterFilter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, HistoryConstant.arrayFilter)
            spinnerFilter.adapter = adapterFilter
            spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    tvTitleFilterBy.text = HistoryConstant.arrayFilter[position]
                    when (position) {
                        0 -> {
                            linearLayoutVerticalFilterCategory.visibility = View.GONE
                            linearLayoutVerticalFilterDate.visibility = View.GONE
                            tvNotAvalaible.text = "Silahkan Pilih Filter Untuk Melihat Data"
                            tvNotAvalaible.visibility = View.VISIBLE
                            binding.rvOnProgressHistory.visibility = View.GONE
                        }
                        1 -> {
                            linearLayoutVerticalFilterDate.visibility = View.VISIBLE
                            linearLayoutVerticalFilterCategory.visibility = View.GONE
                        }
                        2 -> {
                            linearLayoutVerticalFilterCategory.visibility = View.VISIBLE
                            linearLayoutVerticalFilterDate.visibility = View.GONE

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }
    }

    private fun onSpinnerFilterCategory() {
        binding.apply {
            val adapterCategory = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, HistoryConstant.arrayCategory)
            spinnerKategori.adapter = adapterCategory
            spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    binding.tvSortingKategori.text = HistoryConstant.arrayCategory[position]
                    var positionCategory = position + 1
                    categoryId = positionCategory.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        loadingDialog.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryOnProgressFragment()
    }
}