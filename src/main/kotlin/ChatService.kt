class ChatService {
    var chatList: MutableList<Chat> = mutableListOf()
    var personsList: MutableList<ChatPerson> = mutableListOf()
    var chatId: Int = 0
    var personId: Int = 0
    fun unreadChatsNumber(): Int{
        val count: Int = chatList.count{ it.getNumberUnread() > 0 }
        return count
    }
    fun getChatsShortView(): List<String>{
        val chatString: MutableList<String> = mutableListOf()

        chatList.forEach { val chatShort: String = "[${it.chatId.toString().padStart(4,'0')}] ${"%15s".format(it.chatName)}:" +
                " ${"%30s".format(it.getLastMessage()?.text)}"
        chatString.add(chatShort)
        }
        return chatString
    }
    fun addPerson(person: ChatPerson){
        personsList += person
    }
    fun sendMessage(person: ChatPerson, text: String): ChatMessage?{
        var chat = chatList.find { it.addressee.personId == person.personId }
        if (chat == null){
            chat = Chat(chatId = getNextChatId(), addressee = person)
            chatList += chat
        }
        return chat.createMessage(text, MessageDirection.outgoing)

    }
    fun sendMessage(chatId: Int, text: String?): ChatMessage? {
        val chat = chatList.find { it.chatId == chatId }

        return chat?.createMessage(text, MessageDirection.outgoing)
    }
    fun receiveMessage(person: ChatPerson, text: String): ChatMessage?{
        val chat = chatList.find { it.addressee.personId == person.personId }
        return chat?.createMessage(text, MessageDirection.incoming)
    }
    fun receiveMessage(chatId: Int, text: String?): ChatMessage? {
        val chat = chatList.find { it.chatId == chatId }

        return chat?.createMessage(text, MessageDirection.incoming)
    }
    fun deleteMessage(chatId: Int, messageId: Int): Boolean{
        val chat = chatList.find { it.chatId == chatId }
        if (chat == null) return false
        chat.deleteMessage(messageId)
        if (chat.getNumberOfMessage() == 0){
            this.deleteChat(chatId)
        }
        return true
    }
    fun deleteChat(person: ChatPerson): Boolean {
        val chat = chatList.find { it.addressee.personId == person.personId }
        if (chat == null)
            return false

        chat.deleteAllMessages()
        return chatList.remove(chat)
    }
    fun deleteChat(chatId: Int): Boolean {
        val chat = chatList.find { it.chatId == chatId }
        if (chat == null)
            return false

        chat.deleteAllMessages()
        return chatList.remove(chat)
    }
    fun getNextChatId(): Int {
        return ++chatId
    }
    fun getNextPersonId(): Int{
        return ++personId
    }
    fun getMessages(chatNumber: Int): List<String>? {
        val chat = chatList.find {it.chatId == chatNumber }
        if (chat == null) return null
        val outStrings: MutableList<String> = mutableListOf()
        chat.getAllMessages()?.forEach {
            val ms: String = "<${it.messageId.toString().padStart(4, '0')}>" +
                    " [${if(it.direction == MessageDirection.incoming) "ВХД" else "ИСХ"}] " +
                    "|${if (it.read)" " else "*"}| : ${it.text} "
            outStrings += ms
            it.read = true
        }
        return outStrings
    }
}