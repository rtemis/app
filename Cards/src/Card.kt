import java.lang.NumberFormatException
import java.util.*
import kotlin.math.*

open class Card(var question: String, var answer: String, val id: String = UUID.randomUUID().toString(), val date: String = Date().toString()) {
    val rating = listOf<Int>(0,3,5)
    var quality = -1
    var repetitions = 0
    var interval = 1
    var nextPracticeDate = 0
    var easiness = 2.5
    var currentDate = 0

    /* Function in charge of showing card, showing answer, and asking for rating */
    open fun show() {
        quality = -1
        println(question)
        println("Press enter to see the answer.")
        var y = readLine()
        println(answer)
        var x : Int? = null
        while (x == null && quality !in rating) {
            print("Rate question (0 hard, 3 moderate, 5 easy): ")
            x = readLine()!!.toIntOrNull()
            if (x != null)
                quality = x
        }

        /* Update the card */
        update()
    }

    /* Controller for studying */
    fun update() {
        /* Easiness update */
        easiness = max(1.3, easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))

        /* Repetitions update */
        repetitions = if (quality < 3) 0
        else repetitions + 1

        /* Interval update */
        interval = when (repetitions) {
            0, 1 -> 1
            2 -> 6
            else -> (interval * easiness).roundToInt()
        }

        /* Next practice date update */
        nextPracticeDate = currentDate + interval
    }

    fun details() {
        println(question + " " + answer + ", eas = " + round(easiness * 100) / 100 + ", rep = " + repetitions.toString() + ", int = " + interval.toString() + ", next = " + nextPracticeDate.toString())
    }

    companion object {
        var pregunta : String? = null
        var respuesta : String? = null

        fun leer(): Card {
            var x = -1
            while (x !in 0..1)
            {
                println("Teclea el tipo 0 (Card) 1 (Cloze): ")
                x = readLine()?.toIntOrNull() ?: -1
            }

            println("Nueva tarjeta:")
            while (pregunta == null) {
                println("    Teclea la pregunta:")
                pregunta = readLine()
            }
            while (respuesta == null) {
                println("    Teclea la respuesta:")
                respuesta = readLine()
            }

            if (x == 0)
                return Card(pregunta.toString(), respuesta.toString())
            else
                return Cloze(pregunta.toString(), respuesta.toString())
        }
    }

}
class Cloze(question: String, answer: String) : Card(question, answer) {
    override fun show() {
        quality = -1
        var change = ("\\*[a-zA-Z ]+\\*").toRegex()
        println(question)
        println("Press enter to see the answer.")
        var z = readLine()
        println(question.replace(change, answer))
        var x : Int? = null
        while (x == null && quality !in rating) {
            print("Rate question (0 hard, 3 moderate, 5 easy): ")
            x = readLine()!!.toIntOrNull()
            if (x != null)
                quality = x
        }

        update()
    }
}

fun main() {
    fun timelapse (deck: List<Card>) {
        for (i in 0..20){
            println("Date: " + i)
            println()
            for (c in deck) {
                if (c.nextPracticeDate == c.currentDate) {
                    c.show()
                    c.details()
                }
                c.currentDate += 1
                println()
            }
        }
    }

    /* MAIN CODE */
    val study = listOf<Card>(
//        Card("Despertarse", "To wake up"),
//        Card("Acostarse", "To go to bed"),
//        Card("Comer", "To eat"),
//        Card("Beber", "To drink"),
//        Card("Hablar", "To speak"),
//        Card("Escuchar", "To listen"),
//        Card("Entender", "To understand"),
//        Card("Vivir", "To live"),
//        Card("Morir", "To die")
            Card("duda", "doubt"),
            Card("dificil", "difficult")
    )
    var deck: MutableList<Card> = mutableListOf()
    deck.addAll(study)

    timelapse(deck)

}
