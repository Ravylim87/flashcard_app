package com.example.flashcard_app

import android.view.View
import android.widget.TextView

data class Flashcard(val question: String, val answer: String){
     var isFlipped: Boolean = false
         private set
     fun flip(questionTextView: TextView, answerTextView: TextView){
         isFlipped = !isFlipped
         if (isFlipped)   {
             questionTextView.visibility = View.INVISIBLE
             answerTextView.visibility = View.VISIBLE
             answerTextView.text = answer
         }
         else {
             questionTextView.visibility = View.VISIBLE
             answerTextView.visibility = View.INVISIBLE
             questionTextView.text = question
         }
     }
 }