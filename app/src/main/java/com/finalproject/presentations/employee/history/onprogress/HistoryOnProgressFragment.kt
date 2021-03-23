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
import com.finalproject.data.models.reimburse.ReimburseListByDateCategory
import com.finalproject.data.models.reimburse.ReimburseListByDateRequest
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.FragmentHistoryOnprogressBinding
import com.finalproject.presentations.employee.history.HistoryViewAdapter
import com.finalproject.presentations.employee.history.HistoryViewModel
import com.finalproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryOnProgressFragment : Fragment() {

    private lateinit var binding: FragmentHistoryOnprogressBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyViewAdapter: HistoryViewAdapter
    private lateinit var loadingDialog: AlertDialog
    private var categoryId = ""

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog.build(requireContext())
        binding = FragmentHistoryOnprogressBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        historyViewAdapter = HistoryViewAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_historyOnProgressFragment_to_homeEmployeeFragment2)
        }
        onSpinnerFilterBy()
        onRadioButtonFilterCategory()
        binding.apply {
            etDateStart.isEnabled = false
            etDateEndDate.isEnabled = false
            dateIconStart.setOnClickListener {
                DateUtils.show(requireContext()) {
                    etDateStart.setText(it)
                }
            }
            dateIconEnd.setOnClickListener {
                DateUtils.show(requireContext()) {
                    etDateEndDate.setText(it)
                }
            }
            tvMoveFragmentHistorySuccess.setOnClickListener {
                findNavController().navigate(R.id.action_historyOnProgressFragment_to_historySuccessFragment2)
            }
            btnFilterCategory.setOnClickListener {
                if(categoryId.isBlank()) {
                    Toast.makeText(requireContext(), "Anda belum memilih kategori", Toast.LENGTH_SHORT).show()
                } else {
                    val request = ReimbursementListRequest(employeeId = getEmployeeId(), categoryId = categoryId)
                    viewModel.getAllHistoryProgressByCategory(request)
                }
            }
            btnFilterDate.setOnClickListener {
                val startDate = etDateStart.editableText.toString()
                val endDate = etDateEndDate.editableText.toString()
                if (startDate.isBlank() && endDate.isBlank()) {
                    Toast.makeText(requireContext(), "Dua Field Belum Anda Pilih", Toast.LENGTH_SHORT).show()
                } else if (endDate.isBlank()) {
                    Toast.makeText(requireContext(), "Anda Belum memasukkan data untuk tanggal akhir", Toast.LENGTH_SHORT).show()
                } else if (startDate.isBlank()) {
                    Toast.makeText(requireContext(), "Anda belum memasukkan data untuk tanggal awal", Toast.LENGTH_SHORT).show()
                } else {
                    val request = ReimburseListByDateRequest(startDate = startDate, endDate = endDate, employeeId = getEmployeeId())
                    viewModel.getAllHistoryProgressByDate(request)
                }
            }
            btnFilterCategoryDate.setOnClickListener {
                val startDate = etDateStart.editableText.toString()
                val endDate = etDateEndDate.editableText.toString()
                if (startDate.isBlank() && endDate.isBlank()) {
                    Toast.makeText(requireContext(), "Dua Field Belum Anda Pilih", Toast.LENGTH_SHORT).show()
                } else if (endDate.isBlank()) {
                    Toast.makeText(requireContext(), "Anda Belum memasukkan data untuk tanggal akhir", Toast.LENGTH_SHORT).show()
                } else if (startDate.isBlank()) {
                    Toast.makeText(requireContext(), "Anda belum memasukkan data untuk tanggal awal", Toast.LENGTH_SHORT).show()
                } else if(categoryId.isBlank()) {
                    Toast.makeText(requireContext(), "Anda belum memilih kategori", Toast.LENGTH_SHORT).show()
                }else {
                    val request =
                        ReimburseListByDateCategory(startDate = startDate, endDate = endDate, employeeId = getEmployeeId(), categoryId = categoryId)
                    viewModel.getAllHistoryProgressByDateCategory(request)
                }
            }
        }

        binding.rvOnProgressHistory.apply {
            adapter = historyViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.reimburseListLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.apply {
                        linearLayoutDataNotFound.visibility = View.VISIBLE
                        linearLayoutDataChooseFilter.visibility = View.GONE
                        linearLayoutRvProgress.visibility = View.GONE
                    }
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.apply {
                        linearLayoutDataNotFound.visibility = View.GONE
                        linearLayoutDataChooseFilter.visibility = View.GONE
                        linearLayoutRvProgress.visibility = View.VISIBLE
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
                    linearLayoutDataChooseFilter.visibility = View.VISIBLE
                    linearLayoutRvProgress.visibility = View.GONE
                    when (position) {
                        0 -> {
                            linearLayoutVerticalFilterCategory.visibility = View.VISIBLE
                            linearLayoutVerticalFilterDate.visibility = View.VISIBLE
                            tvTitleFilterCategory.visibility = View.VISIBLE
                            tvTitleFilterDate.visibility = View.VISIBLE
                            btnFilterCategory.visibility = View.GONE
                            btnFilterDate.visibility = View.GONE
                            btnFilterCategoryDate.visibility = View.VISIBLE
                        }
                        1 -> {
                            linearLayoutVerticalFilterDate.visibility = View.VISIBLE
                            linearLayoutVerticalFilterCategory.visibility = View.GONE
                            tvTitleFilterCategory.visibility = View.GONE
                            tvTitleFilterDate.visibility = View.VISIBLE
                            btnFilterCategory.visibility = View.GONE
                            btnFilterCategoryDate.visibility = View.GONE
                            btnFilterDate.visibility = View.VISIBLE
                        }
                        2 -> {
                            linearLayoutVerticalFilterCategory.visibility = View.VISIBLE
                            linearLayoutVerticalFilterDate.visibility = View.GONE
                            tvTitleFilterCategory.visibility = View.VISIBLE
                            tvTitleFilterDate.visibility = View.GONE
                            btnFilterCategory.visibility = View.VISIBLE
                            btnFilterDate.visibility = View.GONE
                            btnFilterCategoryDate.visibility = View.GONE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }
    }

    private fun onRadioButtonFilterCategory() {
        binding.apply {
            radioButtonGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    radioButtonKacamata.id -> categoryId = "1"
                    radioButtonPelatihan.id -> categoryId = "2"
                    radioButtonMelahirkan.id -> categoryId = "3"
                    radioButtonDinas.id -> categoryId = "4"
                    radioButtonAsuransi.id -> categoryId = "5"
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