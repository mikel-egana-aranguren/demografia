(ns demografia.core
  (:require [grafter.tabular :refer [_ add-column add-columns apply-columns
                              build-lookup-table column-names columns
                              derive-column drop-rows graph-fn grep make-dataset
                              mapc melt move-first-row-to-header read-dataset
                              read-datasets rows swap swap take-rows
                              test-dataset test-dataset ]]
            [grafter.pipeline :refer [declare-pipeline]]
            
            [grafter.rdf]
            [grafter.rdf.io]
            [grafter.rdf :refer [s]]
            [grafter.rdf.protocols :refer [->Quad]]
            [grafter.rdf.protocols :refer [ITripleWriteable]]
            [grafter.rdf.templater :refer [graph]]
            [grafter.rdf.io :refer [rdf-serializer]]
            [grafter.rdf.formats :refer [rdf-nquads rdf-turtle]]
            [grafter.vocabularies.qb :refer :all]
            
            [grafter.pipeline :refer [declare-pipeline]]
            [grafter.vocabularies.rdf :refer :all]
            [grafter.vocabularies.foaf :refer :all]
            
            [demografia.transform :refer [->integer observation-uri urlify]]
            
            [demografia.prefix :refer [base-id base-graph base-vocab base-data]]
    )
  )

;"http://opendata.eurohelp.es/property/observation_location"
(def make-graph
  (graph-fn [{:keys [a c i j k jaio-herria-uri herria-uri observation-uri]}]
            (graph (base-graph "ataun-demografia-2014")
                   [observation-uri
                    [rdf:a qb:Observation]
                    ["http://opendata.eurohelp.es/property/observation_location" herria-uri]]
                   )
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
      (derive-column :jaio-herria-uri [:i] base-id)
      (derive-column :herria-uri [:c] base-id)
      (derive-column :observation-uri [:a :c :i :j :k] observation-uri)
      )
  )


(declare-pipeline convert-ataun-to-data [Dataset -> Dataset]
                  {data-file "Demografia ataun"})

(print (convert-ataun-to-data "./data/ataun-coma-result-2014.csv"))

(defn convert-ataun-data-to-graph
  "Pipeline to convert the tabular persons data sheet into graph data."
  [dataset]
  (-> dataset convert-ataun-to-data make-graph))

(declare-pipeline convert-ataun-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;(print (convert-ataun-data-to-graph "./data/ataun-coma-result-2014.csv"))

;(read-dataset "./data/example-data.csv")
;(println (convert-ataun-to-data "./data/ataun2014.csv"))
;(print (convert-ataun-to-data "./data/example-data.csv"))

; Solo imprime el objeto incanter
;(spit "./output/ataun-coma-result-grafter-2014.csv" (convert-ataun-to-data "./data/ataun-coma-result-2014.csv"))

