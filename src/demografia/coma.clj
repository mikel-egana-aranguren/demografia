(use 'clojure.java.io)

(def s (slurp "./data/ataun-coma-2014.csv"))

(def sr (clojure.string/replace s ";" ","))

(spit "./data/ataun-coma-result-2014.csv" sr)




