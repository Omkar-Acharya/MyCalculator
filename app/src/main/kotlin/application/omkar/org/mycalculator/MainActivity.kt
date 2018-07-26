package application.omkar.org.mycalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*All the setOnClickListener methods are called using Functional Programming*/
        //Concat Add operator to expression
        add.setOnClickListener { concat("+", false) }
        //Concat Subtract operator to expression
        subtract.setOnClickListener { concat("-", false) }
        //Concat Multiply operator to expression
        multiply.setOnClickListener { concat("*", false) }
        //Concat Division operator to expression
        division.setOnClickListener { concat("/", false) }
        //Concat Modulus operator to expression
        percent.setOnClickListener { percentage()}
        //Call Negate function on plusminus button click
        plusminus.setOnClickListener { negate() }

        //Concat digit 1 to expression
        one.setOnClickListener { concat("1", true) }
        //Concat digit 2 to expression
        two.setOnClickListener { concat("2", true) }
        //Concat digit 3 to expression
        three.setOnClickListener { concat("3", true) }
        //Concat digit 4 to expression
        four.setOnClickListener { concat("4", true) }
        //Concat digit 5 to expression
        five.setOnClickListener { concat("5", true) }
        //Concat digit 6 to expression
        six.setOnClickListener { concat("6", true) }
        //Concat digit 7 to expression
        seven.setOnClickListener { concat("7", true) }
        //Concat digit 8 to expression
        eight.setOnClickListener { concat("8", true) }
        //Concat digit 9 to expression
        nine.setOnClickListener { concat("9", true) }
        //Concat digit 0 to expression
        zero.setOnClickListener { concat("0", true) }
        //Concat dot to expression
        dot.setOnClickListener { concat(".", true) }

        //Compile, evaluate the expression on equal-to button click
        equalto.setOnClickListener {
            try {
                /*Passing expression to ExpressionBuilder for compiling.
                * ExpressionBuidler package is an easy way to compile and evaluate the mathematical expression*/
                val compiledExpression = ExpressionBuilder(digits.text.toString()).build()
                //Validating and calculating the answer for the expression
                val evaluatedAnswer = compiledExpression.evaluate()
                //Converting Int to Long
                val longEvaluatedAnswer = evaluatedAnswer.toLong()
                if(evaluatedAnswer == longEvaluatedAnswer.toDouble())
                    //Appending Long to answer TextView
                    answer.text = longEvaluatedAnswer.toString()
                else
                    //Appending Int to answer TextView
                    answer.text = evaluatedAnswer.toString()

            }
            //Catching Arithmetic Exception "Division by Zero" and appending it to answer TextView
            catch(e:ArithmeticException){
                answer.setText("Cannot Divide by Zero")
            }
            //Catching all other Exceptions and logging the error message
            catch (e:Exception){
                Log.d("Exception"," message : " + e.message )
            }
        }

        //Clearing the digits and answer TextView on click of C button
        c.setOnClickListener {
            digits.text = ""
            answer.text = "0"
        }

        //Clearing the characters from expression on click of undo button
        undo.setOnClickListener {
            val string = digits.text.toString()
            if(string.isNotEmpty()){
                //Removing the last index element from the string
                digits.text = string.substring(0,string.length-1)
            }
            answer.text = ""
        }
    }

    /*Function to concat digits and operator for creating expression
    * Input: String: digit/operator
    *        Boolean: true/false
    * Output: None. Modifies the digits TextView with expression and answer TextView with expression's answer
    */
    fun concat(string: String, flag: Boolean) {

        //Clearing digits TextView if Answer TextView is not empty so that the answer becomes the part of expression
        if(answer.text.isNotEmpty()){
            digits.text = ""
        }

        //If flag is true, the string is a digit. This block of if appends the digit to the expression and clears answer TextView
        if (flag) {
            answer.text = ""
            digits.append(string)
        }

        //If flag is false, the string is a operator. Here answer TextView is also appended to expression along with operator
        else {
            digits.append(answer.text)
            digits.append(string)
            answer.text = ""
        }
    }

    /*Function to negate the digit/expression on click of plus/minus button
    * Input/ Output: None.
    * Modifies the digits TextView and answer TextView with negation sign*/
    fun negate() {

        try {
            //If answer TextView is not empty, negate the evaluated expression's answer
            if (answer.text.isNotEmpty()) {

                digits.text = ""
                //Convert String answer to Int and then negate it by multiplying with -1
                val answernegativeno: Int = answer.text.toString().toInt() * -1
                answer.text = ""
                //Set the answer to digits TextView for forming the expression
                digits.setText(answernegativeno.toString())
            }

            //If answer TextView is empty, negate the number present in the digits TextView
            else {

                //Convert String number to Int and then negate it by multiplying with -1
                val digitsnegativeno: Int = digits.text.toString().toInt() * -1
                //Set the negated number to digits TextView for forming the expression
                digits.setText(digitsnegativeno.toString())
            }
        } catch (e: Exception) {
            Log.d("Exception", " message : " + e.message)
        }
    }

    /*Function to find the percentage of  the digit/expression on click of percentage button
    * Input/ Output: None.
    * Modifies the digits TextView and answer TextView with percentage of the digits*/
    fun percentage() {
        try {
            //If answer TextView is not empty, find the percentage of evaluated expression's answer
            if (answer.text.isNotEmpty()) {

                digits.text = ""
                //Convert String answer to Double and then multiplying with 0.01
                val answernegativeno: Double = answer.text.toString().toDouble() * (0.01)
                answer.text = ""
                //Set the answer to digits TextView for forming the expression
                digits.setText(answernegativeno.toString())
            }
            else if(digits.text.contains("*")){
                val seconddigit: Double = digits.text.toString().split("*")[1].toDouble() * 0.01
                val newdigits = digits.text.toString().split("*")[0]+"*"+ seconddigit
                digits.setText(newdigits.toString())
            }

            else{
                //Convert String answer to Double and then multiplying with 0.01
                val digitspercent: Double = digits.text.toString().toDouble() * (0.01)
                //Set the percantage number to answers TextView
                answer.setText(digitspercent.toString())
            }

        } catch (e: Exception) {
            Log.d("Exception", " message : " + e.message)
        }
    }
}
