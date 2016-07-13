## Usage

### Leiningen

lein grafter run demografia.core/convert-persons-data ./data/example-data.csv output.csv

lein grafter run demografia.core/convert-persons-data ./data/example-data.csv output.xlsx

lein grafter run demografia.core/convert-persons-data-to-graph ./data/example-data.csv ./output/output.ttl

lein grafter run demografia.core/convert-ataun-to-data ./data/ataun2014.csv ./output/output-ataun.csv

lein grafter run demografia.core/convert-ataun-data-to-graph ./data/ataun-coma-result-2014.csv ./output/output-ataun.ttl

### REPL

=> (demografia.core/convert-persons-data-to-graph "./data/example-data.csv")

=> (demografia.core/convert-ataun-to-data "./data/ataun2014.csv")




