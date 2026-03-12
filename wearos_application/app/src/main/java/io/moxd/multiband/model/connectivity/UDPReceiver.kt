package io.moxd.multiband.model.connectivity

import io.moxd.multiband.udpSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.isActive
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.ByteBuffer
import java.nio.ByteOrder

class UDPReceiver : DataReceiver {
    override fun receiveData(): Flow<List<Int>> = flow {
        val receivingBuffer = ByteArray(1024)
        while (currentCoroutineContext().isActive) {
            try {
                if (udpSocket.isClosed) connect()
                val packet = DatagramPacket(receivingBuffer, receivingBuffer.size)
                udpSocket.receive(packet)

                val buffer = ByteBuffer.wrap(packet.data).order(ByteOrder.LITTLE_ENDIAN)

                val receivedData = mutableListOf<Int>()
                var nextData = 0
                while(nextData != -1) {
                    nextData = buffer.getInt()
                    if(nextData != -1)
                        receivedData.add(nextData)
                }

                emit(receivedData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }.flowOn(Dispatchers.IO)
        .onCompletion { udpSocket.close() }
        .cancellable()

    private fun connect() {
        udpSocket = DatagramSocket(44444)
    }
}