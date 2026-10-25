package benchmarks.fake

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetDemoObjectsFromDBUseCase : GetDemoObjectsFromDBUseCase {
    var demoObjects: List<DemoObject>? = null

    override suspend fun execute(params: Nothing?): Flow<List<DemoObject>?> {
        return flowOf(demoObjects)
    }
}
