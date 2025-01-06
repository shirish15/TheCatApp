package com.example.thecatapp.cat.module

import com.example.thecatapp.cat.repo.CatRepo
import com.example.thecatapp.cat.repo.impl.CatRepoImpl
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