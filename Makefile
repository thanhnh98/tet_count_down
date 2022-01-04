APP=com.thanh_nguyen.tet_count_down
APP_DEBUG=$(APP).debug
current_dir = $(shell pwd)

debug_file_path = D:\Github\tet_count_down\app\build\outputs\apk\debug\app-debug.apk

default:
	@echo "-------------------------------------------------------------------"
	@echo ".                                                                 ."
	@echo ". USAGE: make <command> or make <command>_<bebug or release>      ."
	@echo ".     ex: make run                                                ."
	@echo ". by default will be executed on DEV                              ."
	@echo ". (DEBUG and RELEASE haven't implemented yet)                     ."
	@echo ".                                                                 ."
	@echo "-------------------------------------------------------------------"

.PHONY: build
.PHONY: run
.PHONY: restart

test:
	@echo $(current_dir)

run: build install restart

install:
	gradlew.bat installDebug

build:
	gradlew.bat assembleDebug

restart: force_stop open

force_stop:
	adb shell am force-stop $(APP_DEBUG)

open:
	adb shell monkey -p $(APP_DEBUG) -c android.intent.category.LAUNCHER 1

clean:
	adb shell pm clear $(APP_DEBUG)
	make restart

run-unit-tests:
	gradlew test

uninstall:
	adb uninstall $(APP_DEBUG)

clean_project:
	gradlew clean cleanBuildCache

devices:
	adb devices
