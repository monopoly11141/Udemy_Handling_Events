package com.example.udemy_handling_events

sealed class CounterEvent{
    data class ValueEntered(val value : String) : CounterEvent()
    object BtnCountClicked : CounterEvent()
    object BtnResetClicked : CounterEvent()
}
