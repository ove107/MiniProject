package com.example.gallerytry


class ImageModel {
    val downloadURL: String
    val Timestamp: String
    val CategoryName :String

    constructor(downloadURL: String, Timestamp: String,CategoryName :String) {
        this.downloadURL = downloadURL
        this.Timestamp = Timestamp
        this.CategoryName = CategoryName
    }
}