# PinCodeInput - Jetpack Compose PIN Code View

A customizable PIN code input view for Jetpack Compose. Perfect for secure validation screens (authentication, payments, etc.), this component includes a shake animation, validation state handling, and a smooth UI.

## ✨ Features
- PIN code input with a numeric keypad
- Shake animation on error
- Validation state messages (loading, success, error)
- Easily customizable size, spacing, and messages

---

## 🚀 Installation

Add this module to your Compose project.

1. Copy the `PinCodeInput.kt` file into your `ui/components` or preferred directory.
2. Make sure you have the necessary Compose dependencies in your `build.gradle`:

```kotlin
implementation("androidx.compose.material3:material3:<version>")
implementation("androidx.compose.ui:ui-tooling-preview:<version>")
implementation("androidx.compose.animation:animation:<version>")
```

---

## ✨ Basic Usage
* The philosophy is to let the parent view handle the validation logic. 
* The `PinCodeInput` component will notify the parent when the PIN code is entered, then the parent can validate it and update the state.
```kotlin
var pincodeStatus by remember { mutableStateOf(PinCodeViewValidationStatus.IDLE) }

PinCodeInput(
    modifier = Modifier.fillMaxWidth(),
    onPinCodeEntered = { pinCode ->
        // Your validation logic here
        pincodeStatus = PinCodeViewValidationStatus.VERIFYING
    },
    isPinCodeValidStatus = pincodeStatus
)
```

---

## 🔧 Customization Options

The `PinCodeInput` component accepts the following options via `PinCodeViewOptions`:

```kotlin
data class PinCodeViewOptions(
    val maxPinCodeLength: Int = 4,
    val buttonSpacing: Dp = 8.dp,
    val buttonSize: Dp = 64.dp,
    val messageWrongPinCode : String = "Wrong pin code !",
    val messagePinCodeValid : String = "Pin code is valid !",
    val messagePinCodePrompt : String = "Enter your pin code",
    val clearButtonText : String = "C",
    val buttonsCustomization: ButtonDefaults = ButtonDefaults
)
```

You can customize:
- Number of digits
- Display messages
- Button spacing and size
- Clear button text

---

## 🔍 Preview

Includes a `@Preview` to quickly try it out in Android Studio:

```kotlin
@Preview
@Composable
fun PinCodeInputPreview() {
    var pincodeStatus by remember { mutableStateOf(PinCodeViewValidationStatus.IDLE) }
    PinCodeInput(
        modifier = Modifier.fillMaxWidth(),
        isPinCodeValidStatus = pincodeStatus,
        onPinCodeEntered = {
            pincodeStatus = PinCodeViewValidationStatus.VERIFYING
        }
    )
}
```

---

## 🌟 To-do / Ideas
- Add unit tests
- Haptic feedback support
- Improved accessibility (TalkBack, etc.)

---

## ✉️ Contribution
Issues, PRs, and suggestions are welcome!

---

## ✅ License
[MIT](LICENSE)