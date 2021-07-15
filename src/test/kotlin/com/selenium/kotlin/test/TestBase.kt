package com.selenium.kotlin.test

import io.cloudbeat.testng.CbTestNGListener.wrapWebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * Project Name    : kotlin-selenium-testng-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 4/11/2020
 * Time            : 9:49 AM
 * Description     : This is the base class for tests
 **/

abstract class TestBase {

    lateinit var driver: WebDriver

    @BeforeMethod
    open fun setup() {
        WebDriverManager.chromedriver().setup()
        // log network requests
        val options = ChromeOptions()
        val logPrefs = LoggingPreferences()
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL)
        options.setCapability("goog:loggingPrefs", logPrefs)
        // wrap driver to monitor web driver actions
        driver = wrapWebDriver(ChromeDriver(options))
        //driver = ChromeDriver()
        driver.manage()?.timeouts()?.implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage()?.window()?.maximize()
        driver.get("http://automationpractice.com/")
    }

    @AfterMethod
    open fun tearDown() {
        driver.close()
    }

    fun getPageTitle(): String? {
        return driver.title
    }

    open fun waitForPageLoad(driver: WebDriver?) {
        val pageLoadCondition: ExpectedCondition<Boolean> =
            ExpectedCondition { driver -> (driver as JavascriptExecutor?)!!.executeScript("return document.readyState") == "complete" }
        val wait = WebDriverWait(driver, 30)
        wait.until(pageLoadCondition)
    }


}