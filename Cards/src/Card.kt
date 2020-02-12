import java.lang.NumberFormatException
import java.util.*
import kotlin.math.*

open class Card(var question: String, var answer: String, val id: String = UUID.randomUUID().toString(), val date: String = Date().toString()) {
    val rating = listOf<Int>(0,3,5)
    var quality = 0
    var repetitions = 0
    var interval = 1
    var nextPracticeDate = 1
    var easiness = 2.5
    var currentDate = 0

    /* Function in charge of showing card, showing answer, and asking for rating */
    open fun show() {
        println(question)
        println("Press enter to see the answer.")
        readLine()
        println(answer)
        println("Rate question (0 hard, 3 moderate, 5 easy):")
        quality = try {
            readLine()!!.toInt()
        } catch (e: NumberFormatException) {
            5
        }

        while (quality !in rating) {
            println("Rate question (0 hard, 3 moderate, 5 easy):")
            quality = readLine()!!.toInt()
        }
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
            else -> (round(easiness * 100)/100 * interval).roundToInt()
        }
        /* Next practice date update */
        nextPracticeDate = currentDate + interval
    }

    fun details() {
        println(question + " " + answer + ", eas = " + round(easiness * 100) / 100 + ", rep = " + repetitions + ", int = " + interval + ", next = " + nextPracticeDate)
    }

    companion object {
        var pregunta : String? = null
        var respuesta : String? = null

        open fun leer(): Card {
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
        var change = ("\\*[a-zA-Z ]+\\*").toRegex()
        println(question)
        println("Press enter to see the answer.")
        readLine()
        println(question.replace(change, answer))
        println("Rate question (0 hard, 3 moderate, 5 easy):")
        quality = try {
            readLine()!!.toInt()
        } catch (e: NumberFormatException) {
            5
        }

        while (quality !in rating) {
            println("Rate question (0 hard, 3 moderate, 5 easy):")
            quality = readLine()!!.toInt()
        }
    }
}

fun main() {

    /* MAIN CODE */
    val study = listOf<Card>(
            Card("Despertarse", "To wake up"),
            Card("Acostarse", "To go to bed"),
            Card("Comer", "To eat"),
            Card("Beber", "To drink"),
            Card("Hablar", "To speak"),
            Card("Escuchar", "To listen"),
            Card("Entender", "To understand"),
            Card("Vivir", "To live"),
            Card("Morir", "To die")
    )
    var deck: MutableList<Card> = mutableListOf()
    deck.addAll(study)

    //  var x = Card.leer()
/*    for (i in 1..2) {
        deck.forEach {
            it.show()
            it.update()
            it.details()
        }
    }*/

    //println (x.details())

    var c = Card.leer()

    c.show()

    /*   for (i in 1..7) {
           deck[0].show()
           deck[0].update()
           deck[0].details()
       }
   */

}
