(ns demografia.core-test
  (:require [clojure.test :refer :all]
;            [demografia.core :refer :all])
  )

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

;(deftest round-trip-quad-serialize-deserialize-test
;  (let [quad (graph "http://example.org/test/graph"
;                    ["http://test/subj" ["http://test/pred" "http://test/obj"]])
;        string-wtr (java.io.StringWriter.)
;        serializer (rdf-serializer string-wtr :format rdf-nquads)]
;    (add serializer quad)
;    (is (= quad
;           (statements (java.io.StringReader. (.toString string-wtr)) :format rdf-nquads)))))
