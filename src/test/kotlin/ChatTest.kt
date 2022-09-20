import org.junit.Assert.assertEquals
import org.junit.Test

class ChatTest {

    @Test
    fun createMessage() {
        val chat = Chat(1, ChatPerson(1, "Алесь"))
        chat.deleteAllMessages()

        chat.createMessage("Привет! Как дела?", MessageDirection.outgoing)
        chat.createMessage("Все хорошо!", MessageDirection.incoming)

        assertEquals(2, chat.getNumberOfMessage())
    }

    @Test
    fun deleteMessage() {
        val chat = Chat(1, ChatPerson(1, "Станислав"))
        chat.deleteAllMessages()

        chat.createMessage("Сообщение 1", MessageDirection.outgoing)
        chat.createMessage("Сообщение 2", MessageDirection.incoming)
        chat.createMessage("Сообщение 3", MessageDirection.outgoing)
        chat.createMessage("Сообщение 4", MessageDirection.incoming)

        chat.deleteMessage(1)
        chat.deleteMessage(2)

        assertEquals(2, chat.getNumberOfMessage())
    }


    @Test
    fun getAllMessages() {
        val chat = Chat(1, ChatPerson(1, "Алексей"))
        chat.deleteAllMessages()

        chat.createMessage("Сообщение 1", MessageDirection.outgoing)
        chat.createMessage("Сообщение 2", MessageDirection.incoming)
        chat.createMessage("Сообщение 3", MessageDirection.outgoing)
        chat.createMessage("Сообщение 4", MessageDirection.incoming)

        val mess = chat.getAllMessages()

        assertEquals(4, mess?.size)
    }


    @Test
    fun getNumberOfMessages() {
        val chat = Chat(1, ChatPerson(1, "Дмитрий"))
        chat.deleteAllMessages()

        chat.createMessage("Сообщение 1", MessageDirection.outgoing)
        chat.createMessage("Сообщение 2", MessageDirection.incoming)

        assertEquals(2, chat.getNumberOfMessage())
    }

    @Test
    fun getMessages() {
        val chat = Chat(1, ChatPerson(1, "Яна"))
        chat.deleteAllMessages()

        chat.createMessage("Сообщение 1", MessageDirection.outgoing)
        chat.createMessage("Сообщение 2", MessageDirection.incoming)
        chat.createMessage("Сообщение 3", MessageDirection.outgoing)
        chat.createMessage("Сообщение 4", MessageDirection.incoming)
        chat.createMessage("Сообщение 5", MessageDirection.outgoing)
        chat.createMessage("Сообщение 6", MessageDirection.incoming)
        chat.createMessage("Сообщение 7", MessageDirection.outgoing)
        chat.createMessage("Сообщение 8", MessageDirection.incoming)

        val mess = chat.getMessage(1, 5)

        assertEquals(5, mess?.size)
    }
}