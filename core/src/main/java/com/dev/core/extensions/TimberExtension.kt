package com.dev.core.extensions

import timber.log.Timber

/*
 * Adapted from [Slimber](https://github.com/PaulWoitaschek/Slimber/blob/bea76b32563906edc8cf196ce4b6cfce8d12d6e6/slimber/src/main/kotlin/de/paul_woitaschek/slimber/Slimber.kt)
 */

/** Invokes an action if any trees are planted */
inline fun ifPlanted(action: () -> Unit) {
    if (Timber.treeCount() != 0) {
        action()
    }
}

/** Delegates the provided message to [Timber.e] if any trees are planted. */
inline fun e(throwable: Throwable? = null, message: () -> String) = ifPlanted {
    throwable?.let {
        Timber.e(it, message())
    } ?: run {
        Timber.e(message())
    }
}

/** Delegates the provided message to [Timber.w] if any trees are planted. */
inline fun w(throwable: Throwable? = null, message: () -> String) = ifPlanted {
    throwable?.let {
        Timber.w(it, message())
    } ?: run {
        Timber.w(message())
    }
}

/** Delegates the provided message to [Timber.i] if any trees are planted. */
inline fun i(throwable: Throwable? = null, message: () -> String) = ifPlanted {
    throwable?.let {
        Timber.i(it, message())
    } ?: run {
        Timber.i(message())
    }
}

/** Delegates the provided message to [Timber.d] if any trees are planted. */
inline fun d(throwable: Throwable? = null, message: () -> String) = ifPlanted {
    throwable?.let {
        Timber.d(it, message())
    } ?: run {
        Timber.d(message())
    }
}

/** Delegates the provided message to [Timber.v] if any trees are planted. */
inline fun v(throwable: Throwable? = null, message: () -> String) = ifPlanted {
    throwable?.let {
        Timber.v(it, message())
    } ?: run {
        Timber.v(message())
    }
}

/** Delegates the provided message to [Timber.wtf] if any trees are planted. */
inline fun wtf(throwable: Throwable? = null, message: () -> String) = ifPlanted {
    throwable?.let {
        Timber.wtf(it, message())
    } ?: run {
        Timber.wtf(message())
    }
}

/** Delegates the provided message to [Timber.log] if any trees are planted. */
inline fun log(priority: Int, t: Throwable, message: () -> String) = ifPlanted {
    Timber.log(priority, t, message())
}

/** Delegates the provided message to [Timber.log] if any trees are planted. */
inline fun log(priority: Int, message: () -> String) = ifPlanted {
    Timber.log(priority, message())
}
