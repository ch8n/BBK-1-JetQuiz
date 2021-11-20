package io.github.ch8n.jetquiz

import android.graphics.Color.parseColor
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
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
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this)
            .load(R.raw.clock)
            .into(binding.imgCountdown)
        createGame()
    }

    private fun createGame() {
        startQuizTimer()
        setupQuiz()
    }

    private fun startQuizTimer() {
        val ticGap = 1000L /* 1 second in millis */
        val gameTime = 30 * ticGap /* 30 second Game */

        // cancel previous countdown timer
        countDownTimer?.cancel()

        // create new countdown timer
        countDownTimer = object : CountDownTimer(gameTime, ticGap) {
            override fun onTick(millisUntilFinished: Long) {
                // find the percentage of time is passed
                val percent = (millisUntilFinished * 100) / gameTime

                // apply status according to game percent left
                val countDownImage = when {
                    percent < 30 -> R.drawable.countdown_3
                    percent < 60 -> R.drawable.countdown_2
                    else -> R.drawable.countdown_1
                }
                binding.imgCountdownStatus.setImageResource(countDownImage)
                binding.progressCountdown.progress = percent.toInt()
            }

            override fun onFinish() {
                // trigger when game timer is over
                binding.imgCountdownStatus.setImageResource(R.drawable.game_over)
                score = 999.0
            }
        }

        // start countdown timer
        countDownTimer?.start()
    }


    private var guessColorIndex: Int by Delegates.observable(0) { _, _, newColorIndex ->

        val containerImages = listOf<AppCompatImageView>(
            binding.imgStatus1,
            binding.imgStatus2,
            binding.imgStatus3,
            binding.imgStatus4,
        )

        // if new value is 4 means quiz needs reset
        if (newColorIndex > 3) {
            setupQuiz()
        } else {
            // else quiz need to advance next step
            val image = containerImages.get(newColorIndex)
            image.setImageResource(R.drawable.current)
            image.visibility = View.VISIBLE
        }
    }

    private var maxScore = 0.0
    private var score: Double by Delegates.observable(0.0) { _, oldScore, newScore ->
        // if timer is out or Score is less than zero end game
        if (newScore == 999.0 || newScore < 0) {
            gameOver(maxScore)
        } else {
            // record max score
            if (newScore > maxScore) {
                maxScore = newScore
            }
            binding.textScore.text = newScore.toString()
        }
    }

    fun gameOver(finalScore: Double) {
        score = 0.0
        maxScore = 0.0
        val alertDialogSetup = AlertDialog.Builder(this)
        alertDialogSetup.setTitle("Game Over!")
        alertDialogSetup.setMessage("Thanks for Playing! Your Score was : $finalScore")
        alertDialogSetup.setCancelable(false)
        alertDialogSetup.setPositiveButton(
            "Retry?"
        ) { dialog, id ->
            createGame()
            dialog.cancel()
        }

        alertDialogSetup.setNegativeButton(
            "No"
        ) { dialog, id ->
            dialog.cancel()
            finish()
        }
        val alertDialog = alertDialogSetup.create()
        alertDialog.show()
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

        // add click action to the buttons
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // evaluate new score
                val newScore = calculateScore(
                    clickedButtonLabel = button.text.toString(),
                    containerColor = containerColors.get(guessColorIndex),
                    currentScore = score
                )
                // evaluate right or wrong and update image
                val currentImage = containerImages.get(guessColorIndex)
                currentImage.setImageResource(
                    if (newScore > score) {
                        R.drawable.right
                    } else {
                        R.drawable.wrong
                    }
                )
                // update score
                score = newScore
                // advance quiz
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