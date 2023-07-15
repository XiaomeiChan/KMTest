package com.example.kmtest.ui.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondViewModel: ViewModel() {
    private val _selected: MutableLiveData<String> = MutableLiveData()
    val selected: LiveData<String> = _selected

    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name

    fun setSelected(selectedValue: String) {
        _selected.value = selectedValue
    }

    fun setName(nameValue: String) {
        _name.value = nameValue
    }
}