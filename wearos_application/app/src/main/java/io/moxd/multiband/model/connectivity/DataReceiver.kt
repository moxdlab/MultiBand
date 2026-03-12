package io.moxd.multiband.model.connectivity

import kotlinx.coroutines.flow.Flow

interface DataReceiver {
    fun receiveData(): Flow<List<Int>>
}