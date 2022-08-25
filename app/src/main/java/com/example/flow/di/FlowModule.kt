package com.example.flow.di

import com.example.flow.data.repository.PokeRepositoryImpl
import com.example.flow.data.source.PokeDataSource
import com.example.flow.data.source.PokeDataSourceImpl
import com.example.flow.domain.usecase.CoroutineUseCase
import com.example.flow.domain.usecase.FlowUseCase
import com.example.flow.presentation.coroutines.CoroutinesViewModel
import com.example.flow.presentation.flow.FlowViewModel
import com.example.flow.presentation.flowext.FlowExtViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    factory { provideService(get()) }
    factory<PokeDataSource> { PokeDataSourceImpl(get()) }
}

val domainModule = module {
    factory { FlowUseCase(PokeRepositoryImpl(get())) }
    factory { CoroutineUseCase(PokeRepositoryImpl(get())) }
}

val presentationModule = module {
    viewModel { CoroutinesViewModel(get(), Dispatchers.IO) }
    viewModel { FlowViewModel(get(), Dispatchers.IO) }
    viewModel { FlowExtViewModel(get(), Dispatchers.IO) }
}

val modules = listOf(networkModule, dataModule, domainModule, presentationModule)

