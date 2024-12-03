package com.treewalker.sigguardsms.domain.repository

import kotlinx.coroutines.flow.Flow

interface SendersRepository {
    suspend fun addSender(sender: String)
    suspend fun removeSender(sender: String)
    suspend fun clearSenders()
    fun getSenders(): Flow<List<String>>
}
