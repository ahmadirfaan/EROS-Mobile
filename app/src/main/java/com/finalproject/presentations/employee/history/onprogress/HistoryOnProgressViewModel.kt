package com.finalproject.presentations.employee.history.onprogress

import androidx.lifecycle.ViewModel
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import com.finalproject.di.qualifier.ReimburseRepoQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryOnProgressViewModel
    @Inject
    constructor(
        @ReimburseRepoQualifier
        private val reimbursementRepository: ReimbursementRepository
    ) : ViewModel() {



}