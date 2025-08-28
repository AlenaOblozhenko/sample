package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val initialText = "Hello UiAutomator!"
    private val emptyText = "   "
    private val fullText = "test123"

    @Test
    fun testEmptyLine() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(MODEL_PACKAGE)), TIMEOUT)

        device.findObject(By.res(MODEL_PACKAGE,"userInput")).text = emptyText
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()

        val result = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text
        assertEquals(initialText,result)
    }

    @Test
    fun testNewActivity() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(MODEL_PACKAGE)), TIMEOUT)

        device.findObject(By.res(MODEL_PACKAGE,"userInput")).text = fullText
        device.findObject(By.res(MODEL_PACKAGE, "buttonActivity")).click()

        Thread.sleep(5000)

        val result = device.findObject(By.res(MODEL_PACKAGE, "text")).text
        assertEquals(fullText,result)
    }

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testInternetSettings() {
        waitForPackage(SETTINGS_PACKAGE)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }
}

