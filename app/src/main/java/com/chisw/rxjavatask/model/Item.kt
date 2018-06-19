package com.chisw.rxjavatask.model

/**
 * Created by Ponomarenko Oleh on 15.06.2018.
 */
class Item(var hits: Array<Story>) {

    data class nbHits(val nbHits: Int)
    data class page(val page: Int)
    data class nbPages(val nbPages: Int)
    data class hitsPerPage(val hitsPerPage: Int)
    data class processingTimeMS(val processingTimeMS: Int)
    data class exhaustiveNbHits(val exhaustiveNbHits: Boolean)
    data class query(val query: String)
    data class params(val params: String)

}