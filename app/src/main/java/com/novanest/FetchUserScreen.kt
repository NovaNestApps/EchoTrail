package com.novanest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.novanest.echotrail.FakeApiService
import com.novanest.echotrail.User
import com.novanest.echotrail.UserResponse
import com.novanest.echotrail_core.logExecution
import com.novanest.echotrail_core.logExecutionSuspend
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun FetchUserScreen(api: FakeApiService) {
    val coroutineScope = rememberCoroutineScope()
    var user by remember { mutableStateOf<User?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(onClick = {
                coroutineScope.launch {
                    loading = true
                    error = null
                    user = null

                    val response: Response<UserResponse> = logExecutionSuspend("FakeApiCall", input = "GET /users/2") {
                        api.getUser()
                    }

                    loading = false
                    if (response.isSuccessful) {
                        user = response.body()?.data
                    } else {
                        error = "Error: ${response.code()}"
                    }
                }
            }) {
                Text("Fetch User")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                loading -> CircularProgressIndicator()
                user != null -> {
                    Text("Name: ${user!!.first_name} ${user!!.last_name}")
                    Text("Email: ${user!!.email}")
                }
                error != null -> Text(error!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
