class Chat (val chatId: Int, val addressee: ChatPerson, var chatName: String? = null, val firstOutMessage: String? = null){
    var messageList: MutableMap<Int, ChatMessage> = mutableMapOf()
    var messageId = 0
    var noMessage = 0
    var noSent = 0
    var noReceived = 0
    init {
        if (chatName == null) chatName = addressee.name
        if (firstOutMessage != null){
            createMessage(firstOutMessage, MessageDirection.outgoing)
        }
    }
    fun createMessage(text:String?, direction: MessageDirection): ChatMessage?{
        val mId = getNextMessageId()
        val read = if(direction == MessageDirection.incoming) false else true
        val message = messageList.put(mId, ChatMessage(mId, addressee, text, direction, read))

        noMessage++

        if (direction == MessageDirection.outgoing){
            noSent++
        } else{
            noReceived++
        }
        return message
    }
    fun deleteMessage(messageId: Int): Boolean{
        val message = messageList.remove(messageId)
        if (message == null) return false
        if (message.direction == MessageDirection.outgoing)
            noSent--
        else
            noReceived--
        return true
    }
    fun getMessage(mId: Int, count: Int):List<ChatMessage>?{
        if (mId > messageId || mId < 0 || count < 0)
            return null
        val messages = messageList.filter { it.key >= mId }.values.toList().subList(0,count)
        val retList = mutableListOf<ChatMessage>()
        retList.addAll(messages)
        return retList
    }
    fun deleteAllMessages(): Boolean{
        messageList = mutableMapOf()
        messageId = 0
        noMessage = 0
        noSent = 0
        noReceived = 0

        return true
    }
    fun getAllMessages(): List<ChatMessage>?{
        return messageList.values.toList()
    }
    fun markAsRead(mId: Int, count: Int): Boolean{
        if (mId > messageId || mId < 0 )
            return false
        val message = messageList.get(mId)
        if (message == null)
            return false
        message.read = true
        return true
    }
    fun getLastMessage(): ChatMessage?{
        return messageList.get(messageId)
    }
    fun getNumberUnread(): Int{
        return messageList.count { it.value.read == false }
    }
    fun getNextMessageId(): Int{
        return ++messageId
    }
    fun getNumberOfMessage(): Int = messageList.count()
}
data class ChatMessage(
    val messageId: Int,
    val personId: ChatPerson,
    val text: String? = null,
    val direction: MessageDirection,
    var read: Boolean = false,
    val timeStamp: Int = System.currentTimeMillis().toInt()
)
enum class MessageDirection{ incoming, outgoing }