# OderoPay Payment SDK

## 1. Overview

The OderoPay SDK is a software development kit that allows developers to integrate payment functionality into their applications.<br>
With this SDK, users can securely and easily make payments within the app.<br><br>
The OderoPay SDK is compatible with Android SDK 21 ad above.

## 2. Support
OderoPay supports **Visa, Visa Electron, MasterCard, Maestro and American Express** card associations.

OderoPay supports following payment: **Single Card, Multiple Cards**

OderoPay supports **3DS Secure** Payment

OderoPay supports **Card Storage** feature
## 3. Getting Started

### 3.1 Installation

To get started with the SDK, follow these steps:


**1. Add the Artifactory repository to your project's build.gradle file:**

```java
maven {
    url "token-artifactory-url"
    credentials {
        username = "token-username"
        password = "token-password"
    }
}

```
Make sure to replace token-artifactory-url, token-username, and token-password with the actual values provided to you.

**2. Add the SDK dependency to your app's build.gradle file:**
```java
implementation 'com.token:oderopay:1.0.0'
```
**3. Sync your project with Gradle to download the SDK and its dependencies.**<br><br>

## 4. SDK Initialization
To initialize the SDK, add the following code in your Application class or activity or fragment:

```java
try {
   OderoLibrary.initSDK(Environment.SANDBOX_TR)

} catch (SDKAlreadyInitializedException e) {
    Log.e(TAG, "SDKAlreadyInitialized: " + e.getMessage());
}

```

## 5. Usage

### 5.1 Init Service
The init service is used to initialize the OderoPay SDK with the payment token obtained from our backend. You should call this function before calling the startPayment() function.

##### 5.1.1 Example
To obtain the initialization token required for starting the SDK, you should make a request to the initialization service provided by our backend. Once you have obtained the initialization token, you can pass it to the startPayment() function to initialize the OderoPay SDK.
###### Request
```java
val call = repository.requestInitToken(
    InitRequest(
        paymentGroup = "PRODUCT",
        callbackUrl = "NO_CALLBACK_URL",
        paidPrice = 100,
        price = 100,
        conversationId = "conversationid", //ForMultiPayment
        currency = "TRY",//Currency
        cardUserKey = "card-user-key", //For StoredCard Feature
        items = listOf(
            Item("Product", 100)
        )
    )
)
```
###### Response
```java
data class InitResponse(
    val data : TokenInfo
)
```
```java
data class TokenInfo(
    var token : String, // Start SDK with this token
    var pageUrl:String
)
```

### 5.2 Start Payment
The startPayment() function is used to start the payment process in the OderoPay SDK. You should call this function when the user initiates a payment in your application.

##### 5.2.1 Function Parameters
The startPayment() function takes the following parameters:

- **token**: the token required to initiate the payment process.
- **applicationContext**: the application context of the calling activity
- **listener**: an instance of OderoPayResultListener to receive callbacks from the SDK

##### 5.2.2 Example
Here's an example of how to use the startPayment() function in your application:

```java
val token = "your-payment-token"

OderoLibrary.getInstance()?.startPayment(
    token = token,
    applicationContext = applicationContext,
    listener = object : OderoPayResultListener {
        override fun onOderoPaySuccess(result: OderoResult) {
            // Handle successful payment
        }

        override fun onOderoPayCancelled() {
            // Handle cancelled payment
        }

        override fun onOderoPayFailure(errorId: Int, errorMsg: String?) {
            // Handle payment failure
        }
    })

```

## 6. Handling Exception
The SDK provides the following exception classes that you can use to handle errors in your application:

##### SDKAlreadyInitializedException
This exception is thrown when you try to initialize the SDK more than once. It indicates that the SDK has already been initialized and further attempts to initialize it are unnecessary.

##### SDKInvalidInputException
This exception is thrown when the SDK receives invalid input from your application. It indicates that the input provided to the SDK is incorrect and needs to be corrected before proceeding.

##### SDKNotInitializedException
This exception is thrown when you try to use the SDK before it has been initialized. It indicates that the SDK has not yet been properly set up and cannot be used until it has been initialized.

## 7. Customization Interfaces
##### OderoPayManager
```java
    fun startPayment(token : String, applicationContext: Context, listener : OderoPayResultListener)
    fun isInitialized() : Boolean
    fun changeToWebView(isWebView: Boolean)
    fun setDefaultResultScreens(value: Boolean)
    fun forceLanguage(language: Languages)
```
##### OderoPayButtonCustomize

```java
    fun setStrokeWidthAndColor(width: Float,color :Int)
    fun setSize(width: Int, height: Int)
    fun setRoundness(radius: Float)
```

## 8. Environments
The Environment enum is used to specify the environment that the OderoPay SDK should be initialized with. You can choose between the following environments:

- **SANDBOX_TR**: the sandbox environment for Turkey
- **SANDBOX_AZ**: the sandbox environment for Azerbaijan
- **PROD_TR**: the production environment for Turkey
- **PROD_AZ**: the production environment for Azerbaijan

## 9. Proguard
If you're using ProGuard, add the following rules to your ProGuard configuration file to ensure that the SDK works correctly:
```java
-keep class com.token.oderopay.** { *; }
```
## License
Copyright © 2022 Token Payment Services and Electronic Money Inc. All rights reserved.



