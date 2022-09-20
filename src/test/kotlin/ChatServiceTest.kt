import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun sendMessage() {
        val cs = ChatService()

        var personsCounter = 0
        val person = ChatPerson(++personsCounter, "Яна")
        cs.addPerson(person)

        cs.sendMessage(person, "Сообщение для $person, Привет! Как дела?")
        cs.sendMessage(person, "Сообщение для $person, Привет! Как дела?")
        cs.sendMessage(person, "Сообщение для $person, Привет! Как дела?")

        assertEquals(1, cs.chatList.size)
    }
}