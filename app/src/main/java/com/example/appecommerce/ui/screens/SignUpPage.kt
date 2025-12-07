package com.example.appecommerce.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appecommerce.R
import com.example.appecommerce.repository.FirebaseAuthRepository
import kotlinx.coroutines.launch

@Composable
fun SignUpPage(
    onSignUpSuccess: () -> Unit,
    onBackClick: () -> Unit
) {

    // Variables non-nullables
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    // Erreurs
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val repository = FirebaseAuthRepository()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            "<",
            fontSize = 26.sp,
            modifier = Modifier.clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Create Account",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // NAME
        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it.trim()
                isNameError = fullName.isBlank()
            },
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isNameError
        )
        if (isNameError) Text("Veuillez entrer votre nom", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(18.dp))

        // EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it.trim()
                isEmailError = email.isBlank()
            },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isEmailError
        )
        if (isEmailError) Text("Veuillez entrer un email valide", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(18.dp))

        // PHONE
        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it.trim()
                isPhoneError = phone.isBlank()
            },
            label = { Text("Phone number") },
            leadingIcon = { Icon(Icons.Default.Phone, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isPhoneError
        )
        if (isPhoneError) Text("Veuillez entrer votre numéro", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(18.dp))

        // PASSWORD
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordError = password.isBlank()
            },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isPasswordError
        )
        if (isPasswordError) Text("Veuillez entrer un mot de passe", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(32.dp))

        // BUTTON
        Button(
            onClick = {
                isNameError = fullName.isBlank()
                isEmailError = email.isBlank()
                isPhoneError = phone.isBlank()
                isPasswordError = password.isBlank()

                if (!isNameError && !isEmailError && !isPhoneError && !isPasswordError) {
                    scope.launch {
                        val result = repository.registerUser(email, password)
                        if (result.isSuccess) onSignUpSuccess()
                        else errorMessage = result.exceptionOrNull()?.message
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Create Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(id = R.string.signin_message))
            Text(
                stringResource(id = R.string.signin),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { onBackClick() }
            )
        }
    }
}
