package com.weatherforecst.lernkotlin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
private val classDayWeek:String = "day-week"
private val classNumbersMonth:String = "numbers-month"
private val classMonth:String = "month"
private val classDayTemperature:String = "day-temperature"
private val classNightTemperature:String = "night-temperature"
private val forecastForTheWeek = MutableList<Forecast?>(7){null}
private var slider_index = -1
lateinit var doc:Document
private var statusDownloadDoc:Boolean = false

val statusRequest:Boolean = false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Включение тёмной темы по умолчанию
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        bunding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bunding.root)

        if (isOnline(this) == true){
            main()
            statusDownloadDoc = true
            next()
        }else{
            makeToast("Подключите интернет !")
        }

        bunding.buttonNext.setOnClickListener {
            if (isOnline(this) == false){
                makeToast("Подключите интернет !")
            }else{
                if (statusDownloadDoc == false){
                    main()
                    statusDownloadDoc = true
                }
                next()
            }
        }
        bunding.buttonBack.setOnClickListener {
            if (isOnline(this) == false){
                makeToast("Подключите интернет !")
            }else {
                if (statusDownloadDoc == false){
                    main()
                    statusDownloadDoc = true
                }
                back()
            }
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

        //Сбор прогноза на неделю
        creatingForecast(forecastForTheWeek,dayWeeks,numbersMonths,months,temperatures,nightTemperaturs)

    }
    //Функция для вывода в Logcat элементов
    fun printLogElements(neamTag:String,selectElements:Elements){
        for (i in 0..6){
            Log.d("$neamTag $i ","${selectElements.get(i).text()}")
        }
    }
    fun creatingForecast(inputList:MutableList<Forecast?>, dayWeeks:Elements
                         ,numbersMonths:Elements,months:Elements
                         ,temperatures:Elements,ightTemperaturs:Elements){
        for (i in 0..6){
            inputList[i] = Forecast(dayWeeks.get(i).text(),numbersMonths.get(i).text().toInt(),
                months.get(i).text(),temperatures.get(i).text(),numbersMonths.get(i).text())
        }
    }
    fun showListForexast(inputList:List<Forecast?>){
        bunding.textViewDayWeek.setText(getString(R.string.dayWeek) + " " + inputList[slider_index]?.dayWeek?.toLowerCase())
        //День
        bunding.textViewNumbersMonths.setText(getString(R.string.numbers_months) + " " + inputList[slider_index]?.numbersMonths.toString())
        //Месяц
        bunding.textViewMonths.setText(getString(R.string.month) + " " + inputList[slider_index]?.months)
        //Температура днём
        bunding.textViewTemperature.setText(getString(R.string.temperature_day) + " " + inputList[slider_index]?.temperatures)
        //Температура ночью
        bunding.textViewNightTemperatur.setText(getString(R.string.night_temperatur) + " " + inputList[slider_index]?.nightTemperaturs)
    }
    fun next() {
        if (slider_index < 6) {
            slider_index += 1
        } else {
            slider_index = 0
        }
        showListForexast(forecastForTheWeek)
    }

    fun back() {
        if (slider_index > 0) {
            slider_index -= 1
        } else {
            slider_index = 6
        }
        showListForexast(forecastForTheWeek)
    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    fun makeToast(text:String){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show()
    }
}