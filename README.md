## 1. QA Automation Project - Day 29

![Java](https://img.shields.io/badge/Java-17-orange)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.x-green)
![ExtentReports](https://img.shields.io/badge/ExtentReports-5.x-purple)

## 2. Deskripsi
Project ini adalah automation testing untuk website [SauceDemo](https://www.saucedemo.com/) menggunakan Java, Selenium WebDriver, TestNG, dan ExtentReports. Tujuan utama adalah membuat test suite yang dapat dijalankan secara data-driven, dilengkapi screenshot otomatis pada saat gagal, serta reporting yang rapi dan modern.
Fitur utama:
- Login testing dengan **data driven** menggunakan Excel
- Listener custom untuk **ExtentReports**
- Screenshot otomatis saat test gagal
- Laporan test modern dengan tema **Dark**
- Menampilkan status test, execution time, deskripsi kasus uji, dan penyebab kegagalan

## 3. Persyaratan
- Java 17
- Gradle 8.x
- Selenium WebDriver
- TestNG 7.x
- ExtentReports 5.x
- Apache Commons IO (untuk screenshot)
  
## 4. Setup
1. Clone repository:
```bash
git clone https://github.com/farichin77/Assigment-day-29-listener-reporting.git
cd Assigment-day-29-listener-reporting

## 5. Jalankan Test
untuk menjalankan test da 2 cara: 
1) ./gradlew clean test
2) ./gradlew clean test "-Psuite=test-regression.xml"



