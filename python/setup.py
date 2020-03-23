#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import sys

try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

import hbasehelloworld


if sys.argv[-1] == 'publish':
    os.system('python setup.py sdist upload')
    sys.exit()


with open(os.path.join(os.path.dirname(__file__), 'README.md')) as f:
    readme = f.read()

packages = [
    'hbasehelloworld',
]

package_data = {
}

requires = [
]

classifiers = [
]

setup(
    name='hbasehelloworld',
    version=hbasehelloworld.__version__,
    description='A Hbase example application',
    long_description=readme,
    packages=packages,
    package_data=package_data,
    install_requires=requires,
    author=hbasehelloworld.__author__,
    url='https://github.com/kitmenke/hbase-hello-world',
    license='MIT',
    classifiers=classifiers,
)
