@ECHO OFF
set MCP=%USERPROFILE%\mcp\1.8\dev-bcic\
set BUILDSRC=%MCP%src
set CLEANSRC=%MCP%src-dev

set PROJBASE="%USERPROFILE%\Mod Stuff\MineFactory"

set SRCBASE=%PROJBASE%\source
set CLIENTSRC="%SRCBASE%\client"
set COMMONSRC="%SRCBASE%\common"
set SERVERSRC="%SRCBASE%\server"

set RELEASEBASE=%PROJBASE%\release\
set RELEASECLIENT=%RELEASEBASE%\clienttemp\
set RELEASESERVER=%RELEASEBASE%\servertemp\

del /s /f /q %BUILDSRC% > NUL
xcopy /y /e /q %CLEANSRC% %BUILDSRC% > NUL
ECHO Copying common to client
xcopy /y /e /q %COMMONSRC%\* %BUILDSRC%\minecraft\
ECHO Copying common to server
xcopy /y /e /q %COMMONSRC%\* %BUILDSRC%\minecraft_server\
ECHO Copying client
xcopy /y /e /q %CLIENTSRC%\* %BUILDSRC%\minecraft\
ECHO Copying server
xcopy /y /e /q %SERVERSRC%\* %BUILDSRC%\minecraft_server\

cd %MCP%
runtime\bin\python\python_mcp runtime\recompile.py %*
runtime\bin\python\python_mcp runtime\reobfuscate.py %*



pause