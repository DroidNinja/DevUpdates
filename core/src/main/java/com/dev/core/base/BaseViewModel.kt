package com.dev.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.core.model.ErrorException
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        t.printStackTrace()
        handleException(t)
    }

    open val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + exceptionHandler)

    open val _lvError = MutableLiveData<ErrorException>()
    val lvError: LiveData<ErrorException>
        get() = _lvError

    open val _lvShowProgress = MutableLiveData<Boolean>()
    val lvShowProgress: LiveData<Boolean>
        get() = _lvShowProgress


    fun launchDataLoad(block: suspend () -> Unit): Job {
        return uiScope.launch {
            block()
        }
    }

    private fun handleException(error: Throwable) {
        if (error !is CancellationException) {
            _lvError.value = ErrorException.unexpectedError(error)
        }
    }

    public override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}