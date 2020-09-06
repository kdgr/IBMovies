package com.kaandogruer.ibmovies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaandogruer.ibmovies.data.local.entity.Movie

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie ORDER BY id DESC")
    val movieList: LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%'")
    fun searchMovie(query: String): LiveData<List<Movie>>

    @Query("DELETE FROM movie")
     fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieList: List<Movie>)

    @Query("SELECT * FROM movie WHERE id=:movieId")
    fun getMovieById(movieId: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movieEntities: List<Movie>)

    @Delete
    fun deleteMovies(movieEntities : List<Movie>)

    @Update
    fun updateMovie(movieEntity: Movie)

    @Delete
    fun deleteMovie(movieEntity: Movie)

}
