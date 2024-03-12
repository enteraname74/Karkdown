import model.*
import org.koin.dsl.module

/**
 * Dependency module of the domain.
 */
val domainModule = module {
    single {
        FileManager()
    }
}