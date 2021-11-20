package io.github.ch8n.jetquiz

import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import io.github.ch8n.jetquiz.data.model.Color
import io.github.ch8n.jetquiz.data.model.colors
import io.github.ch8n.jetquiz.databinding.ActivityMainBinding
import kotlin.properties.Delegates


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

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(R.raw.clock)
            .into(binding.imgCountdown)

        setupQuiz()
    }


    private var guessColorIndex: Int by Delegates.observable(0) { _, _, newColorIndex ->
        val containerImages = listOf<AppCompatImageView>(
            binding.imgStatus1,
            binding.imgStatus2,
            binding.imgStatus3,
            binding.imgStatus4,
        )

        if (newColorIndex > 3) {
            setupQuiz()
        } else {
            val image = containerImages.get(newColorIndex)
            image.setImageResource(R.drawable.current)
            image.visibility = View.VISIBLE
        }
    }


    private var score: Double by Delegates.observable(0.0) { _, _, newColorIndex ->
        binding.textScore.text = score.toString()
    }


    fun setupQuiz() = with(binding) {

        val quizColors = colors.shuffled().take(4)
        val buttonLabels = quizColors.shuffled()
        val containerColors = quizColors.shuffled()

        val buttons = listOf(
            binding.btnColor1,
            binding.btnColor2,
            binding.btnColor3,
            binding.btnColor4
        )

        val containers = listOf<CardView>(
            binding.containerA,
            binding.containerB,
            binding.containerC,
            binding.containerD,
        )

        val containerImages = listOf<AppCompatImageView>(
            binding.imgStatus1,
            binding.imgStatus2,
            binding.imgStatus3,
            binding.imgStatus4,
        )

        // apply button labels
        buttonLabels.forEachIndexed { index, color ->
            val button = buttons.get(index)
            button.text = color.name
        }

        // apply container colors
        containerColors.forEachIndexed { index, color ->
            val container = containers.get(index)
            container.setCardBackgroundColor(parseColor(color.value))
        }

        // hide all images
        containerImages.forEach {
            it.visibility = View.GONE
        }

        // set current guess to first image
        guessColorIndex = 0

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val newScore = calculateScore(
                    clickedButtonLabel = button.text.toString(),
                    containerColor = containerColors.get(guessColorIndex),
                    currentScore = score
                )
                val currentImage = containerImages.get(guessColorIndex)
                currentImage.setImageResource(
                    if (newScore > score) {
                        R.drawable.right
                    } else {
                        R.drawable.wrong
                    }
                )
                score = newScore
                guessColorIndex += 1
            }
        }

    }

    private fun calculateScore(
        clickedButtonLabel: String,
        containerColor: Color,
        currentScore: Double
    ): Double {
        return if (clickedButtonLabel == containerColor.name) {
            currentScore + 1.0
        } else {
            currentScore - 0.5
        }
    }

}