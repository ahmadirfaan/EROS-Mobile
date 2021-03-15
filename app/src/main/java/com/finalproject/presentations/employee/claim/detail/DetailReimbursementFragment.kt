package com.finalproject.presentations.employee.claim.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.R
import com.finalproject.databinding.FragmentDetailReimbursementBinding


class DetailReimbursementFragment : Fragment() {

    private lateinit var binding : FragmentDetailReimbursementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_reimbursement, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailReimbursementFragment()
    }
}