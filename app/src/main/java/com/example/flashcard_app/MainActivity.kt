package com.example.flashcard_app

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var flipButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var deleteButton: Button
    private lateinit var addCardButton: Button
    private lateinit var currentflashcard: Flashcard
    private val flashcardList = mutableListOf<Flashcard>()
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // add flash cards to the list
        flashcardList.add(Flashcard("What is the capital of Cambodia?", "Phnom Penh"))
        flashcardList.add(Flashcard("What is the highest mountain in the world?", "Mount Everest"))

        questionTextView = findViewById(R.id.question_text_view)
        answerTextView = findViewById(R.id.answer_text_view)
        flipButton = findViewById(R.id.flip_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        deleteButton = findViewById(R.id.delete_button)
        addCardButton = findViewById(R.id.add_button)

        // set current flash card to the first one in the list
        currentflashcard = flashcardList[currentIndex]
        updateUI()

        flipButton.setOnClickListener {
            currentflashcard.flip(questionTextView,answerTextView)
            updateUI()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % flashcardList.size
            currentflashcard = flashcardList[currentIndex]
            updateUI()
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + flashcardList.size) % flashcardList.size
            currentflashcard = flashcardList[currentIndex]
            updateUI()
        }

        deleteButton.setOnClickListener {
            flashcardList.removeAt(currentIndex)
            if (flashcardList.isEmpty()) {
                flashcardList.add(Flashcard("Empty", "Empty"))
            }
            if (currentIndex >= flashcardList.size) {
                currentIndex = flashcardList.size - 1
            }
            currentflashcard = flashcardList[currentIndex]
            updateUI()
        }

        addCardButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_add_card, null)
            val questionEditText = view.findViewById<EditText>(R.id.question_input)
            val answerEditText = view.findViewById<EditText>(R.id.answer_input)
            val addButton = view.findViewById<Button>(R.id.add_button)
            val cancelButton = view.findViewById<Button>(R.id.cancel_button)
            builder.setView(view)
            val dialog = builder.create()
            addButton.setOnClickListener {
                val question = questionEditText.text.toString().trim()
                val answer = answerEditText.text.toString().trim()
                if (question.isNotEmpty() && answer.isNotEmpty()) {
                    flashcardList.add(Flashcard(question, answer))
                    currentIndex = flashcardList.size - 1
                    currentflashcard = flashcardList[currentIndex]
                    updateUI()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Please fill out both fields.", Toast.LENGTH_SHORT).show()
                }
            }
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun updateUI() {
        questionTextView.text = currentflashcard.question
        answerTextView.text = currentflashcard.answer
    }
}

