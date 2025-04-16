package app.pickle.composepincodeview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

enum class PinCodeViewValidationStatus {
    VERIFYING,
    VALID,
    INVALID,
    IDLE
}

@Composable
private fun PinCodeButton(
    modifier: Modifier,
    currentPincodeViewStatus: PinCodeViewValidationStatus,
    number: Int,
    currentPincode: String,
    maxPinCodeLength: Int,
    buttonSize: Dp = 54.dp,
    addedNumber: (String) -> Unit,
) {
    OutlinedButton(
        enabled = currentPincodeViewStatus in listOf(
            PinCodeViewValidationStatus.IDLE,
            PinCodeViewValidationStatus.INVALID
        ),
        onClick = {
            if (currentPincode.length < maxPinCodeLength) {
                addedNumber(number.toString())
            }
        },
        modifier = modifier.size(buttonSize)
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun PinCodeDotsView(
    modifier: Modifier = Modifier,
    pinCodeMaxLength: Int,
    currentLength: Int,
    enabled : Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        for (i in 0 until pinCodeMaxLength) {
            Text(
                text = if (i < currentLength) "●" else "○",
                modifier = Modifier,
                fontSize = 42.sp,
                color = if(enabled) MaterialTheme.colorScheme.onSurface else Color.LightGray
            )
        }
    }
}

@Composable
fun PinCodeInput(
    modifier: Modifier,
    pinCodeMaxLength: Int = 4,
    pinCodeOptions: PinCodeViewOptions = PinCodeViewOptions(),
    onPinCodeEntered: (String) -> Unit,
    isPinCodeValidStatus: PinCodeViewValidationStatus = PinCodeViewValidationStatus.IDLE
) {
    var pinCode by remember { mutableStateOf("") }

    LaunchedEffect(pinCode) {
        if (pinCode.length == pinCodeMaxLength) {
            onPinCodeEntered(pinCode)
        }
    }

    LaunchedEffect(isPinCodeValidStatus) {
        if (isPinCodeValidStatus == PinCodeViewValidationStatus.INVALID) {
            pinCode = ""
        }
    }


    // Display the pin code input
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Pincode indicators
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(isPinCodeValidStatus == PinCodeViewValidationStatus.VERIFYING) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
            }

            AnimatedVisibility(isPinCodeValidStatus == PinCodeViewValidationStatus.VALID) {
                Text(pinCodeOptions.messagePinCodeValid, style = MaterialTheme.typography.titleLarge)
            }

            AnimatedVisibility(isPinCodeValidStatus == PinCodeViewValidationStatus.IDLE) {
                Text(pinCodeOptions.messagePinCodePrompt, style = MaterialTheme.typography.titleLarge)
            }

            AnimatedVisibility(isPinCodeValidStatus == PinCodeViewValidationStatus.INVALID) {
                Text(pinCodeOptions.messageWrongPinCode, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.error)
            }


            ShakyBox(
                modifier = Modifier,
                shakeTrigger = PinCodeViewValidationStatus.INVALID == isPinCodeValidStatus,
            ) {
                PinCodeDotsView(
                    pinCodeMaxLength = pinCodeMaxLength,
                    currentLength = pinCode.length,
                    enabled = PinCodeViewValidationStatus.VERIFYING != isPinCodeValidStatus
                )
            }

        }
        // Pincode
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(pinCodeOptions.buttonSpacing)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(pinCodeOptions.buttonSpacing)
            ) {
                for (i in 1 until 4) {
                    PinCodeButton(
                        modifier = Modifier,
                        currentPincodeViewStatus = isPinCodeValidStatus,
                        number = i,
                        buttonSize = pinCodeOptions.buttonSize,
                        maxPinCodeLength = pinCodeMaxLength,
                        currentPincode = pinCode,
                        addedNumber = { addedNumber ->
                            pinCode += addedNumber
                        }
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(pinCodeOptions.buttonSpacing)
            ) {
                for (i in 4 until 7) {
                    PinCodeButton(
                        modifier = Modifier,
                        currentPincodeViewStatus = isPinCodeValidStatus,
                        number = i,
                        buttonSize = pinCodeOptions.buttonSize,
                        maxPinCodeLength = pinCodeMaxLength,
                        currentPincode = pinCode,
                        addedNumber = { addedNumber ->
                            pinCode += addedNumber
                        }
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(pinCodeOptions.buttonSpacing)
            ) {
                for (i in 7 until 10) {
                    PinCodeButton(
                        modifier = Modifier,
                        currentPincodeViewStatus = isPinCodeValidStatus,
                        number = i,
                        buttonSize = pinCodeOptions.buttonSize,
                        maxPinCodeLength = pinCodeMaxLength,
                        currentPincode = pinCode,
                        addedNumber = { addedNumber ->
                            pinCode += addedNumber
                        }
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(pinCodeOptions.buttonSpacing)
            ) {
                TextButton(
                    enabled = isPinCodeValidStatus in listOf(
                        PinCodeViewValidationStatus.IDLE,
                        PinCodeViewValidationStatus.INVALID
                    ),
                    onClick = {
                        // Remove the last character from the pin code
                        if (pinCode.isNotEmpty()) {
                            pinCode = pinCode.dropLast(1)
                        }
                    },
                    modifier = Modifier
                ) {
                    Text(text = pinCodeOptions.clearButtonText, style = MaterialTheme.typography.titleMedium)
                }
                PinCodeButton(
                    modifier = Modifier,
                    currentPincodeViewStatus = isPinCodeValidStatus,
                    number = 0,
                    buttonSize = pinCodeOptions.buttonSize,
                    maxPinCodeLength = pinCodeMaxLength,
                    currentPincode = pinCode,
                    addedNumber = { addedNumber ->
                        pinCode += addedNumber
                    }
                )
                Box(
                    modifier = Modifier.size(pinCodeOptions.buttonSize)
                ) {

                }
            }
        }
    }

}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PinCodeInputPreview() {
    var pincodeStatus by remember { mutableStateOf(PinCodeViewValidationStatus.IDLE) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Pin Code Input")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Button(
                onClick = {
                    pincodeStatus = PinCodeViewValidationStatus.VALID
                }
            ) {
                Text(text = "PinCode OK")
            }

            Button(
                onClick = {
                    pincodeStatus = PinCodeViewValidationStatus.INVALID
                }
            ) {
                Text(text = "PinCode Wrong")
            }

            PinCodeInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                isPinCodeValidStatus = pincodeStatus,
                onPinCodeEntered = { pinCode ->
                    pincodeStatus = PinCodeViewValidationStatus.VERIFYING
                }
            )
        }
    }
}