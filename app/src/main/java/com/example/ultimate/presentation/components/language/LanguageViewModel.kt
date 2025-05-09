package com.example.ultimate.presentation.components.language

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import java.util.Locale


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val pref: SharedPreferences
) : ViewModel() {

    // Holds the selected language (default to stored value or "en")
    private val _selectedLanguage = mutableStateOf(
        pref.getSharedPreferences(Constants.LANG, Constants.LANG_AR)
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
    fun applyLanguage(resources: Resources,onSuccess: () -> Unit) {
        pref.setSharedPreferences(Constants.LANG, _selectedLanguage.value)
        _isDialogVisible.value = false
        // Optional: trigger app-wide language change or refresh here
        if(_selectedLanguage.value == Constants.LANG_AR){
            setLocale(Constants.LANGUAGE_AR_CODE,resources)
        }else{
            setLocale(Constants.LANGUAGE_EN_CODE,resources)
        }
        onSuccess()
    }

    private fun setLocale(languageCode: String, resources: Resources){
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
