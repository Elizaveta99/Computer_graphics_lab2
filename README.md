# Лабораторная работа №2

## Комментарии к коду
 - [Main.java](https://github.com/Elizaveta99/Computer_graphics_lab2/blob/master/cgLab2/src/Main.java) - UI
 - [MyChooser.java](https://github.com/Elizaveta99/Computer_graphics_lab2/blob/master/cgLab2/src/MyChooser.java) - Выбрать файл
 - [MyChooserDir.java](https://github.com/Elizaveta99/Computer_graphics_lab2/blob/master/cgLab2/src/MyChooserDir.java) - Выбрать папку с файлами
 - [ParseMetadata.java](https://github.com/Elizaveta99/Computer_graphics_lab2/blob/master/cgLab2/src/ParseMetadata.java) - Выполнить "разбор" картинки по свойствам
 
## Описание работы
При работе была использована внешняя библиотека [**metadata-extractor**](https://github.com/drewnoakes/metadata-extractor), \
данные "доставались" с её помощью из директорий, соответствующих типу изрбражения (например: ExifIFD0Directory для .tif, BmpHeaderDirectory для .bmp и др.) по соответствующим тегам (например PngDirectory.TAG_IMAGE_WIDTH).<br />
Проводились дополнительные вычисления, чтобы привести информацию к нужному виду, где это было необходимо.
