package com.app.travelbuddy.domain

class TipModel(var title: String, var description: String, var color: String?) {
    constructor(title: String, description: String) : this(title, description, null)
}
