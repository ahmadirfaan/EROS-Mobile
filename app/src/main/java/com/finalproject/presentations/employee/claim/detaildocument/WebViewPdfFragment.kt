package com.finalproject.presentations.employee.claim.detaildocument

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.finalproject.R
import com.finalproject.databinding.FragmentWebViewPdfBinding
import com.finalproject.utils.AppConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL

class WebViewPdfFragment : Fragment() {

    private lateinit var binding : FragmentWebViewPdfBinding
    private var urlPdf :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWebViewPdfBinding.inflate(layoutInflater)
        arguments?.apply {
            urlPdf = this.getString("StringURLPDF")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            val input : InputStream
            input = URL("${AppConstant.BASE_URL}/bill/files/$urlPdf.pdf").openStream()
            binding.webViewPdf.fromStream(input)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load()
        }
    }



//    @SuppressLint("SetJavaScriptEnabled")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val urlSeePdf = "${AppConstant.BASE_URL}/bill/files/$urlPdf.pdf"
//        Log.d("URL SEE PDF", urlSeePdf)
//        binding.apply {
//            progressBar.visibility = View.VISIBLE
//            webViewPdf.apply {
//                settings.apply {
//                    javaScriptEnabled = true
//                    builtInZoomControls = true
//                    displayZoomControls = true
//                    loadWithOverviewMode = true
//                }
//                webViewClient = object : WebViewClient() {
//                    override fun onPageFinished(view: WebView?, url: String?) {
//                        super.onPageFinished(view, url)
////                        webViewPdf.loadUrl("javascript:(function() { " +
////                                "document.querySelector('[role=\"toolbar\"]').remove();})()");
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }
//                loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$urlSeePdf")
//            }
//        }
//    }

    companion object {

        @JvmStatic
        fun newInstance() = WebViewPdfFragment()
    }
}