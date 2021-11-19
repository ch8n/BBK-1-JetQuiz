package io.github.ch8n.jetquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


/***
 * JetQuiz
 * Game -> Name the Colour Correctly and Win Score
 * Specs :
 * -> Grid of 4 Color
 * -> Grid of 4 Color Names
 * -> Select right name of Color for Each Grid item
 * -> Correct Guess +1 and Wrong Guess -1
 * -> Score reaches 0 -> End of Game -> retry?
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}