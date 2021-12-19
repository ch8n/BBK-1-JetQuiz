package io.github.ch8n.jetquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ch8n.jetquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.clearFocus()

        binding.btnStart.setOnClickListener {
            val playerName = binding.editName.editText?.text

            if (playerName.isNullOrEmpty()) {
                binding.editName.error = "Please enter your name..."
                return@setOnClickListener
            }

            binding.editName.error = null
            binding.root.clearFocus()

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("player_name", playerName)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getHighScore()
    }


    private fun getHighScore() = with(binding) {
        val sharedPref = getSharedPreferences("jetQuizPref",Context.MODE_PRIVATE)
        val nameHighScore1 = sharedPref.getString("key_score_1_name", "???") ?: "???"
        val nameHighScore2 = sharedPref.getString("key_score_2_name", "???") ?: "???"
        val nameHighScore3 = sharedPref.getString("key_score_3_name", "???") ?: "???"

        val highScore1 = sharedPref.getString("key_score_1_value", "???") ?: "???"
        val highScore2 = sharedPref.getString("key_score_2_value", "???") ?: "???"
        val highScore3 = sharedPref.getString("key_score_3_value", "???") ?: "???"

        textScore1Name.text = nameHighScore1
        textScore2Name.text = nameHighScore2
        textScore3Name.text = nameHighScore3

        textScore1Value.text = highScore1
        textScore2Value.text = highScore2
        textScore3Value.text = highScore3
    }
}