package levilin.todocompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import levilin.todocompose.navigation.SetupNavigation
import levilin.todocompose.ui.theme.ToDoComposeTheme
import levilin.todocompose.viewmodel.SharedViewModel

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                navHostController = rememberNavController()
                SetupNavigation(navHostController = navHostController, sharedViewModel = sharedViewModel)
            }
        }
    }
}