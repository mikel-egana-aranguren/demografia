(ns demografia.core
  (:require [grafter.tabular :refer [_ add-column add-columns apply-columns
                              build-lookup-table column-names columns
                              derive-column drop-rows graph-fn grep make-dataset
                              mapc melt move-first-row-to-header read-dataset
                              read-datasets rows swap swap take-rows
                              test-dataset test-dataset ]]
            [grafter.pipeline :refer [declare-pipeline]]
            [demografia.transform :refer [->integer]]
            [demografia.transform :refer [rep]]
            [demografia.transform :refer [urlify]]
    )
  )



(defn convert-ataun-to-data
  "Pipeline to convert tabular Ataun demografia data into a different tabular format."
  [data-file]
  (-> (read-dataset data-file)
      (drop-rows 1)
      (columns [:a :c :i :j :k])
;      (make-dataset move-first-row-to-header) ; Column names with whit spaces won't work
      (mapc {:i urlify})
      )
  )


(declare-pipeline convert-ataun-to-data [Dataset -> Dataset]
                  {data-file "Demografia ataun"})

(print (convert-ataun-to-data "./data/ataun-coma-result-2014.csv"))
;(read-dataset "./data/example-data.csv")
;(println (convert-ataun-to-data "./data/ataun2014.csv"))
;(print (convert-ataun-to-data "./data/example-data.csv"))

; Solo imprime el objeto incanter
;(spit "./output/ataun-coma-result-grafter-2014.csv" (convert-ataun-to-data "./data/ataun-coma-result-2014.csv"))

