package com.example.agis
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // 3 seconds timeout

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Splash screen delay using Handler
        Handler(Looper.getMainLooper()).postDelayed({
//            val sharedPreferences: SharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0)
//            val hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false)
//
//            val intent = if (hasLoggedIn) {
//                Intent(this@MainActivity, DashboardActivity::class.java)
//            } else {
//                Intent(this@MainActivity, LoginActivity::class.java)
//            }
            val intent = Intent(this@MainActivity, TabActivity::class.java)
                        startActivity (intent)
                        finish ()
        }, SPLASH_TIME_OUT)
    }
}
