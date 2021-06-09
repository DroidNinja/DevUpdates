package me.arunsharma.devupdates.utils

import com.squareup.moshi.internal.Util
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class TypeToken<T>{
    val type: Type
        get() = run{
        val superclass = javaClass.genericSuperclass
        Util.canonicalize((superclass as ParameterizedType).actualTypeArguments[0])
    }
}