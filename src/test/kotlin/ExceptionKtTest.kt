import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ExceptionKtTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test(expected = PostNotFound::class)
    fun shouldThrow(){
        val comment = Comment(2, 2, 3, "Hello")
        WallService.createComment(2,comment)
    }

    @Test
    fun createComment() {
        val post = Post(
            1, 1, 1, 1, 1,
            "Hi", 1, false, true, 2,
        )
        WallService.add(post)
        val comment = Comment(2, 2, 3, "Hello")
        val result = WallService.createComment(post.id,comment)
        assertNotNull(result)
    }
}
