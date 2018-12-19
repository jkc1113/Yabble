.PHONY: build ## Generate files from source (default)
build:
	@echo Build: Unimplemented

.PHONY: serve ## Start server
serve: build
	./mvnw spring-boot:run

.PHONY: clean ## Remove generated files
clean:
	@echo Clean: Unimplemented

.PHONY: help ## Print this help
help:
	@echo Options
	@grep -E '^\.PHONY: [a-zA-Z_-]+ .*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = "(: |##)"}; {printf "\033[36m%-10s\033[0m %s\n", $$2, $$3}'
