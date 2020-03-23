#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
An example HBase client
"""
import happybase

def main():
    # CHANGE ME
    HOST='hbase-docker'
    PORT=9090

    # Will create and then delete this table
    TABLE_NAME='table-name'.encode('utf-8')
    ROW_KEY='row-key'.encode('utf-8')
    
    connection = happybase.Connection(HOST, PORT)

    table = connection.table(TABLE_NAME)

    print("Getting values for row key '{0}'".format(ROW_KEY))
    row = table.row(ROW_KEY)
    print(row)

if __name__ == "__main__":
    main()