APP=com.thanh_nguyen.tet_count_down
APP_DEBUG=$(APP).debug
current_dir = $(shell pwd)
GRADLEW_PATH = gradlew.bat # Windows
detected_OS = Windows
debug_file_path = D:\Github\tet_count_down\app\build\outputs\apk\debug\app-debug.apk

ifeq '$(findstring ;,$(PATH))' ';'
    detected_OS := Windows
else
    detected_OS := $(shell uname 2>/dev/null || echo Unknown)
    detected_OS := $(patsubst CYGWIN%,Cygwin,$(detected_OS))
    detected_OS := $(patsubst MSYS%,MSYS,$(detected_OS))
    detected_OS := $(patsubst MINGW%,MSYS,$(detected_OS))
endif


ifeq ($(detected_OS),Darwin)        # Mac OS X
    GRADLEW_PATH := ./gradlew
    debug_file_path := ./app/build/outputs/apk/debug/app-debug.apk
endif

default:
	@echo "-------------------------------------------------------------------"
	@echo ".                                                                 ."
	@echo ". USAGE: make <command> or make <command>_<bebug or release>      ."
	@echo ".     ex: make help                                                ."
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

run_fp: build grant_full_permission

install:
	adb install -r -t $(debug_file_path)

grant_full_permission:
	adb install -r -t -g $(debug_file_path)
	make restart

build:
	$(GRADLEW_PATH) assembleDebug

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

lint:
	gradlew lint

help:
	@echo "01. run"
	@echo "02. run_fp #run with full permission"
	@echo "03. install"
	@echo "04. uninstall"
	@echo "05. grant_full_permission"
	@echo "06. build"
	@echo "07. restart"
	@echo "08. force_stop"
	@echo "09. open"
	@echo "10. clean #clear cache && restart"
	@echo "11. clean_project #clear all cached files of project"
	@echo "12. devices"
