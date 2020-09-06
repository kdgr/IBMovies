/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaandogruer.ibmovies.data.remote.model.datamodels.responsemodels

import com.google.gson.annotations.SerializedName
import com.kaandogruer.ibmovies.data.local.entity.Movie

data class TopRatedMovieList(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("total_results")
    val totalResults: String,
    @field:SerializedName("total_pages")
    val totalPages: String,
    @field:SerializedName("results")
    val results: List<Movie>
)
