package com.example.gallerytry.Model

import android.net.Uri
import com.google.android.gms.tasks.Task

class AddToTimeline(val url: Task<Uri>, val timestamp:Long) {
}