import kotlin.random.Random

fun main() {
    var cs = ChatService()
    var personsCounter: Int = 0
    cs.addPerson(ChatPerson(++personsCounter, "Яна"))
    cs.addPerson(ChatPerson(++personsCounter, "Алексей"))
    cs.addPerson(ChatPerson(++personsCounter, "Станислав"))
    cs.addPerson(ChatPerson(++personsCounter, "Дмитрий"))
    cs.addPerson(ChatPerson(++personsCounter, "Алесь"))

    cs.personsList.forEach{
        val noMessage = Random.nextInt(1, 100)
        for (i: Int in 0 until noMessage){
            if (i % 2 == 0)
                cs.sendMessage(it, "Исходящее сообщение $i")
            else
                cs.receiveMessage(it,"Входящее сообщения $i")
        }
    }
    var userInput: String? = null
    do {
        println("Номер чата         Имя           Последнее сообщение")
        println("*******************************************************************************************************")
        val chatsShort = cs.getChatsShortView()
        chatsShort.forEach { println(it) }
        println("*******************************************************************************************************")
        println("Открыть чат - введите номер чата, узнать количество чатов с непрочитанными сообщениями - введите ?, закрыть приложение - end")
        print("Введите Ваш выбор: ")
        userInput = readLine()

        if (userInput == "?") {
            println("*******************************************************************************************************")
            println("У вас  ${cs.unreadChatsNumber()} чата с непрочитанными сообщениями")
            println("*******************************************************************************************************")
            userInput = readLine()
            continue
        }
        val chatNumber = userInput?.toIntOrNull()
        if (chatNumber == null) continue

        do {
            val chatMessages = cs.getMessages(chatNumber)
            println("Номер   Статус      Сообщения")
            println("*******************************************************************************************************")
            chatMessages?.forEach { println(it) }
            println("*******************************************************************************************************")
            println("* - непрочитанное сообщение")
            println("Удалить сообщение - введите номер сообщения , отправить новое - s, получить новое - r, q - выход")
            userInput = readLine()

            if (userInput == "s") {
                print("Введите текст сообщения для отправки: ")
                val mt = readLine()
                cs.sendMessage(chatNumber, mt)
                continue
            }

            if (userInput == "r") {
                print("Введите текст полученного сообщения: ")
                val mt = readLine()
                cs.receiveMessage(chatNumber, mt)
                continue
            }

            val mdId = userInput?.toIntOrNull()
            if (mdId == null) continue

            cs.deleteMessage(chatNumber, mdId)

        } while (userInput != "q")

    } while (userInput != "end")
}