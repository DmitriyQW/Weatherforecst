package com.weatherforecst.lernkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weatherforecst.lernkotlin.databinding.ActivityMainBinding

private lateinit var bunding:ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        bunding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bunding.root)
    }
}