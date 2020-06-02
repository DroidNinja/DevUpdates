package com.dev.core.extensions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.dev.core.R
import com.dev.core.base.BaseFragment

fun Activity.toast(message: String) = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

fun FragmentActivity.addFragment(fragment: BaseFragment, frameId: Int, addToBackstack: Boolean = true, navAnimation: Boolean = true) {
    supportFragmentManager.inTransaction {
        if (addToBackstack) {
            addToBackStack(fragment.getFragmentTag())
        }
        if (navAnimation) {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        add(frameId, fragment)
    }
}

fun FragmentActivity.replaceFragment(fragment: BaseFragment, frameId: Int, addToBackstack: Boolean = true, navAnimation: Boolean = true) {
    supportFragmentManager.inTransaction {
        if (addToBackstack) {
            addToBackStack(fragment.getFragmentTag())
        }
        if (navAnimation) {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        replace(frameId, fragment, fragment.getFragmentTag())
    }
}

fun FragmentActivity.popTo(fragmentTag: String, flag: Int = FragmentManager.POP_BACK_STACK_INCLUSIVE) {
    supportFragmentManager.popBackStack(fragmentTag, flag)
}

fun FragmentActivity.getCurrentFragment(frameId: Int): BaseFragment? {
    return supportFragmentManager.findFragmentById(frameId) as? BaseFragment
}

fun FragmentActivity.isFragmentInBackStack(fragmentTag: String): Boolean {
    return supportFragmentManager.findFragmentByTag(fragmentTag) != null
}

fun FragmentActivity.pop() {
    if (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStackImmediate()
    }
}

fun Fragment.pop() {
    if (childFragmentManager.backStackEntryCount > 0) {
        childFragmentManager.popBackStack()
    } else {
        activity?.pop()
    }
}

fun FragmentActivity.popToRoot() {
    val first = supportFragmentManager.getBackStackEntryAt(0)
    supportFragmentManager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.executePendingTransactions()
}

fun FragmentActivity.popAll() {
    if (supportFragmentManager.backStackEntryCount > 0) {
        val first = supportFragmentManager.getBackStackEntryAt(0)
        supportFragmentManager.popBackStackImmediate(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        getCurrentFragment(R.id.container)?.let {
            removeFragment(it)
        }
    }
}

fun FragmentActivity.popAllAndReplace(fragment: BaseFragment, frameId: Int, addToBackstack: Boolean = true) {
    val first = supportFragmentManager.getBackStackEntryAt(0)
    supportFragmentManager.popBackStackImmediate(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    getCurrentFragment(R.id.container)?.let {
        removeFragment(it)
    }
    replaceFragment(fragment, frameId, addToBackstack)
}

fun FragmentActivity.removeFragment(fragment: BaseFragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
}

fun FragmentActivity.replaceFragment(fragment: BaseFragment, frameId: Int, addToBackstack: Boolean) {
    supportFragmentManager.inTransaction {
        if (addToBackstack)
            addToBackStack(fragment.getFragmentTag())
        replace(frameId, fragment)
    }
}

inline fun FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) {
    beginTransaction().func().commit()
}