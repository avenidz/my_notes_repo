package com.example.testapp.todoData

import android.content.Context
import androidx.room.*

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) var tId: Int,
    @ColumnInfo(name = "todoTitle") var title: String = "",
    @ColumnInfo(name = "todoCheck") var check: Boolean = false
) {
    var detail: String = ""
}


@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDao

    companion object {
        private val databaseName = "todoDatabase"
        private var INSTANCE: TodoListDatabase? = null

        fun getInstance(context: Context): TodoListDatabase {
            var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoListDatabase::class.java, databaseName
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }

            return instance
        }
    }
}