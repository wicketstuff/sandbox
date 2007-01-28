Wicket Bench
------------

Originally written by Phil Kulak, and forward ported to Wicket 1.3 (snapshot version) by Martijn Dashorst. This project contains a benchmark page for frameworks to see how they compare under load/stress. Currently this project supports Wicket [1] and Tapestry [2]. Other project implementations are welcomed.

Running the benchmark
---------------------
You'll need Maven 2 to run this project, or you'll have to get creative yourself. All you need to do is:

    mvn jetty:run

and point your browser to the following URL:

    http://localhost:8080/wicket-benchmark

You can find a jmeter [3] test script in src/jmeter to satisfy your curiosity how the frameworks handle stress.

[1] http://wicketframework.org
[2] http://tapestry.apache.org
[3] http://jakarta.apache.org/jmeter
