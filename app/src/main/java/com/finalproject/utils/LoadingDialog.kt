package com.finalproject.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.finalproject.R

class LoadingDialog {

    companion object {
        fun build(context: Context): AlertDialog {
            val inflate = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null)
            val dialog = AlertDialog.Builder(context).setView(inflate).setCancelable(false).create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            return dialog
        }
    }
}