package com.example.challenge.presentation.ui.splash

sealed class SplashUiEvent {
    data object NavigateToLogIn : SplashUiEvent()
    data object NavigateToConnections : SplashUiEvent()
}