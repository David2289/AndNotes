package com.example.display.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.display.business.model.User
import com.example.display.business.model.Users
import com.example.display.business.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ListViewModel constructor(private val usersRepository: UsersRepository) : ViewModel() {

    var isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var users: MutableLiveData<ArrayList<User>> = MutableLiveData()
    var userList: ArrayList<User> = ArrayList()

    init {
        getUsers()
        isLoadingLiveData.value = true
    }

    private fun getUsers() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { usersRepository.getUsers(1) }
            handleResponse(result)
        }
    }

    private fun handleResponse(response: Response<Users>) {
        if (response.isSuccessful && response.body() != null) {
            this.userList.clear()
            this.userList.addAll(response.body()!!.data)
            users.value = this.userList
        } else {
            handleError(response.errorBody().toString())
        }
    }

    private fun handleError(t: String) {
        Log.w("RETROFIT", "HAS BEEN AN ERROR: $t")
    }

}