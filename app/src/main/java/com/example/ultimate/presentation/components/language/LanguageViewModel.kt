package com.example.ultimate.presentation.components.language

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val pref: SharedPreferences
) : ViewModel() {

    // Holds the selected language (default to stored value or "en")
    private val _selectedLanguage = mutableStateOf(
        pref.getSharedPreferences(Constants.LANG, "1")
    )
    val selectedLanguage: State<String> = _selectedLanguage

    // Dialog visibility state
    private val _isDialogVisible = mutableStateOf(false)
    val isDialogVisible: State<Boolean> = _isDialogVisible

    // Show or hide the dialog
    fun showDialog(show: Boolean) {
        _isDialogVisible.value = show
    }

    // Set a temporary selection before applying
    fun selectLanguage(languageCode: String) {
        _selectedLanguage.value = languageCode
    }

    // Apply the language: save to preferences, refresh app, close dialog
    fun applyLanguage(onSuccess: () -> Unit) {
        pref.setSharedPreferences(Constants.LANG, _selectedLanguage.value)
        _isDialogVisible.value = false
        // Optional: trigger app-wide language change or refresh here
        onSuccess()
    }
}
