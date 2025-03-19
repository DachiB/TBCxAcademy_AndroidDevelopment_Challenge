package com.example.challenge.presentation.ui.connection

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.BaseFragment
import com.example.challenge.databinding.FragmentConnectionsBinding
import com.example.challenge.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectionsFragment :
    BaseFragment<FragmentConnectionsBinding>(FragmentConnectionsBinding::inflate) {

    private val viewModel: ConnectionsViewModel by viewModels()
    private lateinit var connectionsRecyclerAdapter: ConnectionsRecyclerAdapter

    override fun start() {
        connectionsRecyclerAdapter = ConnectionsRecyclerAdapter()
        binding.apply {
            recyclerConnections.layoutManager = LinearLayoutManager(requireContext())
            recyclerConnections.adapter = connectionsRecyclerAdapter
        }
        viewModel.onEvent(ConnectionEvent.FetchConnections)
    }

    override fun listeners() {
        binding.btnLogOut.setOnClickListener {
            viewModel.onEvent(ConnectionEvent.LogOut)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionState.collect {
                    handleConnectionState(state = it)
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

    private fun handleConnectionState(state: ConnectionState) {
        binding.loaderInclude.loaderContainer.isVisible = state.isLoading

        state.connections?.let {
            connectionsRecyclerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(ConnectionEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: ConnectionsViewModel.ConnectionUiEvent) {
        when (event) {
            is ConnectionsViewModel.ConnectionUiEvent.NavigateToLogIn -> {
                findNavController().navigate(ConnectionsFragmentDirections.actionConnectionsFragmentToLogInFragment())
            }
        }

    }
}

