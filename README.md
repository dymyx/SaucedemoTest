it works, trust me.

Инструкция по запуску программного кода:

# Chrome (default):

mvn clean test

# Конкретный браузер:

mvn clean test -Dbrowser=firefox mvn clean test -Dbrowser=edge

# Конкретный тест:

mvn clean test -Dtest=LoginTests
