package benchmarks

import benchmarks.fake.MapperFakeData
import com.vnteam.architecturetemplates.data.mapperimpls.DemoObjectResponseMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.OwnerResponseMapperImpl
import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.presentation.mapperimpls.DemoObjectUIMapperImpl
import com.vnteam.architecturetemplates.presentation.mapperimpls.OwnerUIMapperImpl
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.BenchmarkTimeUnit
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
class DemoObjectMappersBenchmark {
    private lateinit var testObject: DemoObject
    private lateinit var testObjectList: List<DemoObject>

    private lateinit var responseMapper: DemoObjectResponseMapperImpl
    private lateinit var uiMapper: DemoObjectUIMapperImpl

    @Setup
    fun setup() {
        responseMapper = DemoObjectResponseMapperImpl(OwnerResponseMapperImpl())
        uiMapper = DemoObjectUIMapperImpl(OwnerUIMapperImpl())

        testObject = MapperFakeData.createFakeDemoObject()
        testObjectList = MapperFakeData.createFakeDemoObjectList(100)
    }

    @Benchmark
    fun singleObjectTransformationChain(): DemoObjectUI {
        val response = responseMapper.mapToImplModel(testObject)
        return uiMapper.mapToImplModel(responseMapper.mapFromImplModel(response))
    }

    @Benchmark
    fun listTransformationChain(): List<DemoObjectUI> {
        val responses = responseMapper.mapToImplModelList(testObjectList)
        return uiMapper.mapToImplModelList(responseMapper.mapFromImplModelList(responses))
    }

    @Benchmark
    fun responseMapperSingle(): DemoObjectResponse {
        return responseMapper.mapToImplModel(testObject)
    }

    @Benchmark
    fun responseMapperList(): List<DemoObjectResponse> {
        return responseMapper.mapToImplModelList(testObjectList)
    }

    @Benchmark
    fun uiMapperSingle(): DemoObjectUI {
        return uiMapper.mapToImplModel(testObject)
    }

    @Benchmark
    fun uiMapperList(): List<DemoObjectUI> {
        return uiMapper.mapToImplModelList(testObjectList)
    }
}
