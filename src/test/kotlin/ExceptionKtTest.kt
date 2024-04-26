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
            2, 1, 1, 1, 1,
            "Hi", 1, false, true, 2,
        )
        val result = WallService.createComment(2, Comment(2, 2, 3, "Hello"))
        assertNotNull(result)
    }
}
