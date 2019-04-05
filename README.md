# valence

A small set of utilities for working with Datomic Ions

* Logging: A [Timbre](https://github.com/ptaoussanis/timbre) appender 
for the Ion [cast logging](https://docs.datomic.com/cloud/ions/ions-monitoring.html#java-logging)
* Reitit-based Routing: COMING SOON


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

