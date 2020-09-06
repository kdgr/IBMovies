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

package com.kaandogruer.ibmovies.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName
import com.kaandogruer.ibmovies.data.local.RoomConstants
import java.io.Serializable

@Entity(
    indices = [
        Index(RoomConstants.MOVIE_ID)],
    primaryKeys = [RoomConstants.MOVIE_ID]
)
data class Movie(
    val id: Int,
    @field:SerializedName(RoomConstants.MOVIE_TITLE)
    val title: String,
    @field:SerializedName(RoomConstants.MOVIE_POSTER_PATH)
    val posterPath: String,
    @field:SerializedName(RoomConstants.MOVIE_BG_PATH)
    val backgroundPath: String,
    @field:SerializedName(RoomConstants.MOVIE_OVERVIEW)
    val overview: String,
    @field:SerializedName(RoomConstants.MOVIE_VOTE_AVERAGE)
    val voteAverage: String

):Serializable {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500/$posterPath"
    }
    fun getBackgroundUrl(): String {
        return "https://image.tmdb.org/t/p/w500/$backgroundPath"
    }
    companion object {
        const val UNKNOWN_ID = -1
    }
}
