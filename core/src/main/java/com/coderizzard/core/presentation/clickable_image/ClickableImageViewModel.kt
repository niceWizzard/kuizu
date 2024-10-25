package com.coderizzard.core.presentation.clickable_image

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ClickableImageViewModel @Inject constructor(

) : ViewModel() {
    private val _isModalShown = MutableStateFlow(false)
    val isModalShown = _isModalShown.asStateFlow()
    fun showModal() {
        _isModalShown.update { true}
    }
    fun hideModal() {
        _isModalShown.update { false}
    }
}

