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
            
;            [demografia.transform :refer [->integer observation-uri urlify]]
             
            [demografia.transform :refer :all]
            
            [demografia.prefix :refer :all]
    )
  )


; https://groups.google.com/d/msg/grafter-users/2W_c5ud6pYE/ARWpnUL_AwAJ

;This is the difference between :keywords and strings I was refering too; sorry I should have suggested the solution to you though!  As I said this is an unfortunate design decision that we've inherited from incanter... basically Incanter tries to hide the difference between keywords and strings; so a lot of the time they will seem interchangeable.  This is great, until they're not interchangeable!  One place they're not interchangeable is in the destructuring inside graph-fn...  If you have string keys for a specifc column you will need to destructure them like so:
;
;(graph-fn [{:keys [keyword-strings go-here ,,,] :strs [string-keys go-here-like-this]}]
;  ,,,)


(def make-graph
  (graph-fn [{:keys [URTEA UDALARENIZENA HERRIARENIZENA EMAKUMEKOPURUA GIZONKOPURUA jaio-herria-uri herria-uri observation-uri]
             :as row }]
            (graph (base-graph "ataun-demografia-2014")
                   [observation-uri
                      [rdf:a qb:Observation]
                      [base-observation-location herria-uri] 
                      [base-emakume-kopurua (->integer (row "EMAKUMEKOPURUA"))] 
                      [base-gizon-kopurua (->integer (row "GIZONKOPURUA"))]
                      ["http://dbpedia.org/property/birthPlace" jaio-herria-uri]
                      ["http://www.w3.org/2006/time#year" (->integer (row "URTEA"))]
                    ]
                   [herria-uri
                    [rdf:a "http://dbpedia.org/class/yago/MunicipalitiesInGipuzkoa"]
                    ]
             )
            )
  )

(defn convert-ataun-to-data
  "Pipeline to convert tabular Ataun demografia data into a different tabular format."
  [data-file]
  (-> (read-dataset data-file)
      (make-dataset move-first-row-to-header)
      (columns [:URTEA :UDALARENIZENA :HERRIARENIZENA :EMAKUMEKOPURUA :GIZONKOPURUA])
      (mapc {:HERRIARENIZENA urlify})
      (derive-column :jaio-herria-uri [:HERRIARENIZENA] base-id)
      (derive-column :herria-uri [:UDALARENIZENA] base-id)
      (derive-column :observation-uri [:URTEA :UDALARENIZENA :HERRIARENIZENA :EMAKUMEKOPURUA :GIZONKOPURUA] observation-uri)
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

(print (convert-ataun-data-to-graph "./data/ataun-coma-result-2014.csv"))

;(read-dataset "./data/example-data.csv")
;(println (convert-ataun-to-data "./data/ataun2014.csv"))
;(print (convert-ataun-to-data "./data/example-data.csv"))

; Solo imprime el objeto incanter
;(spit "./output/ataun-coma-result-grafter-2014.csv" (convert-ataun-to-data "./data/ataun-coma-result-2014.csv"))

