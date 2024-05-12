package com.weatherforecst.lernkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weatherforecst.lernkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

private lateinit var bunding:ActivityMainBinding
private val urlMain:String = "https://world-weather.ru/pogoda/russia/rostov_na_donu/?ysclid=lw2c5d5gej380639428"
private val classTable = "tabs tabs-db"
private val classDayWeek:String = "day-week"
private val classNumbersMonth:String = "numbers-month"
private val classMonth:String = "month"
private val classDayTemperature:String = "day-temperature"
private val classNightTemperature:String = "night-temperature"

lateinit var doc:Document

val statusRequest:Boolean = false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        bunding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bunding.root)
            main()
    }

    fun main():Unit = runBlocking(){
        launch (Dispatchers.IO) {
            web()
        val result = "doW"
        }
    }
    suspend fun web(){
        doc = Jsoup.connect(urlMain).get()
        Log.d("DocTextHtml", doc.html().toString())

        var mainNeam:Elements = doc.getElementsByClass(classTable)
        Log.d("tableText", mainNeam.html().toString())
    }

}