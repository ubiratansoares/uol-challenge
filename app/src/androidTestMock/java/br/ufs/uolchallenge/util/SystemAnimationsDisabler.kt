package br.ufs.uolchallenge.util

import android.content.pm.PackageManager
import android.os.IBinder
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import android.util.Log

/**
 * Created by bira on 11/7/17.
 */

class SystemAnimationsDisabler {
    private val ANIMATION_PERMISSION = "android.permission.SET_ANIMATION_SCALE"
    private val DISABLED = 0.0f

    init {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val targetContext = instrumentation.targetContext
        UiDevice
                .getInstance(instrumentation)
                .executeShellCommand(
                        "pm grant "
                                + targetContext.packageName
                                + ANIMATION_PERMISSION
                )

    }

    fun disableAll() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val targetContext = instrumentation.targetContext
        val permStatus = targetContext.checkCallingOrSelfPermission(ANIMATION_PERMISSION)
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(DISABLED)
        }
    }

    private fun setSystemAnimationsScale(animationScale: Float) {
        try {
            val windomStubClazz = Class.forName("android.view.IWindowManager\$Stub")
            val asInterface = windomStubClazz.getDeclaredMethod("asInterface", IBinder::class.java)
            val serviceManagerClazz = Class.forName("android.os.ServiceManager")
            val getService = serviceManagerClazz.getDeclaredMethod("getService", String::class.java)
            val windowManagerClazz = Class.forName("android.view.IWindowManager")
            val setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", FloatArray::class.java)
            val getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales")

            val windowManagerBinder = getService.invoke(null, "window") as IBinder
            val windowManagerObj = asInterface.invoke(null, windowManagerBinder)
            val currentScales = getAnimationScales.invoke(windowManagerObj) as FloatArray
            for (i in currentScales.indices) {
                currentScales[i] = animationScale
            }
            setAnimationScales.invoke(windowManagerObj, arrayOf<Any>(currentScales))
        } catch (e: Exception) {
            Log.e("SystemAnimations", "Could not change animation scale to $animationScale :'(")
        }

    }
}