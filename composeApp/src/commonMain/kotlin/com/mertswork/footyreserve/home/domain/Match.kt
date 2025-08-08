package com.mertswork.footyreserve.home.domain

import footyreserve.composeapp.generated.resources.Res

data class Match(
    val id :String,
    val adminId : String,
    val teamName : String,
    val spotsTaken : Int,
    val totalSpots : Int,
    val moneyCollected : Double?,
    val totalCost : Double,
    val stadium : String,
    val playersId : List<String>?,
    val matchDate : String,
    val matchTime : String
)
