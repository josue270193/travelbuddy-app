package com.app.josuelopez.travelbuddy.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.josuelopez.travelbuddy.data.local.entity.User
import com.app.josuelopez.travelbuddy.data.local.entity.UserFull
import com.app.josuelopez.travelbuddy.data.remote.dto.UserResponse

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users u")
    fun getUsersSync(): List<UserFull>

    @Transaction
    @Query("SELECT * FROM users u")
    fun getUsers(): LiveData<List<UserFull>>

    @Transaction
    @Query("SELECT * FROM users u WHERE u.email = :email")
    fun getUserByEmail(email: String): LiveData<UserFull>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()


    @Transaction
    fun insertUserFull(response: UserResponse) {
        deleteAll()
        val user = User(
            response.user._id,
            response.user.name,
            response.user.email,
            response.token!!
        )
        insert(user)
    }
}