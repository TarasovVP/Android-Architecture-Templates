package benchmarks.fake

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner

object MapperFakeData {
    fun createFakeDemoObject(): DemoObject = DemoObject(
        demoObjectId = "test_id_123",
        name = "Test Object",
        owner = Owner(
            ownerId = "owner_id_456",
            login = "testuser",
            avatarUrl = "https://example.com/avatar.jpg",
            url = "https://example.com/profile"
        ),
        htmlUrl = "https://example.com/object",
        description = "Test description"
    )

    fun createFakeDemoObjectList(size: Int): List<DemoObject> =
        List(size) { index ->
            createFakeDemoObject().copy(
                demoObjectId = "id_$index",
                name = "Object $index"
            )
        }
}