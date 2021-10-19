# Тестовое задание
Сервис обращается к сервису OpenExchangeRates и получает курсы валют за вчерашний и сегодняшний день.
Если искомый курс вырос по отношению к рублю, то сервис обращается к giphy.com и получает случайное изображение по запросу rich, в противном случае - по запросу broke.

##API endpoints

GET /api/gif/{currency_code}

###Параметры
**currency_code: string**  *Required* <br>
*Сервис поддреживает все валюты, которые поддерживает OpenExchangeRates.*

###Примеры
.../api/gif/USD

###Ответ сервиса
* в случае удачного запроса
```json
{
    "error" : "false",
    "errorMessage" : "",
    "data": {
        "url": "some_link",
        "bitlyUrl": "some_link",
         "embedUrl": "some_link",
        }        
}
```
 * в случае ошибок
```json
{
    "error" : "true",
    "errorMessage" : "error_message",
    "data": "null"  
}
```

