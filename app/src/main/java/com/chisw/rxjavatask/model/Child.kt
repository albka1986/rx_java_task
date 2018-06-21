package com.chisw.rxjavatask.model

/**
 * Created by Ponomarenko Oleh on 15.06.2018.
 */

data class Result(val title: Title, val url: Url, val author: Author) {
    data class Title(val value: String, val matchLevel: String, val matchdWords: Array<String>)
    data class Url(val value: String, val matchLevel: String, val matchdWords: Array<String>)
    data class Author(val value: String, val matchLevel: String, val matchdWords: Array<String>)
}

