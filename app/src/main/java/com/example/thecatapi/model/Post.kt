package com.example.thecatapi.model

data class Post (
    val id: String,
    val url: String,
    val subID: String,
    val width: Long,
    val height: Long,
    val originalFilename: String,
    val pending: Long,
    val approved: Long
)