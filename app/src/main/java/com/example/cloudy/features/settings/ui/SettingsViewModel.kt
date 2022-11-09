package com.example.cloudy.features.settings.ui

import androidx.lifecycle.*
import com.example.cloudy.features.settings.data.datastore.PreferencesManager
import com.example.cloudy.features.settings.data.datastore.TempUnitSelection
import com.example.cloudy.features.settings.data.datastore.ThemeSelection
import com.zhuinden.flowcombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceManager: PreferencesManager,
    savedStateHandle: SavedStateHandle
) : ViewModel(), SettingsScreenActions {

    private val appPreferences = preferenceManager.appPreferences
    private val showThemeDialog =
        savedStateHandle.getLiveData("showThemeDialog", false)
    private val showTempUnitDialog =
        savedStateHandle.getLiveData("showTempUnitDialog", false)

    var uiState = combineTuple(
        appPreferences,
        showThemeDialog.asFlow(),
        showTempUnitDialog.asFlow()
    ).map { (appPreferences, showThemeDialog, showTempUnitDialog) ->
        SettingsScreenState(
            appPreferences = appPreferences,
            showThemeDialog = showThemeDialog,
            showTempUnitDialog = showTempUnitDialog
        )

    }.asLiveData()

    override fun onThemePreferenceClicked() {
        showThemeDialog.value = true
    }

    override fun onTempUnitPreferenceClicked() {
        showTempUnitDialog.value = true
    }

    override fun onThemeUpdated(theme: ThemeSelection) {
        showThemeDialog.value = false
        viewModelScope.launch {
            preferenceManager.updateSelectedTheme(theme)
        }
    }

    override fun onTempUnitUpdated(tempUnit: TempUnitSelection) {
        showTempUnitDialog.value = false
        viewModelScope.launch {
            preferenceManager.updatedSelectedTempUnit(tempUnit)
        }
    }

    override fun onThemeDialogDismissed() {
        showThemeDialog.value = false
    }

    override fun onTempUnitDialogDismissed() {
        showTempUnitDialog.value = false
    }
}