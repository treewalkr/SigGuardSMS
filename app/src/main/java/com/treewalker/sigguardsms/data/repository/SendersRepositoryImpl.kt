package com.treewalker.sigguardsms.data.repository

import com.treewalker.sigguardsms.data.local.datastore.SendersLocalDataSource
import com.treewalker.sigguardsms.domain.repository.SendersRepository
import kotlinx.coroutines.flow.Flow

class SendersRepositoryImpl(
    private val sendersLocalDataSource: SendersLocalDataSource
) : SendersRepository {

    override suspend fun addSender(sender: String) {
        sendersLocalDataSource.addSender(sender)
    }

    override fun getSenders(): Flow<List<String>> {
        return sendersLocalDataSource.getSenders()
    }

    override suspend fun removeSender(sender: String) {
        sendersLocalDataSource.removeSender(sender)
    }

    override suspend fun clearSenders() {
        sendersLocalDataSource.clearSenders()
    }
}
