package com.kuzmin.bookcatalog.model.converter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.time.Year

@Configuration
class YearAttributeConverter() {
    @Bean
    fun mongoCustomConversions(): MongoCustomConversions =
        MongoCustomConversions(listOf(
            YearToIntConverter(),
            IntToYearConverter()
        ))


    @WritingConverter
    class YearToIntConverter: Converter<Year, Int> {
        override fun convert(p0: Year): Int = p0.value
    }

    @ReadingConverter
    class IntToYearConverter: Converter<Int, Year> {
        override fun convert(p0: Int): Year? = Year.of(p0)
    }
}