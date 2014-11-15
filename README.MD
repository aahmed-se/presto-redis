presto-redis
================


[PrestoDB](http://prestodb.io/) connector for [Redis](http://redis.io/).

This is an experiment in using PrestoDb to run SQL queries on top of redis. 
It starts a PrestoDB server and attempts to connect to redis instance on localhost port 6379

The data model is treat maps as if they were tables and objects in maps as rows.


Requirements
================

The redis collections pakcage should be installed in the local maven repo
[Redis Collection}(https://github.com/otaviojava/redis-collections)


Usage
================

1. Start the server using gradle (2+). This will start the server on port 8383:

 - `gradle runServer`
 
2. Download presto [client](http://prestodb.io/docs/current/installation/cli.html) and run it using:

 - `./presto --server localhost:8383 --catalog redis -schema maps` 

3. Run some SQL queries

 - `SHOW TABLES;`
 - `DESC employees;` 
 - `SELECT * FROM employees WHERE sales < 2000;`
 - `SELECT active, SUM(sales) FROM employees GROUP BY active;`

The server comes with some example data in table *employees*.
More data can be inserted into redis directly


Config
================

Config is in src/main/resources


Status
================

Experimental proof of concept.

**Warning** The server will attempt to wipe all data on the connecting redis instance evey time it starts.


Credits
================

The overall design is based on the presto hazecast connector.
[Presto Hazelcast](https://github.com/ajermakovics/presto-hazelcast)
