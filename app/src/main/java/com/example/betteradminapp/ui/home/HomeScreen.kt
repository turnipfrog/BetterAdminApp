package com.example.betteradminapp.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.ui.AppViewModelProvider
import com.example.betteradminapp.ui.navigation.NavigationDestination


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    onDonePressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        HomeBody(
            windowSize = windowSize,
            viewModel = viewModel,
            onDonePressed = onDonePressed,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    windowSize: WindowWidthSizeClass,
    viewModel: HomeViewModel,
    onDonePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    LoginFields(
        windowSize = windowSize,
        viewModel = viewModel,
        onDonePressed = onDonePressed
    )
}

@Composable
fun LoginFields(
    windowSize: WindowWidthSizeClass,
    viewModel: HomeViewModel,
    onDonePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val loginFailedStr = stringResource(id = R.string.login_unsuccessful)
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo shown if phone is not tilted horizontally
        if (windowSize != WindowWidthSizeClass.Expanded)
        {
            Image(
                painter = painterResource(id = R.drawable.loginlogo),
                contentDescription = stringResource(R.string.painterdesc_loginlogo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 50.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 40.sp,
            modifier = modifier.padding(bottom = 20.dp)
        )
        // Email TextField
        TextField(
            value = viewModel.emailAttempt,
            onValueChange = { viewModel.updateEmailAttempt(it) },
            label = { Text(stringResource(id = R.string.email)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.email)) },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next)
                }
            )
        )
        // Password TextField
        TextField(
            value = viewModel.passwordAttempt,
            onValueChange = { viewModel.updatePasswordAttempt(it) },
            label = { Text(stringResource(id = R.string.password)) },
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.password)) },
            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
            trailingIcon = {
                val image = if (viewModel.passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localized description for accessibility services
                val description = if (viewModel.passwordVisible)
                    stringResource(R.string.hide_password)
                else
                    stringResource(R.string.show_password)

                // Toggle button to hide or display password
                IconButton(onClick = { viewModel.togglePasswordVisible() }) {
                    Icon(imageVector = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (viewModel.validateLogin(viewModel.emailAttempt, viewModel.passwordAttempt))
                    {
                        onDonePressed()
                        viewModel.storeCredentialsLocally(viewModel.emailAttempt, viewModel.passwordAttempt)
                        //viewModel.clearLoginAttempt()
                    }
                    else {
                        viewModel.storeCredentialsLocally("", "")
                        viewModel.clearLoginAttempt()
                        Toast.makeText(
                            context, loginFailedStr,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        )
        // Practical for use, but destroys the design of the UI?
        OutlinedButton(onClick = onDonePressed, modifier = Modifier.padding(top = 20.dp)) {
            Text(stringResource(id = R.string.login))
        }
    }
}