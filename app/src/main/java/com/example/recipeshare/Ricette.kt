package com.example.recipeshare


var arrPrimi = mutableListOf<Ricette>()

data class Ricette(val image : Int? = 0,
                   val titolo : String? = "",
                   val categoria : String? = "",
                   val descrizione : String? = "",
                   val valutazione : Int? = 0,
                   val count : Int? = 0,
                   val id : Int? = 0)