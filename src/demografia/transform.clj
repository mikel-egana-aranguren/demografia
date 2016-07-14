(ns demografia.transform
  (:require 
    [clojure.string :as st]
    [demografia.prefix :refer [base-id base-graph base-vocab base-data]]
    )
  )

;;; You can specify transformation functions in this namespace for use
;;; within the pipeline.

(defn ->integer
  "An example transformation function that converts a string to an integer"
  [s]
  (Integer/parseInt s))

(defn urlify [sr] 
  (st/replace (st/trim sr) #"\(|\)|\s|\/|\." "-"))

(defn observation-uri [a b c d e] 
  (base-id 
    (str "Observation/" (urlify 
                         (str a "-" b "-" c "-" d "-" e)
                               )
         )
    )
  )
