package com.dennydev.spotyrex.utils

fun formatDuration(durationInMs: Double):String{
    val minutes = Math.floor((durationInMs/1000) / (60)).toInt()
    val seconds = Math.floor((durationInMs/1000) % (60)).toInt();
    val mm = if(minutes < 10) "0${minutes}" else minutes.toString();
    val ss = if(seconds < 10) "0${seconds}" else seconds.toString();
    return "${mm}:${ss}";
}