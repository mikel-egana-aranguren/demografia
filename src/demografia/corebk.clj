(ns demografia.corebk
    (:require
     [grafter.tabular :refer [_ add-column add-columns apply-columns
                              build-lookup-table column-names columns
                              derive-column drop-rows graph-fn grep make-dataset
                              mapc melt move-first-row-to-header read-dataset
                              read-datasets rows swap swap take-rows
                              test-dataset test-dataset]]
     [grafter.rdf :refer [s]]
     [grafter.rdf.protocols :refer [->Quad]]
     [grafter.rdf.templater :refer [graph]]
     [grafter.pipeline :refer [declare-pipeline]]
     [grafter.vocabularies.rdf :refer :all]
     [grafter.vocabularies.foaf :refer :all]
     [demografia.prefix :refer [base-id base-graph base-vocab base-data]]
     [demografia.transform :refer [->integer]]))

; Declare our graph template which will destructure each row and
; convert it into an RDF graph.  This will be the final step in our
; pipeline definition.

(def make-graph
  (graph-fn [{:keys [name sex age person-uri gender]}]
            (graph (base-graph "example")
                   [person-uri
                    [rdf:a foaf:Person]
                    [foaf:gender sex]
                    [foaf:age age]
                    [foaf:name (s name)]])))


; Declare a pipe so the plugin can find and run it.  It's just a
; function from Datasetable -> Dataset.

(defn convert-persons-data
  "Pipeline to convert tabular persons data into a different tabular format."
  [data-file]
  (-> (read-dataset data-file)
      (drop-rows 1)
      (make-dataset [:name :sex :age])
      (derive-column :person-uri [:name] base-id)
      (mapc {:age ->integer
             :sex {"f" (s "female")
                   "m" (s "male")}})))

(declare-pipeline convert-persons-data [Dataset -> Dataset]
                  {data-file "A data file"})

(defn convert-persons-data-to-graph
  "Pipeline to convert the tabular persons data sheet into graph data."
  [dataset]
  (-> dataset convert-persons-data make-graph))

(declare-pipeline convert-persons-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

