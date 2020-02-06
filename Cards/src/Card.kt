import java.util.*
import kotlin.properties.Delegates

fun main() {
    val rating = listOf<Int>(0,3,5)

    class Card(var question: String, var answer: String, val id: UUID, val date: String = Date().toString()) {
        var quality: Int? = null
        var repetitions = 0
        var interval = 1
        var nextPracticeDate = 1
        var easiness = easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)

        fun show() {
            println(question)
            println("Press enter to see the answer.")
            println(answer)
            println("Rate question (0 hard, 3 moderate, 5 easy):")
            quality = readLine()!!.toIntOrNull()
            while (quality == null) {
                println("Rate question (0 hard, 3 moderate, 5 easy):")
                quality = readLine()!!.toIntOrNull()
            }

        }
    }

    var card = Card("Despertarse", "To wake up", UUID.randomUUID())

    card.show()

}
