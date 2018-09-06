from appium import webdriver
import time

caps = {}
caps["platformName"] = "Android"
caps["platformVersion"] = "7.0"
caps["deviceName"] = "huawei"
caps["appActivity"] = ".MainActivity"
caps["appPackage"] = "com.example.test.myapplication"
caps["autoGrantPermissions"] = "true"
driver = webdriver.Remote("http://localhost:4723/wd/hub", caps)



el1 = driver.find_element_by_id("com.example.test.myapplication:id/et_name")
el1.send_keys("22032277")

el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_pass")
el2.send_keys("wyhcb18276")

el3 = driver.find_element_by_id("com.example.test.myapplication:id/login")
el3.click()

driver.quit()