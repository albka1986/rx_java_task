package com.chisw.rxjavatask.model

/**
 * Created by Ponomarenko Oleh on 15.06.2018.
 */
data class Story(
        val objectID: Int,
        val create_at: String,
        val title: String,
        val url: String,
        val author: String,
        val points: Int,
        val story_text: String,
        val comment_text: String,
        val num_comments: Int,
        val story_id: Int,
        val story_title: String,
        val story_url: String,
        val parent_id: Int,
        val created_at_i: String,
        val _tags: Array<String>,
        val _highlightResult: Result)

