package com.finalproject.utils

class AppConstant {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8081"
        const val STORAGE_READ_PERMISSION_CODE = 98;
        const val STORAGE_WRITE_PERMISSION_CODE = 95;
        const val ON_BOARDING_FINISHED = "OnBoardingFinished" //Untuk Shared Preferences
        const val ON_LOGIN_FINISHED = "OnLoginFinished" //Untuk Shared Preferences
        const val APP_SHARED_PREF = "ApplicationSharedPref"
        const val APP_ID_LOGIN = "ApplicationIdLogin"
        const val APP_ID_EMPLOYEE = "ApplicationIdEmployee"
        const val SEND_BUNDLE_DATA_EMPLOYEE = "BundleDataEmployee"
        val GENDER_ARRAYS = arrayListOf<String>("MALE", "FEMALE")
        val RELIGION_ARRAYS = arrayListOf<String>("ISLAM", "KRISTEN PROTESTAN", "KRISTEN KATOLIK", "BUDDHA", "HINDU", "KONG HU CU")
        //val GRADE_ARRAYS = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
        val MARITAL_STATUS_ARRAYS = arrayListOf<String>("MARRIED", "SINGLE", "DIVORCE")
        val BLOOD_TYPE_ARRAY = arrayListOf<String>("A", "B", "O", "AB")
    }
}