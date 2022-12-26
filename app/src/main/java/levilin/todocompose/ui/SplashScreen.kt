package levilin.todocompose.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import levilin.todocompose.R
import levilin.todocompose.ui.theme.*
import levilin.todocompose.utility.ConstantValue

@Composable
fun SplashScreen(navigateToListScreen: () -> Unit) {
    var triggerAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (triggerAnimation) {
            SPLASH_SCREEN_ANIMATION_OFFSET_END
        } else {
            SPLASH_SCREEN_ANIMATION_OFFSET_START
        },
        animationSpec = tween(durationMillis = ConstantValue.SPLASH_SCREEN_ANIMATION_DURATION)
    )
    val alphaState by animateFloatAsState(
        targetValue = if (triggerAnimation) {
            ConstantValue.SPLASH_SCREEN_ANIMATION_ALPHA_END
        } else {
            ConstantValue.SPLASH_SCREEN_ANIMATION_ALPHA_START
        },
        animationSpec = tween(durationMillis = ConstantValue.SPLASH_SCREEN_ANIMATION_DURATION)
    )

    LaunchedEffect(key1 = true) {
        triggerAnimation = true
        delay(ConstantValue.SPLASH_SCREEN_DELAY.toLong())
        navigateToListScreen()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.splashScreenBackgroundColor), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(SPLASH_SCREEN_LOGO_SIZE).offset(y = offsetState).alpha(alphaState),
            painter = painterResource(id = getLogo()),
            contentDescription = stringResource(id = R.string.splash_screen_background_logo)
        )
    }
}

@Composable
fun getLogo(): Int {
    return if (isSystemInDarkTheme()) {
        R.drawable.todo_compose_dark
    } else {
        R.drawable.todo_compose
    }
}

@Composable
@Preview
fun SplashScreenLightPreview() {
    ToDoComposeTheme(darkTheme = false) {
        SplashScreen(navigateToListScreen = {})
    }
}

@Composable
@Preview
fun SplashScreenDarkPreview() {
    ToDoComposeTheme(darkTheme = true) {
        SplashScreen(navigateToListScreen = {})
    }
}