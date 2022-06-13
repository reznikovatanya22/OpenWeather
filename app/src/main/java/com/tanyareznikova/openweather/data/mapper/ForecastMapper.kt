package com.tanyareznikova.openweather.data.mapper

import com.tanyareznikova.openweather.data.api.dto.forecast.ForecastDto
import com.tanyareznikova.openweather.data.database.entity.ForecastEntity
import com.tanyareznikova.openweather.domain.model.forecast.ForecastModel

/*
* Forecast Dto Mapper
*/
fun ForecastDto.toForecast() : ForecastModel {

    return ForecastModel(
        city = city,
        list = list
    )

}

/*
* Forecast Entity Mapper
*/
fun ForecastEntity.toForecastModel() : ForecastModel {

    return ForecastModel(
        city = city,
        list = list
    )

}

fun ForecastModel.toForecastEntity() : ForecastEntity {

    return ForecastEntity(
        city = city,
        list = list
    )

}
