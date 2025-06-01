package com.novanest.echotrail
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.novanest.FetchUserScreen
import com.novanest.echotrail.ui.theme.EchoTrailTheme
import com.novanest.echotrail_core.EchoTrailConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", "reqres-free-v1")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(FakeApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        EchoTrailConfig.isLoggingEnabled =BuildConfig.IS_DEBUG_BUILD
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EchoTrailTheme {
                FetchUserScreen(api)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EchoTrailTheme {
        Greeting("Android")
    }
}