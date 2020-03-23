.PHONY: init
init:
	pip install -r requirements.txt

.PHONY: install
install:
	pip install .


.PHONY: test
test:
	find . -name \*.pyc -delete
	ptw -- -v -s --cache-clear