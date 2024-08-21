package com.codewithfk.expensetracker.android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codewithfk.expensetracker.android.data.dao.ExpenseDao
import com.codewithfk.expensetracker.android.data.model.ExpenseEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = false)
@Singleton
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_database"

        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getInstance(@ApplicationContext context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ExpenseDatabaseCallback(
        private val scope: CoroutineScope,
        private val daoProvider: Provider<ExpenseDao>
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Initialize with basic data
            scope.launch(Dispatchers.IO) {
                daoProvider.get().let { dao ->
                    dao.insertExpense(
                        ExpenseEntity(
                            id = null,
                            title = "Salary",
                            amount = 5000.40,
                            date = "2021-08-01",
                            category = "Salary",
                            type = "Income"
                        )
                    )
                    dao.insertExpense(
                        ExpenseEntity(
                            id = null,
                            title = "Paypal",
                            amount = 2000.50,
                            date = "2021-08-01",
                            category = "Paypal",
                            type = "Income"
                        )
                    )
                    dao.insertExpense(
                        ExpenseEntity(
                            id = null,
                            title = "Netflix",
                            amount = 100.43,
                            date = "2021-08-01",
                            category = "Netflix",
                            type = "Expense"
                        )
                    )
                    dao.insertExpense(
                        ExpenseEntity(
                            id = null,
                            title = "Starbucks",
                            amount = 400.56,
                            date = "2021-08-02",
                            category = "Starbucks",
                            type = "Income"
                        )
                    )
                }
            }
        }
    }
}
