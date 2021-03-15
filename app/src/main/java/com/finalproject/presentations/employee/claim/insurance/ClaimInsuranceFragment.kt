package com.finalproject.presentations.employee.claim.insurance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.R


class ClaimInsuranceFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_claim_insurance, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ClaimInsuranceFragment()

    }
}