package com.weatherforecst.lernkotlin

class Forecast {
    private  var dayWeek:String
    private  var numbersMonths:Int
    private  var months:String
    private  var temperatures:String
    private  var nightTemperaturs:String

    constructor(
        dayWeek: String,
        numbersMonths: Int,
        months: String,
        temperatures: String,
        nightTemperaturs: String
    ) {
        this.dayWeek = dayWeek
        this.numbersMonths = numbersMonths
        this.months = months
        this.temperatures = temperatures
        this.nightTemperaturs = nightTemperaturs
    }

    override fun toString(): String {
        return "Прогноз погоды : \nДень недели = $dayWeek \nДень = $numbersMonths \nМесяц = $months \nТемпература днём = $temperatures \nТемпература ночью : $nightTemperaturs"
    }

}