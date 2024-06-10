package com.example.firebasedpa24.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.firebasedpa24.R
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private lateinit var apiKey: String
    private lateinit var initialPrompt: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_chat, container, false)
        val btChat: Button = view.findViewById(R.id.btChat)
        var etChat: EditText = view.findViewById(R.id.etChat)
        val tvResponse: TextView = view.findViewById(R.id.tvResponse)
        val db = FirebaseFirestore.getInstance()
        val markwon = Markwon.create(requireContext())
        activity?.title = "Chat de TurismoGo"

        fetchConfig { config ->
            apiKey = config["API_KEY"] as String
            initialPrompt = config["INITIAL_PROMPT"] as String

            val model = GenerativeModel(
                "gemini-1.5-flash",
                apiKey,
                generationConfig = generationConfig {
                    temperature = 1f
                    topK = 64
                    topP = 0.95f
                    maxOutputTokens = 8192
                    responseMimeType = "text/plain"
                },
            )
            //Initialize model with prompt in Firestore
            val chatHistory = listOf(
                content("user") {
                    text(initialPrompt)
                },
            )
            //TODO: Save chat history in database
            val chat = model.startChat(chatHistory)

            btChat.setOnClickListener {
                var chatMessage = etChat.text.toString()
                // Iniciar una nueva corrutina en el alcance de la vista
                lifecycleScope.launch {
                    val responseText = sendMessageAndGetResponse(chat, chatMessage)
                    markwon.setMarkdown(tvResponse, responseText)
                }
            }
        }
        return view
    }
    suspend fun sendMessageAndGetResponse(chat: Chat, message: String): String {
        val response = chat.sendMessage(message)
        return response.text ?: "No se recibió respuesta del modelo"
    }

    private fun fetchConfig(onConfigFetched: (Map<String, Any>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("config").document("DH2Xzo4Cgma1W6Wbvfvv")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    onConfigFetched(document.data!!)
                } else {
                    // Handle the case where the document does not exist
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors
            }
    }
}