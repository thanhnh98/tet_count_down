package com.thanh_nguyen.test_count_down.app.presentation.ui.main

import android.os.Bundle
import com.neovisionaries.ws.client.*
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.base.mvvm.activity.BaseActivity
import com.thanh_nguyen.test_count_down.databinding.ActivityTestSocketBinding
import com.thanh_nguyen.test_count_down.utils.WTF
import com.thanh_nguyen.test_count_down.utils.onClick
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TestSocket: BaseActivity<ActivityTestSocketBinding>() {
    override fun inflateLayout(): Int = R.layout.activity_test_socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btn.onClick {
            GlobalScope.launch {
                WebSocketFactory()
//                    .createSocket("ws://sap-tet-api.herokuapp.com/chat")
                    .createSocket("ws://192.168.0.97:8080/join/${binding.edtId.text.toString()}")
                    .addListener(object: WebSocketAdapter(){
                        override fun onConnected(
                            websocket: WebSocket?,
                            headers: MutableMap<String, MutableList<String>>?
                        ) {
                            super.onConnected(websocket, headers)
                            WTF("connected")
                        }

                        override fun onTextFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                            super.onTextFrame(websocket, frame)
                            WTF("FRAME: ${frame?.isTextFrame} -> ${frame?.payloadText}")
                        }
                    })
                    .connect()
            }
        }

    }

    suspend fun client() {

    }
}