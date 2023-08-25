package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    //login
    fun onUserNameChanged(userName: String)
    fun onPasswordChanged(password: String)
    fun onKeepLoggedInClicked()
    fun onClickLogin(userName: String, password: String, isKeepMeLoggedInChecked: Boolean)

    //permission will move
    fun onOwnerEmailChanged(ownerEmail: String)
    fun onNameChanged(restaurantName: String)
    fun onDescriptionChanged(description: String)
    fun onRequestPermissionClick()
    fun onClickSubmit()
    fun onCancelClick()
}