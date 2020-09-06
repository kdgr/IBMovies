package com.kaandogruer.ibmovies.data.remote.model.datamodels.requestmodels

class TopRatedMoviesRequestModel {
    var page: Int? = null
    var apiKey: String? = null
    var query: String? = null

    constructor(page: Int?, apiKey: String?,query: String?) {
        this.page = page
        this.apiKey = apiKey
        this.query = query
    }
}
