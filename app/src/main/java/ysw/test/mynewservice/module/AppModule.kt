package ysw.test.mynewservice.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides //Hilt가 생성할 객체를 제공하는 메서드를 정의합니다.
    @Singleton // 애플리케이션 전역에서 하나의 인스턴스만 사용하도록 설정합니다.
    fun provideTestString(): String = "Hello from Hilt!"
}