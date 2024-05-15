package com.example.betteradminapp.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.ui.AppViewModelProvider
import com.example.betteradminapp.ui.navigation.NavigationDestination

object SendMessageDestination : NavigationDestination {
    override val route = "sendmessage"
    override val titleRes = R.string.new_message
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageScreen(
    windowSize: WindowWidthSizeClass,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SendMessageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val sendMessageUiState by viewModel.sendMessageUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(SendMessageDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    when (viewModel.validateMessage()) {
                        MessageValidity.VALID -> {
                            viewModel.sendMessage()
                            Toast.makeText(
                                context,
                                context.resources.getString(R.string.message_sent),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        MessageValidity.RECEIVER_ISSUE ->
                            Toast.makeText(
                                context,
                                context.resources.getString(R.string.receiver_missing),
                                Toast.LENGTH_SHORT
                            ).show()

                        MessageValidity.TITLE_ISSUE ->
                            Toast.makeText(
                                context,
                                context.resources.getString(R.string.title_missing),
                                Toast.LENGTH_SHORT
                            ).show()

                        MessageValidity.CONTENT_ISSUE ->
                            Toast.makeText(
                                context,
                                context.resources.getString(R.string.content_missing),
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, "Floating action button.")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        SendMessageBody(
            setReceiverEmail = { viewModel.setReceiverEmail(it) },
            setMessageTitle = { viewModel.setMessageTitle(it) },
            setMessageContent = { viewModel.setMessageContent(it) },
            sendMessageUiState = sendMessageUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun SendMessageBody(
    setReceiverEmail: (String) -> Unit,
    setMessageTitle: (String) -> Unit,
    setMessageContent: (String) -> Unit,
    sendMessageUiState: SendMessageUiState,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        AutoComplete(
            focusManager = focusManager,
            setReceiverEmail = setReceiverEmail,
            autoCompleteList = sendMessageUiState.teacherEmails
        )
        TitleTextField(
            focusManager = focusManager,
            setStringFunc = setMessageTitle
        )
        ContentTextField(
            focusManager = focusManager,
            setStringFunc = setMessageContent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoComplete(
    focusManager: FocusManager,
    setReceiverEmail: (String) -> Unit,
    autoCompleteList: List<String>,
    modifier: Modifier = Modifier
) {
    var category by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // Category Field
    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp),
            text = stringResource(id = R.string.receiver),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = category,
                    onValueChange = {
                        category = it
                        setReceiverEmail(it)
                        expanded = true
                    },
                    placeholder = { Text(stringResource(id = R.string.receivers_email)) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(
                                focusDirection = FocusDirection.Next)
                        }
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                    ) {

                        if (category.isNotEmpty()) {
                            items(
                                autoCompleteList.filter {
                                    it.lowercase()
                                        .contains(category.lowercase()) || it.lowercase()
                                        .contains("others")
                                }.sorted()
                            ) {
                                ItemsCategory(title = it) { title ->
                                    category = title
                                    setReceiverEmail(it)
                                    expanded = false
                                }
                            }
                        } else {
                            items(
                                autoCompleteList.sorted()
                            ) {
                                ItemsCategory(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemsCategory(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTextField(
    focusManager: FocusManager,
    setStringFunc: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var category by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {
        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp),
            text = stringResource(id = R.string.title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightTextFields)
                .border(
                    width = 1.8.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(15.dp)
                )
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            value = category,
            onValueChange = {
                category = it
                setStringFunc(it)
            },
            placeholder = { Text(stringResource(id = R.string.type_title)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next)
                }
            ),
            singleLine = true,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTextField(
    focusManager: FocusManager,
    setStringFunc: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var category by remember {
        mutableStateOf("")
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)) {
        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp),
            text = stringResource(id = R.string.content),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(
                    width = 1.8.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(15.dp)
                )
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            value = category,
            onValueChange = {
                category = it
                setStringFunc(it)
            },
            placeholder = { Text(stringResource(id = R.string.type_content)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next)
                }
            ),
            singleLine = false,
        )
    }
}