package com.kuzmin.bookcatalog.config

import com.kuzmin.bookcatalog.model.entity.Book
import io.lettuce.core.ClientOptions
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
class Config {
    @Bean
    fun redisOperations(factory: ReactiveRedisConnectionFactory): ReactiveRedisOperations<String, Book> {
        val serializer = Jackson2JsonRedisSerializer(Book::class.java)
        val builder: RedisSerializationContextBuilder<String, Book> =
            RedisSerializationContext.newSerializationContext(StringRedisSerializer())
        val context: RedisSerializationContext<String, Book> = builder.value(serializer).build()
        return ReactiveRedisTemplate(factory, context)
    }
}