(ns valence.timbre
  "A timbre appender that delgates calls to the Datomic Ions cast api,
  based on timbre example appender.  Current default mapping
  is :error -> cast/alert, all others -> event. "
  (:require
    [taoensso.encore :as enc]
    [taoensso.timbre :as timbre]
    [clojure.pprint :refer [pprint]]
    [datomic.ion.cast :as cast]))

(defn appender                                              ; Appender constructor
  "Docstring to explain any special opts to influence appender construction,
  etc. Returns the appender map."

  ;; []
  [& [opts]]                                                ; Only necessary if your appender constructor takes any special opts

  (let [{:keys []} opts]                                    ; Destructure any special appender constructor opts

    ;; We'll return a new appender (just a map),
    ;; see `timbre/example-config` for info on all available keys:

    {:enabled?   true                                       ; Please enable new appenders by default
     :async?     false                                      ; Use agent for appender dispatch? Useful for slow dispatch
     :min-level  nil                                        ; nil (no min level), or min logging level keyword

     ;; Provide any default rate limits?
     ;; :rate-limit nil
     :rate-limit [[5 (enc/ms :mins 1)]                      ; 5 calls/min
                  [100 (enc/ms :hours 1)]                   ; 100 calls/hour
                  ]

     :output-fn  :inherit                                   ; or a custom (fn [data]) -> string


     :fn
                 (fn [data]
                   (let [{:keys
                          [instant level output_
                           ]} data
                         output-str (force output_)]

                     ;; This is where we produce our logging side effects.
                     ;; In this case we'll just call `println`:

                     (pprint (select-keys data [:appender-id
                                                  :instant
                                                  :level
                                                  :error-level?
                                                  :?ns-str
                                                  :?file
                                                  :?line
                                                  :?err
                                                  :vargs
                                                  :msg_]))
                     (let [base-err (cond-> {:msg   (force (:msg_ data))
                                             :ns    (:?ns-str data)
                                             :level (:level data)
                                             :file  (:?file data)
                                             :line  (:?line data)}
                                      (:?err data) (assoc :err (:?err data)))]
                       (if (:error-level? data)
                         (cast/alert base-err)
                         (cast/event base-err)))))}))

(comment
  ;to test locally
  (cast/initialize-redirect :stdout)
  (timbre/merge-config! {:appenders {:ions    (appender)
                                     :println {:enabled? true}}})


  (merge (example-appender) {:min-level :debug}))