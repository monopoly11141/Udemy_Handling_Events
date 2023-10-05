package com.example.udemy_handling_events

sealed class UIEvent {

    data class ShowMessage(val message: String) : UIEvent()

}
