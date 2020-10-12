package com.app.travelbuddy.domain

class NplResponse {
    var sentiment: Array<NplSentiment>? = null
    var sentimentScore: String? = null
}

class NplSentiment {
    var confidenceScores: NplSentimentScore? = null
}

class NplSentimentScore {
    var positive: String? = null
    var neutral: String? = null
    var negative: String? = null
}
