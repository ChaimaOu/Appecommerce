package com.example.appecommerce.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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

@Composable
fun SignUpPage(
    onSignUpSuccess: () -> Unit,
    onBackClick: () -> Unit
) {

    // Variables NULLABLES
    var fullName by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf<String?>(null) }
    var phone by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf<String?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }

    // Erreurs
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "<",
            fontSize = 26.sp,
            modifier = Modifier
                .clickable { onBackClick() }
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Create Account",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // NAME
        OutlinedTextField(
            value = fullName ?: "",
            onValueChange = {
                fullName = it
                isNameError = it.isBlank()
            },
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isNameError
        )
        if (isNameError) {
            Text(
                text = "Veuillez entrer votre nom",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // EMAIL
        OutlinedTextField(
            value = email ?: "",
            onValueChange = {
                email = it
                isEmailError = it.isBlank()
            },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isEmailError
        )
        if (isEmailError) {
            Text(
                text = "Veuillez entrer un email",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // PHONE
        OutlinedTextField(
            value = phone ?: "",
            onValueChange = {
                phone = it
                isPhoneError = it.isBlank()
            },
            label = { Text("Phone number") },
            leadingIcon = { Icon(Icons.Default.Phone, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isPhoneError
        )
        if (isPhoneError) {
            Text(
                text = "Veuillez entrer votre numéro",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // PASSWORD
        OutlinedTextField(
            value = password ?: "",
            onValueChange = {
                password = it
                isPasswordError = it.isBlank()
            },
            label = { Text("password") },
            leadingIcon = { Icon(Icons.Default.Visibility, null) },
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isPasswordError
        )
        if (isPasswordError) {
            Text(
                text = "Veuillez entrer un mot de passe",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // BUTTON
        Button(
            onClick = {

                // Vérification AVANT navigation
                isNameError = fullName.isNullOrBlank()
                isEmailError = email.isNullOrBlank()
                isPhoneError = phone.isNullOrBlank()
                isPasswordError = password.isNullOrBlank()

                // Si tout est OK → navigation
                if (!isNameError && !isEmailError && !isPhoneError && !isPasswordError) {
                    onSignUpSuccess()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Create Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.signin_message))
            Text(
                text = stringResource(id = R.string.signin),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { onBackClick() }
            )
        }
    }
}
