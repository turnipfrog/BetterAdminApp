package com.example.betteradminapp.ui.screens

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteradminapp.BetterAdminBottomNavigationBar
import com.example.betteradminapp.BetterAdminTopAppBar
import com.example.betteradminapp.R
import com.example.betteradminapp.data.model.Message
import com.example.betteradminapp.ui.AppViewModelProvider
import com.example.betteradminapp.ui.navigation.NavigationDestination
import java.util.Date

object MessageDestination : NavigationDestination {
    override val route = "message"
    override val titleRes = R.string.nav_bar_messages
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    windowSize: WindowWidthSizeClass,
    navigateToMain: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMessage: () -> Unit,
    navigateToEvent: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateUp: () -> Unit,
    navigateToSendMessage: () -> Unit,
    setUnreadMessages: (Int) -> Unit,
    unreadMessages: Int,
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val messageUiState by viewModel.messageUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.fetchData()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BetterAdminTopAppBar(
                title = stringResource(MessageDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = navigateToSendMessage,
            ) {
                Icon(Icons.AutoMirrored.Filled.Message, "Floating action button.")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BetterAdminBottomNavigationBar(
                navigateToMain = navigateToMain,
                navigateToCourse = navigateToCourse,
                navigateToMessage = navigateToMessage,
                navigateToEvent = navigateToEvent,
                navigateToSettings = navigateToSettings,
                currentSelected = "message",
                unreadMessages = unreadMessages
            )
        },

    ) { innerPadding ->
        MessageBody(
            viewModel = viewModel,
            messageUiState = messageUiState,
            unreadMessagesReceived = { viewModel.unreadReceivedMessages(it) },
            setUnreadMessages = setUnreadMessages,
            modifier = Modifier
                .padding(innerPadding)

        )
    }
}

@Composable
fun MessageBody(
    viewModel: MessageViewModel,
    messageUiState: MessageUiState,
    unreadMessagesReceived: ((Int) -> Unit) -> Unit,
    setUnreadMessages: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ToggleScreenButtons(
            toggleScreen = { viewModel.setScreenIsReceivedMessages(it) },
            messageUiState = messageUiState,
            modifier = Modifier
        )
        MessageCardList(
            messageUiState = messageUiState,
            getTeacherNameFromEmail = { viewModel.getTeacherNameByEmail(it) },
            setMessageSeen = { viewModel.setMessageSeen(it) },
            unreadMessagesReceived = unreadMessagesReceived,
            setUnreadMessages = setUnreadMessages,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun ToggleScreenButtons(
    toggleScreen: (Boolean) -> Unit,
    messageUiState: MessageUiState,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)) {
        if (messageUiState.screenIsReceivedMessages) {
            OutlinedButton(
                onClick = { toggleScreen(false) },
                modifier = Modifier.weight(1f),
                shape = CutCornerShape(0.dp)
            ) {
                Text(text = stringResource(R.string.button_sent))

            }
            FilledTonalButton(
                onClick = { },
                modifier = Modifier.weight(1f),
                shape = CutCornerShape(0.dp)
            ) {
                Text(text = stringResource(R.string.button_received))
            }
        }
        else {
            FilledTonalButton(
                onClick = { },
                modifier = Modifier.weight(1f),
                shape = CutCornerShape(0.dp)
            ) {
                Text(text = stringResource(R.string.button_sent))

            }
            OutlinedButton(
                onClick = { toggleScreen(true) },
                modifier = Modifier.weight(1f),
                shape = CutCornerShape(0.dp)
            ) {
                Text(text = stringResource(R.string.button_received))
            }
        }
    }
}

@Composable
fun MessageCardList(
    messageUiState: MessageUiState,
    getTeacherNameFromEmail: (String) -> String,
    unreadMessagesReceived: ((Int) -> Unit) -> Unit,
    setUnreadMessages: (Int) -> Unit,
    setMessageSeen: (Message) -> Unit,
    modifier: Modifier = Modifier
) {
    val messageList = if (messageUiState.screenIsReceivedMessages) {
        messageUiState.messagesReceived
    }
    else {
        messageUiState.messagesSent
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = messageList, key = { it.id }) { message ->
            val fullName: String = if (messageUiState.screenIsReceivedMessages) {
                getTeacherNameFromEmail(message.senderEmail)
            }
            else {
                getTeacherNameFromEmail(message.receiverEmail)
            }
            // Dummycode to be replaced
            val faceImage = if (fullName == "Sarah Saxogpapir") R.drawable.saxogpapir_billede else R.drawable.klavermus_billede
            MessageCard(
                message = message,
                fullName = fullName,
                setMessageSeen = setMessageSeen,
                unreadMessagesReceived = unreadMessagesReceived,
                setUnreadMessages = setUnreadMessages,
                faceImage = faceImage
            )
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun MessageCard(
    message: Message,
    fullName: String,
    @DrawableRes faceImage: Int,
    unreadMessagesReceived: ((Int) -> Unit) -> Unit,
    setUnreadMessages: (Int) -> Unit,
    setMessageSeen: (Message) -> Unit,
    descriptionFontSize: TextUnit = MaterialTheme.typography.titleSmall.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 50,
    shape: Shape = RoundedCornerShape(4.dp),
    padding: Dp = 12.dp
) {
    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    val color: Color = if (message.isNew) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(faceImage),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(50.dp)
                        .height(50.dp)

                )
                Spacer(modifier = Modifier.padding(end = 5.dp))
                Column(modifier = Modifier.weight(6f)) {
                    Text(
                        modifier = Modifier,
                        text = fullName,
                        fontSize = descriptionFontSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier,
                        text = message.title,
                        fontSize = descriptionFontSize,
                        fontWeight = descriptionFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier.weight(3f),
                    text = message.timeSent.toString(),
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.2f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                        if (message.isNew) {
                            setMessageSeen(message)
                            unreadMessagesReceived(setUnreadMessages)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Column {
                    Text(
                        text = message.content,
                        fontSize = descriptionFontSize,
                        fontWeight = descriptionFontWeight,
                        maxLines = descriptionMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MessageCardPreview() {
    MessageCard(
        message = Message(
            id = 0,
            title = "Vigtig info",
            content = "Hej alle bla bla bla",
            timeSent = Date(2024, 6, 6, 12, 0, 0),
            receiverEmail = "test@test.dk",
            senderEmail = "Saksepigen@musik.dk",
            isNew = true
        ),
        fullName = "Søren Sølvfisk",
        setMessageSeen = {},
        setUnreadMessages = {},
        unreadMessagesReceived = {},
        faceImage = R.drawable.klavermus_billede
    )
}
