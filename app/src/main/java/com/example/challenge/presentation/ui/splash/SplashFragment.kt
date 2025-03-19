package com.example.challenge.presentation.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.databinding.FragmentSplashBinding
import com.example.challenge.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()

    override fun start() {

    }

    override fun listeners() {

    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: SplashUiEvent) {
        when (event) {
            is SplashUiEvent.NavigateToConnections -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToConnectionsFragment()
            )

            is SplashUiEvent.NavigateToLogIn -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToLogInFragment()
            )
        }
    }
}
