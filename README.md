# UniversalConverter

Сервис конвертирует значения, которые передаются через POST /convert .

Порт используется 80, пример запроса: http://localhost:80/convert
<br>В теле запроса передается JSON, например:
<pre>
{
    "from": "м",
    "to":  "см"
}
</pre>

При запуске необходимо передать в качестве аргумента файл в csv формате, где будут указаны значения которые можно конвертировать.

Запустить сервис можно так:
<pre>
java -jar universal-converter-1.0.0.jar /path/to/file.csv
</pre>
