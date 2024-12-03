package com.treewalker.sigguardsms.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.treewalker.datastore.SendersProto.Senders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

// Define Serializer for the Proto DataStore
object SendersSerializer : Serializer<Senders> {
    override val defaultValue: Senders = Senders.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Senders {
        return Senders.parseFrom(input)
    }

    override suspend fun writeTo(t: Senders, output: OutputStream) {
        t.writeTo(output)
    }
}

// Extension function to create a DataStore instance
val Context.sendersDataStore: DataStore<Senders> by dataStore(
    fileName = "senders.pb",
    serializer = SendersSerializer
)

// Local Data Source class for managing senders data
class SendersLocalDataSource(private val context: Context) {

    private val dataStore: DataStore<Senders> = context.sendersDataStore

    // Function to add a sender
    suspend fun addSender(newSender: String) {
        dataStore.updateData { currentSenders ->
            currentSenders.toBuilder().addSender(newSender).build()
        }
    }

    // Function to get the list of senders
    fun getSenders(): Flow<List<String>> {
        return dataStore.data.map { it.senderList }
    }

    // Function to remove a specific sender
    suspend fun removeSender(senderToRemove: String) {
        dataStore.updateData { currentSenders ->
            val updatedSenders = currentSenders.senderList.filterNot { it == senderToRemove }
            currentSenders.toBuilder().clearSender().addAllSender(updatedSenders).build()
        }
    }

    // Function to clear the entire list of senders
    suspend fun clearSenders() {
        dataStore.updateData { currentSenders ->
            currentSenders.toBuilder().clearSender().build()
        }
    }
}
