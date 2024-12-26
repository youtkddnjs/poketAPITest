package ysw.test.mynewservice.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ysw.test.mynewservice.ui.theme.MyNewServiceTheme
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var testString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyNewServiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        okhttpTestButton = { okhttpTestButton() }
                    )
                }
            }
        }

        // Hilt로 주입된 testString 사용
        Log.d("HiltTest", testString)
    } //onCreate
} //MainActivity

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    okhttpTestButton: () -> Unit = {  }
) {
    Box (
        modifier = modifier
    ){
        Column(){
            Text(
                text = "Hello $name!",
                modifier = modifier
            )

            Button(
                onClick = okhttpTestButton
            ) {
                Text(
                    "okhttpTestButton"
                )
            }
        }
    } //Box
} //Greeting

fun okhttpTestButton(){
    val client = OkHttpClient()
    // 요청 생성
    val request = Request.Builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .build()

    client.newCall(request).enqueue(object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.d("okhttpTest", "onFailure : ${e.message}")
        } //onFailure

        override fun onResponse(call: Call, response: Response) {
            if(response.isSuccessful){
                response.body?.string()?.let {
                    Log.d("okhttpTest", it)
                }
            } else {
                Log.e("OkHttpTest", "Error: ${response.code}")
            }
        } //onResponse
    })
} //okhttpTestButton

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNewServiceTheme {
        Greeting("Android")
    }
}