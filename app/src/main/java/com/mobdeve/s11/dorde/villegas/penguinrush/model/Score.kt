package com.mobdeve.s11.dorde.villegas.penguinrush.model

class Score(
    var username: String,
    var score: Int,
){
    constructor() : this ("", -1)

    constructor( score: Int)
            :this("", score)
}