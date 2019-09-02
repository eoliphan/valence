# valence

A set of utilities for working with Datomic Cloud and Ions.  Includes integrations with some common libraries as well as implementations of a few features that are similar to what's available in on-prem.

This is also a bit of an experiment with better ways to manage libs with Clojure deps.  Being liberated from .jars makes even more fine grained libraries more feasible. So here we'll try to allow you to pull in exactly what you need and no more.

* Logging: A [Timbre](https://github.com/ptaoussanis/timbre) appender 
for the Ion [cast logging](https://docs.datomic.com/cloud/ions/ions-monitoring.html#java-logging)
* Reitit-based Routing: COMING SOON
* Datomic log processing (a lá on-prem's tx-report-queue): COMING SOON


## Usage
Add to your deps.edn 
```clojure
...
:deps {{:git/url "https://github.com/eoliphan/valence.git"
        :tag "0.1.2"}}

```

### Timbre Logging
```clojure
(ns foo.core 
  (:require 
  [valence.timbre :as v.timbre]
  [taoensso.timbre :as log]))
  
; setup the logger, in your ion entry point 
(timbre/merge-config! {:appenders {:ions (v.timbre/appender)}})


; use timbre normally

(def myfunc
  [x]
  (log/debug "The value of x is " x)) 
  
```
The initial release maps :error level messages to `(cast/alert)` and all others to `(cast/event)`, subsequent versions
will allow more fine-grained control.  Messages are "cast" with the following format
```clojure
{:msg   ...
 :ns    ...
 :level ...
 :file  ...
 :line  ...
 :err   <optional stack trace for exception> }

```

### Reitit Routing
**COMING SOON**

### Log processing
**COMING SOON**
Datomic On-prem's `tx-report-queue` function provided a nice way to add reactivity, event-oriented integrations, etc to your Datomic apps.  It's not available in Cloud, but we also have all of AWS's goodness and the Datomic Log API at our disposal.  This lib leverages AWS Step Functions and a liberal dose of Onyx-inspired data driven configuration to make it relatively easy for you to consume the log with a bit of data and your own functions. 


