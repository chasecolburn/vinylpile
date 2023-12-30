package com.mindtwisted.vinylpile

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mindtwisted.vinylpile.databinding.ActivityMainBinding
import com.mindtwisted.vinylpile.helper.QueryHelper
import com.mindtwisted.vinylpile.model.Record
import com.mindtwisted.vinylpile.ui.record.RecordViewModel
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_records, R.id.navigation_collections, R.id.navigation_other
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()

//        val records = mutableListOf<Record>()
//        val firstRecord = Record()
//        firstRecord.name = "Test"
//        firstRecord.artist = "First artist"
//        QueryHelper.saveRecord(this, firstRecord)
//
//
//        val secondRecord = Record()
//        secondRecord.name = "Test again"
//        secondRecord.artist = "Second artist"
//        QueryHelper.saveRecord(this, firstRecord)
//
//        val recordViewModel =
//            ViewModelProvider(this)[RecordViewModel::class.java]
//
//        recordViewModel.recordList.value = records;
    }

    fun fetchAlbumResults() {
        val url = URL("https://api.deezer.com/search?q=")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET" // or "POST"
        conn.doOutput = true
        val postData = "foo1=bar1&foo2=bar2"
        DataOutputStream(conn.outputStream).use { dos ->
            dos.writeBytes(postData)
        }

        BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                println(line)
            }
        }
    }
}