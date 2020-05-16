//package com.dev.core.base
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.*
//import org.json.JSONObject
//import java.net.UnknownHostException
//
//open class BaseViewModel : ViewModel() {
//
//    interface CancelCoroutine {
//        fun getCoroutineScope(): CoroutineScope?
//    }
//
//    private val viewModelJob = SupervisorJob()
//
//    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
//        t.printStackTrace()
//    }
//
//    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + exceptionHandler)
//
//    open val lvError = MutableLiveData<FCErrorException>()
//
//    open val lvshowProgress = SingleLiveEvent<Boolean>()
//
//    open val errorMessageLiveData = SingleLiveEvent<String>()
//
//    fun launchDataLoad(block: suspend (scope: CoroutineScope) -> Unit): Job {
//        return uiScope.launch {
//            try {
//                block(this)
//            } catch (error: Exception) {
//                handleException(error)
//            } finally {
//            }
//        }
//    }
//
//    fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, maxMillis: Long = Long.MAX_VALUE, action: () -> Unit, onFinish: () -> Unit) = uiScope.launch {
//        delay(delayMillis)
//        if (repeatMillis > 0) {
//            var timeInMillis = 0L
//            while (true) {
//                if (timeInMillis >= maxMillis) {
//                    onFinish()
//                    break
//                }
//                action()
//                delay(repeatMillis)
//                timeInMillis += repeatMillis
//            }
//        } else {
//            action()
//        }
//    }
//
//    fun handleException(error: Exception) {
//        error.printStackTrace()
//        if (error !is CancellationException) {
//            lvError.value = FCErrorException.unexpectedError(error)
//        }
//        if (error is UnknownHostException) {
//            Crashlytics.logException(error)
//        }
//    }
//
//    fun isInProgress(): Boolean {
//        return lvshowProgress.value ?: false
//    }
//
//    fun getCoroutineScope(): CoroutineScope {
//        return uiScope
//    }
//
//    fun showErrorMessage(json: String) {
//        val exception: FCErrorException
//        exception = try {
//            val jsonObject = JSONObject(json)
//            val gson = Gson()
//            val type = object : TypeToken<FCError>() {}.type
//            FCErrorException(gson.fromJson(jsonObject.get("exception").toString(), type)!!)
//        } catch (e: Exception) {
//            FCErrorException(FCError("-400", "Something went wrong and we are sorry for that. Please try again later"))
//        }
//
//        lvError.value = exception
//    }
//
//    public override fun onCleared() {
//        LoggerUtils.logDebug("BaseViewModel", "onCleared")
//        super.onCleared()
//        viewModelJob.cancel()
//    }
//}