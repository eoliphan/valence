# valence

A small set of utilities for working with Datomic Ions

* Logging: A [Timbre](https://github.com/ptaoussanis/timbre) appender 
for the Ion [cast logging](https://docs.datomic.com/cloud/ions/ions-monitoring.html#java-logging)
* Reitit-based Routing: COMING SOON


## Usage

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
TODO: Add output examples

### Reitit Routing

