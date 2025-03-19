package com.example.challenge.presentation.ui.log_in

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.BaseFragment
import com.example.challenge.databinding.FragmentLogInBinding
import com.example.challenge.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun start() {

    }

    override fun listeners() {
        binding.btnLogIn.setOnClickListener {
            logIn()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collect {
                    handleLogInState(logInState = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun logIn() {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleLogInState(logInState: LogInState) {
        binding.loaderInclude.loaderContainer.isVisible =
            logInState.isLoading

        logInState.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(LogInEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: LogInViewModel.LogInUiEvent) {
        when (event) {
            is LogInViewModel.LogInUiEvent.NavigateToConnections -> findNavController().navigate(
                LogInFragmentDirections.actionLogInFragmentToConnectionsFragment()
            )
        }
    }
}
