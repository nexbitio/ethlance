# Makefile for deploying the ethlance design static website as a
# baseline for ethlance ui development
#
# Requires:
#  - php cli (sudo apt-get install php)
#  - gulp (npm install -g gulp)
#
# Quickstart:
#  - make build deploy

.PHONY: help build deploy clean clean-all deps


SERVER_PORT := 8080


help:
	@echo "Ethlance Static PHP Site"
	@echo ""
	@echo "Development Commands:"
	@echo "  deps               :: Setup initial dependencies."
	@echo "  build              :: Build the src files for deployment."
	@echo "  deploy             :: Run a local PHP server for development."
	@echo ""
	@echo "Misc Commands:"
	@echo "  clean              :: Clean out build artifacts."
	@echo "  help               :: Displays this help message."


GULP_SCRIPT := ./node_modules/gulp/bin/gulp.js
build:
	node $(GULP_SCRIPT) build


deploy:
	cd ./deploy && php -S localhost:$(SERVER_PORT)


deps:
	npm install .


clean:
	rm -rf deploy/css
	rm -rf deploy/js
	rm -rf deploy/pages
	rm -rf deploy/css
	rm -rf deploy/index.php


clean-all: clean
	rm -rf node_modules
