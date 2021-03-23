package com.finalproject.presentations.employee.claim.detaildocument

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.finalproject.databinding.FragmentOpenPdfBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.File


class OpenPdfFragment : Fragment() {

    private lateinit var binding : FragmentOpenPdfBinding
    private var uriFile : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            uriFile = this.getString("Document")
        }
        binding = FragmentOpenPdfBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(uriFile != null) {
            binding.pdfView.apply {
                fromUri(uriFile?.toUri()).enableSwipe(true).swipeHorizontal(true).load()
            }
        } else {
            binding.warningFile.visibility = View.VISIBLE
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = OpenPdfFragment()
    }
}