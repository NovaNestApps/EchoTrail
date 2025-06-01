# EchoTrail

EchoTrail is a lightweight, component-agnostic structured logging library for Android. It helps developers trace the execution of business logic across layers like use cases, repositories, ViewModels, mappers, and utility functions — without polluting the codebase with ad-hoc `Log.d()` or print statements.

[![](https://jitpack.io/v/NovaNestApps/EchoTrail.svg)](https://jitpack.io/#NovaNestApps/EchoTrail)

## ✨ Why EchoTrail?

Debugging complex flows in a modular codebase can be time-consuming. EchoTrail gives you a consistent, readable, and structured way to track what's happening inside your logic — so you can catch issues faster, understand behaviors clearly, and stay in flow.

## 🚀 Features

* `logExecution(tag, input) { ... }` inline wrapper for synchronous code
* `logExecutionSuspend(tag, input) { ... }` suspend-friendly variant for coroutine use
* Logs:

  * Start event with inputs
  * Success with result and execution time
  * Failure with error message and duration
* Output to Logcat in structured format
* Enabled in debug builds only by default
* Minimal setup, works across all layers of your app

## 🧱 Log Format

```
[2025-06-01 14:05:33] [INFO] [LoginUseCase] Started with input: email=abc@example.com
[2025-06-01 14:05:33] [INFO] [LoginUseCase] Success with result: AuthToken(token=xyz) in 120ms
[2025-06-01 14:05:33] [ERROR] [LoginUseCase] Failed with error: NullPointerException - user is null in 25ms
```

## 🛠 How to Use

### 1. Add JitPack Repository

```kotlin
repositories {
  maven("https://jitpack.io")
}
```

### 2. Add Dependency

```kotlin
dependencies {
  implementation("com.github.NovaNestApps:EchoTrail:1.0.0")
}
```

### 3. Wrap Any Logic

```kotlin
val result = logExecution("MyUseCase", input = "userId=2") {
  repository.fetchUser("2")
}
```

### 4. Wrap Suspend Calls

```kotlin
val response = logExecutionSuspend("ApiCall", input = "email=test@mail.com") {
  api.login("test@mail.com", "password")
}
```

### 5. Customize Logger (optional)

```kotlin
EchoTrailConfig.logger = MyCustomLogger()
EchoTrailConfig.isLoggingEnabled = BuildConfig.DEBUG
```

## 🧪 Sample App Included

The demo app shows how to:

* Use EchoTrail to wrap a Retrofit API call
* View the log trace for start, success, and failure
* Output response and error clearly in Logcat

## 🤝 Contributing

We follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) to ensure automated versioning and changelogs.

Use commit messages like:

* `fix: handle empty input in logger`
* `feat: add logExecutionSuspend support`
* `chore: update README`
* `refactor: simplify duration logic`

Pull requests should target the `main` branch and include relevant unit tests where applicable.

## 🧩 What's Next

* Structured (JSON-style) logging
* Firebase/Sentry integration
* Debug log viewer overlay

Let EchoTrail show you what your app is thinking. 🧠
