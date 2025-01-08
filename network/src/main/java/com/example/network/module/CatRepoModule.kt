package com.example.network.module

import com.example.network.repo.CatRepo
import com.example.network.repo.impl.CatRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CatRepoModule {

    @Binds
    @Singleton
    abstract fun bindCatRepo(catRepoImpl: CatRepoImpl): CatRepo
}