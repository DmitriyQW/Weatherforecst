package com.weatherforecst.lernkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(bunding.root)

        bunding.buttonDiscover.setOnClickListener {
            main()
        }
    }

    fun main():Unit = runBlocking(){
        launch (Dispatchers.IO) {
            web()
        val result = "Good"
        }
    }
    suspend fun web(){
        doc = Jsoup.connect(urlMain).get()
        Log.d("DocTextHtml", doc.html().toString())

        var mainTable:Elements = doc.getElementsByClass(classTable)
        Log.d("tableText0", mainTable.html())

        var dayWeeks:Elements = doc.getElementsByClass(classDayWeek)
        printLogElements("День недели",dayWeeks)

        var numbersMonths:Elements = doc.getElementsByClass(classNumbersMonth)
        printLogElements("День",numbersMonths)

        var months:Elements = doc.getElementsByClass(classMonth)
        printLogElements("Месяц",months)

        //Вывод погоды днём
        var temperatures:Elements = doc.getElementsByClass(classDayTemperature)
        printLogElements("Температура днём",temperatures)

        //Вывод погоды ночью
        var nightTemperaturs:Elements = doc.getElementsByClass(classNightTemperature)
        printLogElements("Температура ночью",nightTemperaturs)

        runOnUiThread {
            //Тестовый вывод
            //День недели
            bunding.textViewDayWeek.setText(getString(R.string.dayWeek)+" "+dayWeeks.get(0).text().lowercase())
            //День
            bunding.textViewNumbersMonths.setText(getString(R.string.numbers_months)+" "+numbersMonths.get(0).text())
            //Месяц
            bunding.textViewMonths.setText(getString(R.string.month)+" "+months.get(0).text())
            //Температура днём
            bunding.textViewTemperature.setText(getString(R.string.temperature_day)+" "+temperatures.get(0).text())
            //Температура ночью
            bunding.textViewNightTemperatur.setText(getString(R.string.night_temperatur)+" "+nightTemperaturs.get(0).text())
        }
    }
    //Функция для вывода в Logcat элементов
    fun printLogElements(neamTag:String,selectElements:Elements){
        for (i in 0..6){
            Log.d("$neamTag $i ","${selectElements.get(i).text()}")
        }
    }

}