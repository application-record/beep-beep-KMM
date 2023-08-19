package org.thechance.common.presentation.users

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.domain.usecase.IGetUsersUseCase
import org.thechance.common.presentation.uistate.UserScreenUiState
import org.thechance.common.presentation.uistate.toUiState


class UserScreenModel : StateScreenModel<UserScreenUiState>(UserScreenUiState()), KoinComponent {
    private val getUsers: IGetUsersUseCase by inject()

    init {
        updateUsers()
    }

    private fun updateUsers() {
        mutableState.update { it.copy(users = getUsers().toUiState()) }
    }
}