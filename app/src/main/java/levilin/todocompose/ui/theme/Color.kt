package levilin.todocompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightBlue = Color(0xFFB6DFF3)
val Blue = Color(0xFF32A5DD)
val DarkBlue = Color(0xFF058ACA)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF5A5959)

val HighPriority = Color(0xFFFD3E44)
val MediumPriority = Color(0xFFFAA850)
val LowPriority = Color(0xFF4CAF50)
val NoPriority = Color(0xFFA1ADAC)


val Colors.splashScreenBackgroundColor: Color
    @Composable
    get() = if (isLight) LightGray else DarkGray

val Colors.topAppBarItemColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Blue else Color.Black

val Colors.floatingAddButtonColor: Color
    @Composable
    get() = if (isLight) Blue else DarkBlue

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) LightGray else DarkGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray