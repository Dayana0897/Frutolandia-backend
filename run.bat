@echo off
REM Script para compilar e iniciar Frutolandia Backend

echo ============================================
echo Frutolandia Backend - Spring Boot
echo ============================================

REM Configurar Maven en el PATH
set MAVEN_BIN=%USERPROFILE%\.maven\apache-maven-3.9.11\bin
set PATH=%MAVEN_BIN%;%PATH%

cd /d "%~dp0"

echo.
echo [1/2] Compilando el proyecto...
call mvn clean install -q

if %ERRORLEVEL% neq 0 (
    echo Error en la compilacion
    pause
    exit /b 1
)

echo [1/2] Compilacion completada!
echo.
echo [2/2] Iniciando aplicacion...
echo La aplicacion estara disponible en http://localhost:8080
echo.
call mvn spring-boot:run

pause
