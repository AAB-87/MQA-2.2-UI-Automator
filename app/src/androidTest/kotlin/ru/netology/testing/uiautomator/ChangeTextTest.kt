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

@RunWith(AndroidJUnit4::class) // показыввает с помощью какого класса нужно запускать тест
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"

//    @Test
//    fun testInternetSettings() {
//        // Нажимаем на кнопку Домой
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Ожидаем запуск
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)

//        // Запускаем меню настроек системы
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val intent = context.packageManager.getLaunchIntentForPackage(SETTINGS_PACKAGE)
//        context.startActivity(intent)

//        // Ожидаем что меню появились
//        device.wait(Until.hasObject(By.pkg(SETTINGS_PACKAGE)), TIMEOUT)
//
//        // Кликаем на секцуию Network & Internet (Сети)
//        device.findObject(
//            UiSelector().resourceId("android:id/title").instance(0)
//        ).click()
//    }

//    @Test
//    fun testChangeText() {
//        // Нажимаем на кнопку Домой
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Ожидаем запуск
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        // Запускаем наше приложение
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val packageName = context.packageName
//        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
//        context.startActivity(intent)

//        // Ожидаем приложение появились
//        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
//
//        // Ищем на икране элемент
//        device.findObject(By.res(packageName, "userInput")).text = textToSet // вводим текст содержащийся в переменной textToSet
//        device.findObject(By.res(packageName, "buttonChange")).click()  // кликаем по кнопке Change text
//
//        // Сравниваем результат на экране с введённым текстом
//        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
//        assertEquals(result, textToSet)
//    }


    // Код не объяснялся
    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Нажимаем кнопку Домой
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Запускаем и ожидаем запуск приложения launcherPackageName
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

    @Test
    fun testChangeText() {
        val packageName = MODEL_PACKAGE // В packageName помещаем константу (приложение)(см. выше)
        waitForPackage(packageName) // ожидание запуска приложения

        device.findObject(By.res(packageName, "userInput")).text = textToSet // вводим текст содержащийся в переменной textToSet
        device.findObject(By.res(packageName, "buttonChange")).click() // кликаем по кнопке Change text

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, textToSet) // проверяем что обе переменные имеют одинаковые значения
    }

    @Test
    fun testEmptyText() {
        val packageName = MODEL_PACKAGE // В packageName помещаем константу (приложение)(см. выше)
        waitForPackage(packageName) // ожидание запуска приложения

        device.findObject(By.res(packageName, "userInput")).text = "  " // вводим пустой текст (2 пробела)
        device.findObject(By.res(packageName, "buttonChange")).click() // кликаем по кнопке "Change text"

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text // отображается введённый текст
        assertEquals(result, "Hello UiAutomator!") //этот текст по умолчанию в строке, проверяем что он остаётся неизменным
    }

    @Test
    fun textInNewActivityTest() {
        val packageName = MODEL_PACKAGE // В packageName помещаем константу (приложение)(см. выше)
        waitForPackage(packageName) // ожидание запуска приложения

        device.findObject(By.res(packageName, "userInput")).text = "New text" // вводим текст
        device.findObject(By.res(packageName, "buttonActivity")).click() // кликаем по кнопке "Open text in Another activity"

        waitForPackage(packageName) // ожидание запуска нового окна

        val result = device.findObject(By.res(packageName, "text")).text // отображается введённый текст
        assertEquals(result, "New text") // проверяем что обе переменные имеют одинаковые значения
    }

}



