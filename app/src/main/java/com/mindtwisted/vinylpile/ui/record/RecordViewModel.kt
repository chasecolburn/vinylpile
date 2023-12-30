package com.mindtwisted.vinylpile.ui.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindtwisted.vinylpile.model.Record

class RecordViewModel : ViewModel() {

    private val _recordList = MutableLiveData<List<Record>>()

    val recordList: MutableLiveData<List<Record>> = _recordList
}