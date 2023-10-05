package com.example.udemy_handling_events

import android.os.Bundle
import android.provider.Contacts.Intents.UI
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.udemy_handling_events.ui.theme.Udemy_Handling_EventsTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Udemy_Handling_EventsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    counterViewModel: CounterViewModel = viewModel()
) {

    val screenState = counterViewModel.screenState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        counterViewModel.uiEventFlow.collectLatest {event ->
            when(event) {
                is UIEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = screenState.displayingResult,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.DarkGray,
                modifier = modifier
                    .fillMaxWidth()
            )
            
            OutlinedTextField(
                value = screenState.inputValue,
                onValueChange = {
                    counterViewModel.onEvent(CounterEvent.ValueEntered(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(
                    color = Color.LightGray,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                ),
                label = { Text(
                    text = "New Count"
                )},
                modifier = modifier
                    .fillMaxWidth()
            )

            if(screenState.isBtnCountVisible) {
                Button(
                    onClick = {
                        counterViewModel.onEvent(CounterEvent.BtnCountClicked)
                    },
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Count",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {

            }

            Button(
                onClick = {
                    counterViewModel.onEvent(CounterEvent.BtnResetClicked)
                },
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Reset",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}