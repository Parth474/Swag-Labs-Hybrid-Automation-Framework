# 📌 SwagLabs Automation Framework

A robust Selenium + TestNG automation framework designed for scalable, maintainable, and cross-browser testing. This project demonstrates real-world automation practices including Docker-based Selenium Grid, data-driven testing, reporting, and logging.

---

## 🚀 Key Features

* Page Object Model (POM) architecture
* Cross-browser testing using Selenium Grid (Dockerized)
* Standalone execution setup for local testing
* Data-driven testing using Excel
* Custom listeners for screenshot capture
* Extent Reports integration (HTML reporting)
* Log4j logging implementation
* Maven-based project structure
* Batch file execution (run without IDE)

---

## 🏗️ Project Structure

```
src/test/java      
│
├── basetest        → Base setup (driver initialization, configuration)
├── dataprovider    → Excel data handling
├── listener        → Screenshot and reporting listeners
├── pages           → Page Object classes
├── tests           → Test cases
├── utils           → Utility classes (Excel, screenshots)
│
src/test/resources
├── testData        → Excel test data
├── config.properties
├── log4j2.xml
│
testng-suites       → TestNG XML files  
docker-compose.yml  → Selenium Grid setup  
run.bat             → Execution without IDE
```
<img width="421" height="667" alt="Screenshot 2026-04-04 204252" src="https://github.com/user-attachments/assets/4d3796de-fc42-4707-9566-d4772becdc5b" />



---

## ⚙️ Tech Stack

* **Language:** Java
* **Automation Tool:** Selenium WebDriver
* **Test Framework:** TestNG
* **Build Tool:** Maven
* **Reporting:** Extent Reports
* **Logging:** Log4j2
* **Data Handling:** Apache POI
* **Driver Management:** WebDriverManager
* **Containerization:** Docker with Selenium Grid

---

## 🧪 Execution Options

### 1. Run using Maven

```
mvn clean test
```

### 2. Run using TestNG Suite

```
mvn test -DsuiteXmlFile=testng-grid.xml
```

### 3. Run using Batch File

```
run.bat
```

This option allows execution without opening the project in an IDE.

---

## 🐳 Docker Setup (Selenium Grid)

### Start Grid

```
docker-compose up -d
```

### Execute Tests

* Uses `testng-grid.xml`
* Runs tests across multiple browsers and environments
Screenshot of executing Session & Container
1. Running docker container for node & hub setup
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/32a92b58-61ac-4b42-b908-28c3cf4e339d" />
.
2. Running Sessions
<img width="1920" height="1080" alt="Screenshot (51)" src="https://github.com/user-attachments/assets/c407ebe8-0f8a-4982-ab5c-2acd9826ec39" />



### Stop Grid

```
docker-compose down
```

---

## 🧪 Standalone Execution

Tests can also be executed locally without Selenium Grid using standard WebDriver configuration.
---
<img width="1920" height="1080" alt="Screenshot (47)" src="https://github.com/user-attachments/assets/ad041afe-8c68-4f2b-905b-51b30e0c1157" />
<img width="1920" height="1080" alt="Screenshot (46)" src="https://github.com/user-attachments/assets/9b9da026-44b5-48a0-ac17-743c9b54d9f3" />

## 📊 Reporting

Extent Reports are generated after execution, providing:

* Execution summary
* Pass/Fail status
* Step-level logs
* Screenshots for failed test cases

**Report Screenshot**
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/68b0b848-c0ed-46c8-9ce3-194aae669907" />

**Report Location:**

```
/test-output/ExtentReport.html
```

---

## 📝 Logging

Logging is implemented using Log4j2 to provide detailed execution insights and assist in debugging.
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/aeb56230-a8dd-4f22-aff6-948a2439f5fe" />

---

## 📂 Data-Driven Testing

Test data is managed using Excel files via Apache POI.

**File Location:**

```
src/test/resources/testData/TestData.xlsx
```

---

## 📸 Screenshot Capture

Screenshots are automatically captured on test failure through TestNG listeners and included in the report.

---

## 🔧 Configuration

Configuration is managed through `config.properties`, allowing flexibility to switch:

* Browser
* Environment
* Execution mode (local or grid)

---

## 💡 Highlights

This framework focuses on practical automation design principles:

* Structured and maintainable codebase
* Scalable execution using Selenium Grid
* Modular design using Page Object Model
* Support for multiple execution strategies
* Suitable for CI/CD integration

---

## 🔮 Future Enhancements

* CI/CD integration (GitHub Actions or Jenkins)
* Improved parallel execution
* API test integration
* Migration or support for Playwright

---

## 👨‍💻 Author
Parth
