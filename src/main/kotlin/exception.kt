class PostNotFound (message: String):RuntimeException(message)
interface Attachment {
    val type: String
}
data class AudioAttachment(var file : Audio) : Attachment {
    override val type: String = "audio"
    val audio = file
}
data class PhotoAttachment(var file : Photo) : Attachment {
    override val type: String = "photo"
    val photo = file
}
data class VideoAttachment(var file : Video) : Attachment {
    override val type: String = "video"
    val video = file
}
data class LikesAttachment(var file : Likes) : Attachment {
    override val type: String = "likes"
    val likes = file
}
data class NoteAttachment(var file : Note) : Attachment {
    override val type: String = "note"
    val note = file
}
data class Audio(
    val id : Int,// Идентификатор аудиозаписи.
    val owner_id : Int, // Идентификатор владельца аудиозаписи.
    val artist : String, // Исполнитель
    val title : String, // Название композиции
    val duration : Int, // Длительность аудиозаписи в секундах
    val url : String, // Ссылка на mp3.
    val lyrics_id : Int, // Идентификатор текста аудиозаписи (если доступно).
    val album_id : Int, // Идентификатор альбома, в котором находится аудиозапись (если присвоен).
    val genre_id : Int, // Идентификатор жанра из списка аудио жанров.
    val date : Int, // Дата добавления.
    val no_search : Int, // 1, если включена опция «Не выводить при поиске». Если опция отключена, поле не возвращается.
    val is_hq : Int, // 1, если аудио в высоком качестве.
)
data class Photo(
    val id : Int, // Идентификатор фотографии.
    val albumId : Int, // Идентификатор альбома, в котором находится фотография.
    val ownerId : Int, // Идентификатор владельца фотографии.
    val userId : Int, // Идентификатор пользователя, загрузившего фото (если фотография размещена в сообществе). Для фотографий, размещенных от имени сообщества, user_id = 100.
    val text : String, // Текст описания фотографии.
    val date : Int, // Дата добавления в формате Unixtime.
    val width : Int, // Ширина оригинала фотографии в пикселах.
    val height : Int // Высота оригинала фотографии в пикселах.
)
data class Video(
    val id : Int, //	Идентификатор видеозаписи
    val owneId : Int, // Идентификатор владельца видеозаписи
    val title : String, // Название видеозаписи
    val description : String, // Текст описания видеозаписи
    val duration : Int, // Длительность ролика в секундах
    val date : Int, // Дата создания видеозаписи в формате Unixtime
    val adding_date : Int, // Дата добавления видеозаписи пользователем или группой в формате Unixtime.
    val views : Int, //	Количество просмотров видеозаписи
    val localViews : Int, // Если видео внешнее, количество просмотров в ВК
    val comments : Int, // Количество комментариев к видеозаписи
    val player : String, // URL страницы с плеером, который можно использовать для воспроизведения ролика в браузере. Поддерживается flash и html5, плеер всегда масштабируется по размеру окна.
    val platform : String, // Название платформы (для видеозаписей, добавленных с внешних сайтов)
    val canAdd : Boolean, // Может ли пользователь добавить видеозапись к себе, true может
)
data class Likes(
    val count : Int,
    val userLikes : Boolean,
    val canLike : Boolean,
    val canPublish : Boolean
)
data class Note(
    val id : Int, // Идентификатор заметки.
    val owner_id : Int, // Идентификатор владельца заметки.
    val title : String, // Заголовок заметки.
    val text : String, // Текст заметки.
    val date : Int, // Дата создания заметки в формате Unixtime.
    val comments : Int, // Количество комментариев.
    val readComments : Int, // Количество прочитанных комментариев (только при запросе информации о заметке текущего пользователя).
    val viewUrl : String, // URL страницы для отображения заметки.
)
data class Post(
    val id: Int,
    val fromId: Int,
    val createdBy: Int,
    val date: Int,
    val signerId: Int,
    val text: String,
    val markedAsAds: Int,
    val isFavorite: Boolean,
    val canPin: Boolean,
    val isPinned: Int,
    val attachments: Array<Attachment>? = emptyArray(),
)
data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String
)
object WallService {
    private var posts = emptyArray<Post>()
    private var lastId = 0
    private var comments = emptyArray<Comment>()
    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId)
        return posts.last()
    }
    fun createComment(postId: Int, comment: Comment):Comment? {
        for ((index,post) in posts.withIndex()) {
            if (post.id == postId) {
                var newComment = comment.copy(id = ++lastId)
                comments += newComment
                return newComment
            }
        }
        return null
    }


    fun update(postNew: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == postNew.id) {
                posts[index] = postNew.copy()
                return true
            }
        }
        return false
    }
    fun clear() {
        posts = emptyArray()
        lastId = 0
    }
    fun printPosts() {
        for (post in posts) {
            println(post)
        }
    }
}
fun main(){
    val audio1 = Audio(1,2,"Aria","Hero",5,"http",1,2,
        5,25,2,1)
    val photo1 = Photo(1,1,5,5,"Text",5,5,4)
    val video1 = Video(1,2,"Text","title",4,5,4,
        7,5,1,"Text2","HD",true)
    val likes1 = Likes(1,true,false,true)
    val note1 = Note(1,2,"first","text3",
        1,2,3,"http")
    val attachmentVideo1 = VideoAttachment(video1)
    val attachmentAudio1 = AudioAttachment(audio1)
    val attachmentPhoto1 = PhotoAttachment(photo1)
    val attachmentNote1 = NoteAttachment(note1)
    val attachmentLikes1 = LikesAttachment(likes1)
    WallService.add(Post(1,1,1,1,1,
        "Hello",1,false,true,2, arrayOf(attachmentVideo1,attachmentLikes1)
    ))
    WallService.add(Post(2,1,1,1,1,
        "Hi",1,false,true,2, arrayOf(attachmentNote1,attachmentAudio1)
    ))
    WallService.printPosts()
    WallService.update(Post(2,1,1,1,1,
        "GoodBye",1,false,true,2, arrayOf(attachmentNote1,attachmentAudio1)
    ))
    WallService.printPosts()
    val findComment = WallService.createComment(2,
        Comment(1,2,2,"Cool"))?: throw PostNotFound("No post with id")
    println(findComment)
}
