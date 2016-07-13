(ns demografia.transform)

;;; You can specify transformation functions in this namespace for use
;;; within the pipeline.

(defn ->integer
  "An example transformation function that converts a string to an integer"
  [s]
  (Integer/parseInt s))

(defn rep [sr] 
  (clojure.string/replace sr "20" "22"))

(defn urlify [sr] 
  (clojure.string/replace sr #"\(|\)|\s|\/" "-"))
