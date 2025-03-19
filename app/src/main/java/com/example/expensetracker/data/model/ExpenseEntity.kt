package com.example.expensetracker.data.model

import androidx.room.Entity

@Entity(tableName = "expenses_table")
data class ExpenseEntity(
    val id: Int,
    val title: String,
    val amount: Double,
    val date: String,
    val category: String,
    val type: String
)
